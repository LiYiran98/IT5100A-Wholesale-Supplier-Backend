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
                println("Please enter the customer identifiers:")
                print("C_W_ID: ")
                val C_W_ID = readLine()
                print("C_D_ID: ")
                val C_D_ID = readLine()
                print("C_ID: ")
                val C_ID = readLine()
                print("Please enter the payment amount:")
                val payment = readLine()
                Payment.PaymentExecutor(C_W_ID, C_D_ID, C_ID, payment)
            }
            case "D"  => {
                print("Please enter the warehouse ID: ")
                val W_ID = readLine()
                print("Please enter the carrier ID: ")
                val CARRIER_ID = readLine()
                Delivery.DeliveryExecutor(W_ID, CARRIER_ID)
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
                println("Please enter the customer identifiers:")
                print("W_ID: ")
                val W_ID = readLine()
                print("D_ID: ")
                val D_ID = readLine()
                print("Stock threshold: ")
                val T = readLine()
                print("Number of last orders to be examined: ")
                val L = readLine()
                StockLevel.StockLevelExecutor(W_ID, D_ID, T, L)
            }
            case "I"  => {
                println("Please enter the customer identifiers:")
                print("W_ID: ")
                val W_ID = readLine()
                print("D_ID: ")
                val D_ID = readLine()
                print("Number of last orders to be examined: ")
                val L = readLine()
                PopularItem.PopularItemExecutor(W_ID, D_ID, L)
            }
            case "T"  => {
                TopBalance.TopBalanceExecutor()
            }
            case "R"  => {
                println("Please enter the customer identifiers:")
                print("C_W_ID: ")
                val C_W_ID = readLine()
                print("C_D_ID: ")
                val C_D_ID = readLine()
                print("C_ID: ")
                val C_ID = readLine()
                RelatedCustomer.RelatedCustomerExecutor(C_W_ID, C_D_ID, C_ID)
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
