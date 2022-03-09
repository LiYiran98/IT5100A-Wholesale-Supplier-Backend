import java.sql.DriverManager
import java.sql.Connection

object Payment {

  def PaymentExecutor(C_W_ID: String, C_D_ID: String, C_ID: String, Payment: String) {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"
      
    var connection:Connection = null

    try {
        Class.forName(driver)
        connection = DriverManager.getConnection(url, username, password)

        val statement = connection.createStatement()
        
        val res1 = statement.executeUpdate(
                "UPDATE Warehouse " +
				"SET W_YTD = W_YTD + " + Payment +
				" WHERE W_ID = " + C_W_ID)
        
        val res2 = statement.executeUpdate(
            "UPDATE District " +
            "SET D_YTD = D_YTD + " + Payment +
			"WHERE D_W_ID = " + C_W_ID + " AND D_ID = " + C_D_ID)
        
        val res3 = statement.executeUpdate(
            "UPDATE Customer " +
            "SET C_BALANCE = C_BALANCE - " + Payment + " , C_YTD_PAYMENT = C_YTD_PAYMENT + " + Payment + " , C_PAYMENT_CNT = C_PAYMENT_CNT + 1 " +
            "WHERE C_W_ID = " + C_W_ID + " AND C_D_ID = " + C_D_ID + " AND C_ID = " + C_ID)
        
        val res4 = statement.executeQuery(
            "SELECT * FROM Customer " +
            "WHERE C_W_ID = " + C_W_ID + " AND C_D_ID = " + C_D_ID + " AND C_ID = " + C_ID)
        
        while (res4.next()){
				printf("Customer identifier:\n" +
				    "   C_W_ID: %s, C_D_ID: %s, C_ID: %s \n", res4.getString("C_W_ID"),
					res4.getString("C_D_ID"), res4.getString("C_ID"));

				printf("Customer name:\n" +
				    "   %s %s %s \n",
				    res4.getString("C_FIRST"), res4.getString("C_MIDDLE"),
					res4.getString("C_LAST"));

				printf("Customer address:\n" +
					"   Street_1: %s \n" +
				    "   Street_2: %s \n" +
					"   City: %s \n" +
					"   State: %s \n" +
					"   Zipcode: %s \n",
					res4.getString("C_STREET_1"), res4.getString("C_STREET_2"),
					res4.getString("C_CITY"), res4.getString("C_STATE"),
					res4.getString("C_ZIP"));

				printf("Other information:\n" +
					"   Phone number: %s \n" +
					"   Entry created date & time: %s \n" +
					"   Credit status: %s \n" +
					"   Credit limit: %s \n" +
					"   Discount rate: %s \n" +
					"   Outstanding payment balance: %s \n",
					res4.getString("C_PHONE"), res4.getString("C_SINCE"),
					res4.getString("C_CREDIT"), res4.getString("C_CREDIT_LIM"),
					res4.getString("C_DISCOUNT"), res4.getString("C_BALANCE"));
				println("------------------------");
			}
        
        val res5 = statement.executeQuery(
            "SELECT W_STREET_1, W_STREET_2, W_CITY, W_STATE, W_ZIP FROM Warehouse " +
					"WHERE W_ID = " + C_W_ID)
        
        while (res5.next()){
				printf("Warehouse address:\n" +
				    "   Street_1: %s \n" +
					"   Street_2: %s \n" +
					"   City: %s \n" +
					"   State: %s \n" +
					"   Zipcode: %s \n",
					res5.getString("W_STREET_1"), res5.getString("W_STREET_2"),
					res5.getString("W_CITY"), res5.getString("W_STATE"),
					res5.getString("W_ZIP"));
				println("------------------------");
        }
        
        val res6 = statement.executeQuery(
            "SELECT D_STREET_1, D_STREET_2, D_CITY, D_STATE, D_ZIP FROM District " +
					"WHERE D_W_ID = " + C_W_ID + " AND D_ID = " + C_D_ID)
        
        while (res6.next()){
                printf("District address:\n" +
					"   Street_1: %s \n" +
					"   Street_2: %s \n" +
					"   City: %s \n" +
					"   State: %s \n" +
					"   Zipcode: %s \n",
					res6.getString("D_STREET_1"), res6.getString("D_STREET_2"),
					res6.getString("D_CITY"), res6.getString("D_STATE"),
					res6.getString("D_ZIP"));
				println("------------------------");
        }
        
    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}