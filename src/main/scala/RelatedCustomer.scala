import java.sql.DriverManager
import java.sql.Connection


object RelatedCustomer {
  def RelatedCustomerExecutor(C_W_ID: String, C_D_ID: String, C_ID: String): Unit = {
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
        "with target_order as \n" +
          "	(select o_w_id, o_d_id, o_id \n" +
          " from order_ o \n" +
          "	where (o.o_w_id = " + C_W_ID + " and o.o_d_id = " + C_D_ID + " and o.o_c_id = " + C_ID + ")), \n" +

          "	order_ols as \n" +
          "	(select o_w_id, o_d_id, o_id, ol_i_id \n" +
          "	from target_order as tos \n" +
          " join order_line as ol \n" +
          "	on (ol.ol_w_id = tos.o_w_id \n" +
          "	and ol.ol_d_id = tos.o_d_id \n" +
          "	and ol.ol_o_id = tos.o_id)), \n" +

          "	common_items as \n" +
          "	(select ol1.o_w_id as o1_w_id, ol1.o_d_id as o1_d_id, ol1.o_id as o1_o_id, \n" +
          "	ol2.ol_w_id as o2_w_id, ol2.ol_d_id as o2_d_id, ol2.ol_o_id as o2_o_id, \n" +
          "	count(*) as common_item_count \n" +
          "	from order_ols as ol1 \n" +
          " join order_line as ol2 \n" +
          "	on (ol2.ol_w_id <> ol1.o_w_id \n" +
          "	and ol2.ol_i_id = ol1.ol_i_id) \n" +
          "	group by o_w_id, o_d_id, o_id, ol_w_id, ol_d_id, ol_o_id) \n" +

          "select o2_w_id as c2_w_id, o2_d_id as c2_d_id, o_c_id as c2_c_id \n" +
          "from common_items as ci \n" +
          "join order_ as o \n" +
          "on (o.o_w_id = ci.o2_w_id \n" +
          "and o.o_d_id = ci.o2_d_id \n" +
          "and o.o_id = ci.o2_o_id) \n" +
          "where ci.common_item_count >= 2");

      println("======Related customer======")

      while (res1.next()) {
        val C_W_ID = res1.getInt("c2_w_id");
        val C_D_ID = res1.getInt("c2_d_id");
        val C_ID = res1.getInt("c2_c_id")
        println("(c_w_id, c_d_id, c_id) = " + (C_W_ID, C_D_ID, C_ID) + "\n")
      }

      println("============================")
    }
    catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }


}
