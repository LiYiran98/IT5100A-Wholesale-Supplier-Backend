import java.sql.DriverManager
import java.sql.Connection

object StockLevel {

  def StockLevelExecutor(W_ID: String, D_ID: String, T: String, L:String) {
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
        "WITH N AS( " +
          "SELECT D_NEXT_O_ID FROM district " +
          s"WHERE D_W_ID = $W_ID AND D_ID = $D_ID)" +
          "SELECT COUNT(DISTINCT(OL_I_ID)) AS res " +
          "FROM Order_line AS ol " +
          "LEFT JOIN Stock AS s " +
          "ON ol.OL_W_ID = s.S_W_ID " +
          "AND ol.OL_I_ID = s.S_I_ID " +
          s"WHERE ol.OL_D_ID = $D_ID " +
          s"AND ol.OL_W_ID = $W_ID " +
          s"AND ol.OL_O_ID >= (SELECT * FROM N)-$L " +
          "AND ol.OL_O_ID <= (SELECT * FROM N) " +
          s"AND s.S_QUANTITY < $T"
      )

      while ( res1.next() ) {
        printf("Customer information: %s\n", res1.getString("res"))
          "---------------------------------"
      }
    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}