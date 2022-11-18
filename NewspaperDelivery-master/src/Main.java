import com.newspaper.customer.CustomerView;
import com.newspaper.dailysummary.DailySummaryMain;
import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryAreaMain;
import com.newspaper.deliverydocket.DeliveryView;
import com.newspaper.deliveryperson.DeliveryPersonDB;
import com.newspaper.invoice.InvoiceView;
import com.newspaper.order.OrderExceptionHandler;
import com.newspaper.order.OrderView;
import com.newspaper.publication.PublicationMain;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);


    public static void main(String[] args) throws SQLException {

        DBconnection.init_db();  //open the connection to the database

        int menuChoice = 0; // variable used to store main menu choice
        final int STOP_APP = 10; //value from menu that is used to quit the application
        DeliveryPersonDB deliveryPersonMenu = new DeliveryPersonDB();
        PublicationMain publicationMenu = new PublicationMain();
        CustomerView customerMenu = new CustomerView();
        DeliveryAreaMain deliveryArea = new DeliveryAreaMain();
        OrderView ov = new OrderView();
        DeliveryView view = new DeliveryView();
        DailySummaryMain dailySummary = new DailySummaryMain();
        InvoiceView invoiceView = new InvoiceView();

        Main main = new Main();

        while (menuChoice != STOP_APP) {
            main.displayMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        // customer
                        customerMenu.customerMainPage();
                        break;
                    case 2:
                        // deliver person
                        deliveryPersonMenu.deliveryPersonMainPage();
                        break;
                    case 3:
                        // delivery area
                        deliveryArea.runDeliveryAreaMenu();
                        break;
                    case 4:
                        // publication
                        publicationMenu.publicationMainPage();
                        break;
                    case 5:
                        // order
                        try {
                            ov.orderMenu();
                        } catch (SQLException | OrderExceptionHandler e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 6:
                        // delivery docket
                        view.deliveryMainPage();
                        break;
                    case 7:
                        //invoice
                        invoiceView.runMenu();
                        break;
                    case 8:
                        // summary
                        try {
                            dailySummary.runSummaryMenu();
                        }
                        catch(SQLException | FileNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 10:
                        // close
                        System.out.println("Program is closing...");
                        DBconnection.cleanup_resources();  // close the connection to the database when finished program
                        break;

                default:
                    System.out.println("You entered an invalid choice, please try again...");
            }
        } else{
            in.nextLine();
            System.out.println("You entered an invalid choice, please try again...");
        }
    }

}

    // Display MAIN MENU
    public static void displayMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1: Customers");
        System.out.println("2: Delivery Person");
        System.out.println("3: Delivery Area");
        System.out.println("4: Publications ");
        System.out.println("5: Order");
        System.out.println("6: Delivery Docket");
        System.out.println("7: Invoice");
        System.out.println("8: Summary");
        System.out.println("10: Exit Application\n");
        System.out.print("Enter your choice: ");
    }
}
