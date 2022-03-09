import scala.io.StdIn.readLine
import java.sql.DriverManager
import java.sql.Connection

object mainDriver extends App {
    
    print("Welcome to the Wholesale Supplier's Backend System!\n")

    while(true) {
        print("Press the corresponding key to perform the operation:\n")
        print("N -- New Order       P -- Payment\n")
        print("D -- Delivery        O -- Order Status\n")
        print("S -- Stock Level     I -- Popular Item\n")
        print("T -- Top Balance     R -- Related Customer\n")
        print("Q -- Quit\n")
        val key = readLine()
        key match {
            case "N"  => {
                println("New Order")
            }
            case "P"  => {
                println("Payment")
            }
            case "D"  => {
                println("Delivery")
            }
            case "O"  => {
                println("Please enter the customer identifiers:")
                print("C_W_ID: ")
                val C_W_ID = readLine()
                print("C_D_ID: ")
                val C_D_ID = readLine()
                print("C_ID: ")
                val C_ID = readLine()
                OrderStatus.OrderStatusExecutor(C_W_ID, C_D_ID, C_ID)
            }
            case "S"  => {
                println("Stock Level")
            }
            case "I"  => {
                println("Popular Item")
            }
            case "T"  => {
                println("Top Balance")
            }
            case "R"  => {
                println("Related Customer")
            }
            case "Q"  => {
                println("Bye!")
                System.exit(0)
            }
            // catch the default with a variable so you can print it
            case whoa  => println("Unexpected case: " + whoa.toString)
        }
    }
}
