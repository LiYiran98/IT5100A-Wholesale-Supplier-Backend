import java.sql.DriverManager
import java.sql.Connection

object NewOrder {

  def NewOrderExecutor(newOrderInfo: String, C_ID: String, W_ID: String, D_ID: String, M: Int) {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"
      
    var connection:Connection = null

    try {
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)

        val statement = connection.createStatement()
        val res1 = statement.execute(
            "insert into newOrderInfoTable values " + newOrderInfo)
        
        val res2 = statement.execute(
            "update district d " +
            "set d_next_o_id = d_next_o_id + 1 " +
            "where d.d_w_id = " + W_ID +
			" and d.d_id = " + D_ID)
        
        val res3 = statement.execute(
            "insert into order_ " +
            "   (o_id, o_d_id, o_w_id, o_c_id, o_entry_d, o_carrier_id, o_ol_cnt, o_all_local) " +
            "with newOrderInfo as (select * from newOrderInfoTable) " +
            "select d_next_o_id - 1, " + D_ID + ", " + W_ID + ", " + C_ID + ", CURRENT_TIMESTAMP(), null, " + M.toString + ", " +
                "IF(" + W_ID + " != any(select no_supplier_warehouse from newOrderInfo), 0, 1) " +
            "from district d " +
            "where d.d_w_id = " + W_ID +
            " and d.d_id = " + D_ID)
        
        val res4 = statement.execute(
            "insert into tmp_new_order " +
            "with newOrderInfo as (select * from newOrderInfoTable) " +
            "select no_supplier_warehouse, no_item_number, " +
            "    IF(s_quantity-no_quantity < 10, s_quantity-no_quantity+100, s_quantity-no_quantity) as adjusted_quantity, " +
            "    s_ytd + no_quantity as new_ytd, " +
            "    s_order_cnt + 1 as new_order_cnt, " +
            "    s_remote_cnt + IF(no_supplier_warehouse != no_w_id, 1, 0) as new_remote_cnt " +
            "from newOrderInfo noi " +
            "join stock s " +
            "on s.s_w_id = noi.no_supplier_warehouse " +
            "and s.s_i_id = noi.no_item_number;")
        
        val res5 = statement.execute(
            "update stock s, tmp_new_order t " +
            "set s_quantity = t.adjusted_quantity, " + 
            "    s_ytd = t.new_ytd, " +
            "    s_order_cnt = t.new_order_cnt, " +
            "    s_remote_cnt = t.new_remote_cnt " +
            "where s.s_w_id = t.no_supplier_warehouse " + 
            "and s.s_i_id = t.no_item_number;" )
        
        val res6 = statement.execute(
            "insert into order_line " +
            "(ol_o_id, ol_d_id, ol_w_id, ol_number, ol_i_id, ol_supply_w_id, ol_quantity, ol_amount, ol_delivery_d, ol_dist_info) " +
            "with " +
            "    newOrderInfo as " +
            "        (select * from newOrderInfoTable), " +
            "    newData as " +
            "        (select d_next_o_id, no_d_id, no_w_id, " +
            "            no_item_number, no_supplier_warehouse, no_quantity, " +
            "            no_quantity * i_price as item_amount, " +
            "            NULL as new_delivery_d, " +
            "            no_row_count as rowno, " +
            "            CONCAT('S_DIST_', d_id) as new_dist_info " +

            "        from newOrderInfo noi " +
            "        join district d " +
            "        on d.d_w_id = noi.no_w_id " + 
            "        and d.d_id = noi.no_d_id " +
            "        join item i " +
            "        on i.i_id = noi.no_item_number) " +
            "select " +
            "    d_next_o_id, no_d_id, no_w_id, " +
            "    rowno, no_item_number, no_supplier_warehouse, no_quantity, " +
            "    item_amount, new_delivery_d, new_dist_info " +
            "from newData;" )
        
        val res7 = statement.executeQuery(
            "select c_last, c_credit, c_discount, w_tax, d_tax " +
            "from district d " +
			"join warehouse w " +
			"on w.w_id = d.d_w_id " +
			"join customer c " +
			"on c.c_w_id = d.d_w_id " +
			"and c.c_d_id = d.d_id " +
			"where d.d_w_id = " + W_ID + " and d.d_id = " + D_ID + " and c.c_id = " + C_ID)
        
        var wtax = 0.00
        var dtax = 0.00
        var cdiscount = 0.00
        while (res7.next()) {
			wtax = res7.getFloat("w_tax");
			dtax = res7.getFloat("d_tax");
			cdiscount = res7.getFloat("c_discount");

			printf(
				"Customer Information:\n" +
				"Customer Identifier: (W_ID: " + W_ID + ", D_ID: " + D_ID + ", C_ID: " + C_ID + ")\n" +
				"Customer Last Name: %s\n" +
				"Customer Credit: %s\n" +
				"Customer Discount: %f\n",
				res7.getString("c_last"),
				res7.getString("c_credit"),
				cdiscount);
			println("------------------------");
			printf(
				"Tax Information:\n" +
				"Warehouse Tax Rate: %f\n" +
				"District Tax Rate: %f\n",wtax, dtax);
			println("------------------------");
        }
        
        val res8 = statement.executeQuery(
            "select o_id, o_entry_d from order_ o " +
            "where (o_w_id, o_d_id, o_id) in " +
			"( " +
			"	select " + W_ID + ", " + D_ID + ", d_next_o_id - 1 " +
			"	from district d " +
			"	where d.d_w_id = " + W_ID +
			"	and d.d_id = " + D_ID +
			");"
        )
        
        while(res8.next()) {
			System.out.printf(
				"Order Information: \n" +
				"Order ID: %s\n" +
				"Order entry date: %s\n",
				res8.getString("o_id"),
				res8.getTimestamp("o_entry_d"));
        }
        println("------------------------");
        
        val res9 = statement.executeQuery(
            "with neworderinfo as " +
			" (select * from neworderinfotable) " +
			"select sum(no_quantity * i_price) as total_amount " +
			"from neworderinfo noi " +
			"join item i " +
			"on i.i_id = noi.no_item_number;"
        )
        
        var total_amount = 0.00
        while(res9.next()) {
            total_amount = res9.getFloat("total_amount");
        }
        total_amount = total_amount * (1 + dtax + wtax) * (1 - cdiscount)
        printf(
			"Number of Items & Total Amount:\n" +
			"Number of Items: " + M.toString + "\n" +
			"Total Amount: " + total_amount.toString + "\n")
		println("------------------------");
        
        val res10 = statement.executeQuery(
            "with " +
			"	newOrderInfo as " +
			"		(select * from newOrderInfoTable), " +
			"	newOrderExtraInfo as " +
			"		(select * " +
			"		from newOrderInfo noi " +
			"		join district d " +
			"		on d.d_w_id = noi.no_w_id " +
			"		and d.d_id = noi.no_d_id) " +
			"select no_item_number, i_name, no_supplier_warehouse, no_quantity, ol_amount, s_quantity " +
			"from newOrderExtraInfo noei " +
			"join item i " +
			"on i.i_id = noei.no_item_number " +
			"join order_line ol " +
			"on ol.ol_w_id = noei.no_w_id " +
			"and ol.ol_d_id = noei.no_d_id " +
			"and ol.ol_o_id = noei.d_next_o_id " +
			"and ol.ol_number = noei.no_row_count " +
			"join stock s " +
			"on s.s_w_id = noei.no_supplier_warehouse " +
			"and s.s_i_id = noei.no_item_number; "
        )
        
        println("Items Information:");
		System.out.println("ITEM_NUMBER/I_NAME/SUPPLIER_WAREHOUSE/QUANTITY/OL_AMOUNT/S_QUANTITY:");
		while (res10.next()) {
            printf(
                "%s\t%s\t%s\t%s\t%s\t%s\n",
                res10.getString("no_item_number"),
                res10.getString("i_name"),
                res10.getString("no_supplier_warehouse"),
                res10.getString("no_quantity"),
                res10.getString("ol_amount"),
                res10.getString("s_quantity"));
		}
        
        val res11 = statement.execute(
            "truncate table tmp_new_order;"
        )
        
        val res12 = statement.execute(
            "truncate table newOrderInfoTable;"
        )
        
    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}