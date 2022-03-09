import java.sql.DriverManager
import java.sql.Connection

object OrderStatus {

  def OrderStatusExecutor(C_W_ID: String, C_D_ID: String, C_ID: String) {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"
      
    var connection:Connection = null

    try {
          Class.forName(driver)
          connection = DriverManager.getConnection(url, username, password)

          val statement = connection.createStatement()
          val res1 = statement.executeQuery(
              "SELECT C_FIRST, C_MIDDLE, C_LAST, C_BALANCE " +
                    "FROM Customer " +
                    "WHERE C_ID = " + C_ID +
                    " AND C_W_ID = " + C_W_ID +
                    " AND C_D_ID = " + C_D_ID )
          while ( res1.next() ) {
            println("Customer information: \n" +
                   "First Name: " + res1.getString("C_FIRST") + "\n" +
                   "Middle Name: " + res1.getString("C_MIDDLE") + "\n" +
                    "Last Name: " + res1.getString("C_LAST") + "\n" +
                    "Balance: " + res1.getString("C_BALANCE") + "\n" +
                   "---------------------------------");
          }
        
        val res2 = statement.executeQuery(
            "with max_o_id_temp as " +
					"(select max(o_id) as max_o_id " +
					"from order_ " +
					"where o_w_id = " + C_W_ID +
					" and o_d_id = " + C_D_ID +
					" and o_c_id = " + C_ID + ") " +

				"select o_id, o_entry_d, o_carrier_id " +
				"from order_ o " +
				"where o_id in (select max_o_id from max_o_id_temp) " +
				"and o_w_id = " + C_W_ID +
				" and o_d_id = " + C_D_ID   
        )
        var max_O_ID = ""
        while ( res2.next() ) {
            max_O_ID = res2.getString("O_ID");
            println("Last order: \n" +
                   "Order Number: " + res2.getString("O_ID") + "\n" +
                   "Entry Date & Time: " + res2.getString("O_ENTRY_D") + "\n" +
                    "Carrier ID: " + res2.getString("O_CARRIER_ID") + "\n" +
                   "---------------------------------");
          }
        
        val res3 = statement.executeQuery(
            "select ol_i_id, ol_supply_w_id, ol_quantity, ol_amount, ol_delivery_d " +
				"from order_line " +
				"where ol_o_id = " + max_O_ID +
				" and ol_w_id = " + C_W_ID +
				" and ol_d_id = " + C_D_ID
        )
        println("Items in the last order:");
        println("Item ID/Warehouse/Quantity/Amount/Delivery Date & Time");
        println("(OL_I_ID)/(OL_SUPPLY_W_ID)/(OL_QUANTITY)/(OL_AMOUNT)/(OL_DELIVERY_D)");
        while (res3.next()){
            printf("%s\t%s\t%s\t%s\t%s\t\n",res3.getString("OL_I_ID"), res3.getString("OL_SUPPLY_W_ID"), res3.getString("OL_QUANTITY"), res3.getString("OL_AMOUNT"), res3.getString("OL_DELIVERY_D"))
        }
        println("---------------------------------");
        
        
    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}