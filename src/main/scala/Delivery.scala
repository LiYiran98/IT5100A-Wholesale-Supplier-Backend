import java.sql.DriverManager
import java.sql.Connection

object Delivery {

  def DeliveryExecutor(W_ID: String, CARRIER_ID: String) {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"
      
    var connection:Connection = null

    try {
          Class.forName(driver)
          connection = DriverManager.getConnection(url, username, password)
        
          println("Updating delivery information...")

          val statement = connection.createStatement()
          val update_order = statement.execute(
              "update order_ o " +
              "set o_carrier_id = " + CARRIER_ID +
              " where (o.o_w_id, o.o_d_id, o.o_id) in " +
              "(select o_w_id, o_d_id, min_oid from " +
              "     (select o_w_id, o_d_id, o_carrier_id, min(o_id) as min_oid" +
              "     from order_ o " +
              "     where o.o_w_id = " + W_ID +
              "     and (o.o_carrier_id = 0 or o.o_carrier_id is null) " +
              "     group by o_w_id, o_d_id, o_carrier_id) as mid_subquery)" )
        
        val update_order_line = statement.execute(
                "update order_line ol2 " +
                "set ol_delivery_d = current_timestamp() " +
                "where (ol2.ol_w_id, ol2.ol_d_id, ol2.ol_o_id, ol2.ol_number) in " +
                "(select * " +
                "from (with N as " +
	            "    (select o_w_id, o_d_id, o_carrier_id, min(o_id) as min_oid " +
	            "    from order_ o " +
	            "    where o.o_w_id = 1 " +
	            "    and (o.o_carrier_id = 0 or o.o_carrier_id is null)" +
	            "    group by o_w_id, o_d_id, o_carrier_id) " +

                "select ol_w_id, ol_d_id, ol_o_id, ol_number " +
	            "from N join order_line ol " +
	            "on ol.ol_w_id = N.o_w_id " +
	            "and ol.ol_d_id = N.o_d_id " +
	            "and ol.ol_o_id = N.min_oid) as subquery); ")
        
        val insert_tmp_data = statement.execute(
                "insert into tmp_delivery " +
                "with N as " +
                "    (select o_w_id as n_owid, " + 
                "        o_d_id as n_odid, " +
                "        o_carrier_id as n_ocarrierid, " + 
                "        min(o_id) as n_oid " +
                "    from order_ o " +
                "    where o.o_w_id = " + W_ID +
                "    and (o.o_carrier_id = 0 or o.o_carrier_id is null) " +
                "    group by n_owid, n_odid, n_ocarrierid), " + 

                "    item_amount as " +
                "    (select n_owid as ia_owid, " + 
                "        n_odid as ia_odid, " +
                "        n_oid as ia_oid, " +
                "        sum(ol_amount) as sum_amount " + 
                "    from N join order_line ol " +
                "    on ol.ol_w_id = N.n_owid " +
                "    and ol.ol_d_id = N.n_odid " +
                "    and ol.ol_o_id = N.n_oid " +
                "    group by ia_owid, ia_odid, ia_oid) " + 

                "select o_w_id, o_d_id, o_c_id, sum_amount " +
                "from item_amount ia join order_ o2 " +
                "on o2.o_w_id = ia.ia_owid " +
                "and o2.o_d_id = ia.ia_odid " +
                "and o2.o_id = ia.ia_oid;" )
        
        val update_customer = statement.execute(
            "update customer c, tmp_delivery t " +
            "set c.c_balance = c.c_balance + t.sum_amount, " +
            "    c.c_delivery_cnt = c.c_delivery_cnt + 1 " +
            "where c.c_w_id = t.o_w_id " +
            "and c.c_d_id = t.o_d_id " +
            "and c.c_id = t.o_c_id;"
        )
        
        val truncate_tmp_table = statement.execute(
            "truncate table tmp_delivery;"
        )
        
        println("Delivery information updated.")
          
        
    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}