import java.sql.DriverManager
import java.sql.Connection

object TopBalance {

  def TopBalanceExecutor() {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"

    var connection:Connection = null

    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      val statement = connection.createStatement()
      val res = statement.executeQuery(
    "with topCustomer AS " +
          "(SELECT C_FIRST, C_MIDDLE, C_LAST, C_BALANCE, C_W_ID, C_D_ID " +
          "FROM Customer ORDER BY C_BALANCE DESC " +
          "LIMIT 10) " +
          "SELECT W_NAME, D_NAME, C_FIRST, C_MIDDLE, C_LAST, C_BALANCE " +
          "From topCustomer t join Warehouse w on t.C_W_ID = w.W_ID join District d on t.C_D_ID = d.D_ID and d.D_W_ID = w.W_ID"
      )
      while ( res.next() ) {
        val districtName: String = res.getString("D_NAME")
        val warehouseName: String = res.getString("W_NAME")
        val firstName: String = res.getString("C_FIRST")
        val middleName: String = res.getString("C_MIDDLE")
        val lastName: String = res.getString("C_LAST")
        val balance: BigDecimal = res.getBigDecimal("C_BALANCE")

        printf("Name: %s %s %s Balance: %12.2f Warehouse: %s District: %s\n",
          firstName, middleName, lastName, balance, warehouseName, districtName)
      }

    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}