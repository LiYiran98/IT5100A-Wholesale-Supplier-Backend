import java.sql.DriverManager
import java.sql.Connection

object PopularItem {

  def PopularItemExecutor(W_ID: String, D_ID: String, L: String) {
    val driver = "com.mysql.cj.jdbc.Driver"
    val url = "jdbc:mysql://localhost/tpcc"
    val username = "root"
    val password = "123456"

    var connection:Connection = null

    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)

      val statement = connection.createStatement()

      val res_n = statement.executeQuery(
        "SELECT D_NEXT_O_ID " +
          "FROM district d " +
          s"WHERE d.D_W_ID = $W_ID " +
          s"AND d.D_ID = $D_ID;"
      )
      val l = L.toInt
      var n = -1
      while ( res_n.next() ) {
        n = res_n.getInt("D_NEXT_O_ID")
      }

      val res_oc = statement.executeQuery(
        "WITH s AS " +
          "	(SELECT O_W_ID, O_D_ID, O_ID, O_C_ID, O_ENTRY_D " +
          "	FROM order_ o " +
         s" WHERE o.O_D_ID = $D_ID " +
         s"	AND o.O_W_ID = $W_ID " +
         s"	AND o.O_ID >= ${n-l} " +
         s"	AND o.O_ID < $n) " +
          "	SELECT O_ID, O_ENTRY_D, C_FIRST, C_MIDDLE, C_LAST " +
          "	FROM s " +
          "	JOIN customer c " +
          "	ON c.C_W_ID = s.O_W_ID " +
          "	AND c.C_D_ID = s.O_D_ID " +
          "	AND c.C_ID = s.O_C_ID;"
      )
      while ( res_oc.next() ) {
        val oid = res_oc.getString("O_ID")
        val oentryd = res_oc.getString("O_ENTRY_D")
        val cfirst = res_oc.getString("C_FIRST")
        val cmiddle = res_oc.getString("C_MIDDLE")
        val clast = res_oc.getString("C_LAST")
        printf("O_ID = %s, O_ENTRY_D = %s, C_FIRST = %s, C_MIDDLE = %s, C_LAST = %s\n",
          oid, oentryd, cfirst, cmiddle, clast);
      }

      val res_quantity = statement.executeQuery(
          "WITH s AS " +
          "	    (SELECT O_W_ID, O_D_ID, O_ID, O_C_ID, O_ENTRY_D " +
          "	    FROM order_ o " +
         s"	    WHERE o.O_D_ID = $D_ID " +
         s"	    AND o.O_W_ID = $W_ID " +
         s"	    AND o.O_ID >= ${n-l} " +
         s"	    AND o.O_ID < $n), " +
          "	  ols AS " +
          "	    (SELECT OL_W_ID, OL_D_ID, OL_O_ID, OL_I_ID, OL_QUANTITY " +
          "	    FROM order_line ol " +
          "	    WHERE (OL_W_ID, OL_D_ID, OL_O_ID) " +
          "	    IN " +
          "		    (SELECT O_W_ID, O_D_ID, O_ID FROM s)), " +
          "	  pxTemp AS " +
          "	    (SELECT O_W_ID, O_D_ID, O_ID, MAX(OL_QUANTITY) AS max_quantity " +
          "	    FROM s " +
          "	    JOIN ols " +
          "	    ON ols.OL_W_ID = s.O_W_ID " +
          "	    AND ols.OL_D_ID = s.O_D_ID " +
          "	    AND ols.OL_O_ID = s.O_ID " +
          "	    GROUP BY O_W_ID, O_D_ID, O_ID) " +
          "	SELECT I_NAME, max_quantity AS quantity " +
          "	FROM pxTemp " +
          "	JOIN ols " +
          "	ON ols.OL_W_ID = pxTemp.O_W_ID " +
          "	AND ols.OL_D_ID = pxTemp.O_D_ID " +
          "	AND ols.OL_O_ID = pxTemp.O_ID " +
          "	AND ols.OL_QUANTITY = pxTemp.max_quantity " +
          "	JOIN item i " +
          "	ON i.I_ID = ols.OL_I_ID;"
      )
      while ( res_quantity.next() ) {
        val iname: String = res_quantity.getString("I_NAME")
        val quantity: Int = res_quantity.getInt("quantity")
        printf("I_NAME = %s, OL_QUANTITY = %d\n", iname, quantity)
      }

      val res_percentage = statement.executeQuery(
        "WITH s AS " +
          "	    (SELECT O_W_ID, O_D_ID, O_ID, O_C_ID, O_ENTRY_D " +
          "		  FROM order_ o " +
         s"		  WHERE o.O_D_ID = $D_ID " +
         s"		  AND o.O_W_ID = $W_ID " +
         s"		  AND o.O_ID >= ${n-l} " +
         s"		  AND o.O_ID < $n), " +
          "	  ols AS " +
          "		  (SELECT OL_W_ID, OL_D_ID, OL_O_ID, OL_I_ID, OL_QUANTITY " +
          "		  FROM order_line ol " +
          "		  WHERE (OL_W_ID, OL_D_ID, OL_O_ID) " +
          "		  IN " +
          "			  (SELECT O_W_ID, O_D_ID, O_ID FROM s)), " +
          "	  pxTemp AS " +
          "		  (SELECT O_W_ID, O_D_ID, O_ID, MAX(OL_QUANTITY) AS max_quantity " +
          "		  FROM s " +
          "		  JOIN ols " +
          "		  ON ols.OL_W_ID = s.O_W_ID " +
          "		  AND ols.OL_D_ID = s.O_D_ID " +
          "		  AND ols.OL_O_ID = s.O_ID " +
          "		  GROUP BY O_W_ID, O_D_ID, O_ID), " +
          "	  px AS " +
          "		  (SELECT I_ID, I_NAME, max_quantity AS quantity " +
          "		  FROM pxTemp " +
          "		  JOIN ols " +
          "		  ON ols.OL_W_ID = pxTemp.O_W_ID " +
          "		  AND ols.OL_D_ID = pxTemp.O_D_ID " +
          "		  AND ols.OL_O_ID = pxTemp.O_ID " +
          "		  AND ols.OL_QUANTITY = pxTemp.max_quantity " +
          "		  JOIN item i " +
          "		  ON i.I_ID = ols.OL_I_ID), " +
          "	  temp AS " +
          "		  (SELECT I_ID, COUNT(*)/(SELECT COUNT(*) FROM s)*100 AS percentage " +
          "		  FROM " +
          "		  ( SELECT DISTINCT(I_ID) AS I_ID FROM px ) iidlist " +
          "		  JOIN ols " +
          "		  ON ols.OL_I_ID = iidlist.I_ID " +
          "		  GROUP BY I_ID) " +
          "	SELECT I_NAME, percentage " +
          "	FROM temp " +
          "	JOIN item i " +
          "	ON i.I_ID = temp.I_ID;"
      )
      while (res_percentage.next()){
        val iname: String = res_percentage.getString("I_NAME")
        val percentage: Double = res_percentage.getDouble("percentage")
        printf("I_NAME = %s, Percentage = %.2f%%\n", iname, percentage)
      }
      println("---------------------------------");

    } catch {
      case e: Throwable => e.printStackTrace
    }
    connection.close()
  }
}