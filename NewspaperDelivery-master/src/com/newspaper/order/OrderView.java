package com.newspaper.order;

import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.Delivery;
import com.newspaper.deliverydocket.DeliveryDocketDB;
import com.newspaper.gui.OrderMainGUI;
import com.newspaper.publication.PublicationView;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Scanner;


public class OrderView {
    Order order = new Order();

    public static void main(String[] args) throws SQLException, OrderExceptionHandler {

        OrderView ov = new OrderView();
        DBconnection.init_db();
        ov.orderMenu();
    }

    public void orderMenu() throws SQLException, OrderExceptionHandler {

        Scanner in = new Scanner(System.in);

        int menuChoice = 0;

        final int STOP_APP = 7;

        while (menuChoice != STOP_APP) {
            displayMainOrderMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        ArrayList<Order> orders = getOrders();
                        printOrdersWithNames(orders);
                        break;
                    case 2:
                        displayByIdOptions();
                        break;
                    case 3:
                        addNewOrder();
                        break;
                    case 4:
                        editOptions();
                        break;
                    case 5:
                        deleteOrder();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("You entered an invalid choice, please try again...");
                }
            } else {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid choice, please try again...");
            }
        }
    }

    public void displayMainOrderMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1: Display all Orders");
        System.out.println("2: Display Order(s) by specific ID");
        System.out.println("3: Add new Order");
        System.out.println("4: Edit Order");
        System.out.println("5: Delete Order");
        System.out.println("6: Return to Main Menu\n");
        System.out.print("Enter your choice: ");
    }

//******************************************************************************************************
// Beginning of display all orders
//******************************************************************************************************

    /**
     * Method that accesses the DB, gets all the order records and returns it in a form of
     * ArrayList of Order objects
     *
     * @return An ArrayList of Orders
     * @throws OrderExceptionHandler is thrown in case of error with DB or validation of the data
     */
    public ArrayList<Order> getOrders() throws OrderExceptionHandler {
        // array list for saving all the objects of the Order class
        ArrayList<Order> orders = new ArrayList<>();

        String query = "Select * from orders order by order_id";
        ResultSet rs;
        try {
            Statement stmt = DBconnection.con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int order_id = rs.getInt("order_id");
                int customer_id = rs.getInt("customer_id");
                int publication_id = rs.getInt("publication_id");
                int freq = rs.getInt("frequency");

                // creating an object of Order class, fill it with data from db
                Order order = new Order(order_id, customer_id, publication_id, freq);

                // add new object to array list
                orders.add(order);
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

            throw new OrderExceptionHandler("Error: failed to read all orders.");
        } catch (OrderExceptionHandler e) {
            throw e;
        }
        return orders;
    }

    public void printOrdersWithNames(ArrayList<Order> orders) throws OrderExceptionHandler {
        System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Order order : orders) {
            int orderId = order.getOrder_id();
            int nameId = order.getCustomer_id();
            String name = getCustomerName(order.getCustomer_id());
            int publicationId = order.getPublication_id();
            String publication = getPublicationByID(order.getPublication_id());
            int frequencyId = order.getFrequency();
            String frequency = convertFrequency(order.getFrequency());
            System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", orderId, nameId, name, publicationId, publication, frequencyId, frequency);
        }
    }


    public String getCustomerName(int customerID) throws OrderExceptionHandler {
        String name = "";

        String query = "SELECT first_name, last_name " +
                "FROM customer\n" +
                "WHERE customer_id = " + customerID + ";";

        ResultSet rs;
        try {
            Statement stmt = DBconnection.con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                name = fName + " " + lName;
            }
        } catch (SQLException sqle) {
            throw new OrderExceptionHandler("Customer does not exist");
        }

        return name;
    }

    public String getPublicationByID(int publicationID) throws OrderExceptionHandler {
        String publicationName = "";

        String query = "SELECT publication_name " +
                "FROM publication\n" +
                "WHERE publication_id = " + publicationID + ";";
        ResultSet rs;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                publicationName = rs.getString("publication_name");
            }

        } catch (SQLException sqle) {
            throw new OrderExceptionHandler("Publication does not exist");
        }
        return publicationName;
    }

    public String convertFrequency(int frequency) throws OrderExceptionHandler, DateTimeException {
        String day = "";

        String query = "Select frequency from orders;";

        ResultSet rs;

        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                day = DayOfWeek.of(frequency).toString();
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } catch (DateTimeException e) {
            throw new DateTimeException("Invalid value for DayOfWeek: " + frequency);
        }

        return day;
    }
//******************************************************************************************************
// Beginning of display a certain order with entered customer ID.
//******************************************************************************************************

    public void displayOrderByCustomerId() throws SQLException {
        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            System.out.println("\nPlease enter the ID of the customer whose order(s) you want to display");
            if (in.hasNextInt()) {

                String query;

                int customerID = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    validateOrderCustomerId(customerID);
                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    query = "Select * from orders where customer_id = " + customerID + " order by order_id;";

                    Statement stmt = DBconnection.con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int order_id = rs.getInt("order_id");
                        int customer_id = rs.getInt("customer_id");
                        int publication_id = rs.getInt("publication_id");
                        int frequency = rs.getInt("frequency");

                        String day = DayOfWeek.of(frequency).toString();

                        System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", order_id, customer_id, getCustomerName(customer_id), publication_id, getPublicationByID(publication_id), frequency, day);
                    }
                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }




    /**
     * Method is checking if customer_id is available in orders table
     *
     * @param customer_id customer id from the db
     * @throws OrderExceptionHandler thrown if order record with this customer_id is not in the db
     */
    public void validateOrderCustomerId(int customer_id) throws OrderExceptionHandler {
        String query = "select count(*) as total from orders where customer_id = " + customer_id + ";";
        ResultSet rs;
        int count = 0;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count == 0) {
                throw new OrderExceptionHandler("Customer does not have an order");
            }
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }
    }
//******************************************************************************************************
// Beginning of display a certain order with entered order ID.
//******************************************************************************************************

    public void displayOrderByOrderId() throws SQLException {
        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            System.out.println("\nPlease enter the ID of the order you want to display");
            if (in.hasNextInt()) {

                String query;

                int orderID = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    order.validateOrderId(orderID);
                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    query = "Select * from orders where order_id = " + orderID + " order by order_id;";

                    Statement stmt = DBconnection.con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int order_id = rs.getInt("order_id");
                        int customer_id = rs.getInt("customer_id");
                        int publication_id = rs.getInt("publication_id");
                        int frequency = rs.getInt("frequency");

                        String day = DayOfWeek.of(frequency).toString();

                        System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", order_id, customer_id, getCustomerName(customer_id), publication_id, getPublicationByID(publication_id), frequency, day);
                    }
                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }

//******************************************************************************************************
// Beginning of display a certain order with entered order ID.
//******************************************************************************************************

    public void displayOrderByPublicationId() throws SQLException {
        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            System.out.println("\nPlease enter the ID of the publication that is on the orders you want to display");
            if (in.hasNextInt()) {

                String query;

                int publicationID = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    order.validatePublicationId(publicationID);
                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    query = "Select * from orders where publication_id = " + publicationID + " order by order_id;";

                    Statement stmt = DBconnection.con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int order_id = rs.getInt("order_id");
                        int customer_id = rs.getInt("customer_id");
                        int publication_id = rs.getInt("publication_id");
                        int frequency = rs.getInt("frequency");

                        String day = DayOfWeek.of(frequency).toString();

                        System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", order_id, customer_id, getCustomerName(customer_id), publication_id, getPublicationByID(publication_id), frequency, day);
                    }
                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }

    //******************************************************************************************************
// Beginning of display a certain order with entered order ID.
//******************************************************************************************************

    public void displayOrderByFrequency() throws SQLException {
        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            System.out.println("\nPlease enter the ID of the frequency that is on the orders you want to display");
            System.out.println("1 = Monday\n2 = Tuesday\n3 = Wednesday\n4 = Thursday\n5 = Friday\n6 = Saturday\n7 = Sunday");
            if (in.hasNextInt()) {

                String query;

                int frequencyID = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    order.validateFrequency(frequencyID);
                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    query = "Select * from orders where frequency = " + frequencyID + " order by order_id;";

                    Statement stmt = DBconnection.con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");
                    System.out.println("-----------------------------------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int order_id = rs.getInt("order_id");
                        int customer_id = rs.getInt("customer_id");
                        int publication_id = rs.getInt("publication_id");
                        int frequency = rs.getInt("frequency");

                        String day = DayOfWeek.of(frequency).toString();

                        System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", order_id, customer_id, getCustomerName(customer_id), publication_id, getPublicationByID(publication_id), frequency, day);
                    }
                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }

    public void displayOrderByIdMenu() {
        System.out.println("\nDisplay by ID Menu");
        System.out.println("1: Display an order by Order ID");
        System.out.println("2: Display order(s) by Customer ID");
        System.out.println("3: Display order(s) by Publication ID");
        System.out.println("4: Display order(s) by Frequency");
        System.out.println("5: Back to Order menu");
        System.out.println("Please enter your choice: ");
        //System.out.println("\n");
    }

    public void displayByIdOptions() throws SQLException {

        Scanner in = new Scanner(System.in);

        int menuChoice = 0;

        final int STOP_APP = 5;

        while (menuChoice != STOP_APP) {
            displayOrderByIdMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        displayOrderByOrderId();
                        break;
                    case 2:
                        displayOrderByCustomerId();
                        break;
                    case 3:
                        displayOrderByPublicationId();
                        break;
                    case 4:
                        displayOrderByFrequency();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("You entered an invalid choice, please try again...");
                }
            } else {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid choice, please try again...");
            }
        }
    }

//******************************************************************************************************
// Beginning of add an order
//******************************************************************************************************

    public void addNewOrder() throws OrderExceptionHandler {
        int cust_id = addNewOrderCustomerID();
        int pub_id = addNewOrderPublicationID();
        int freq = addNewOrderFrequency();

        String insertQuery = "Insert into orders (order_id, customer_id, publication_id, frequency) values (null, " + cust_id + ", " + pub_id + ", " + freq + ")";

        try {
            Statement stmt = DBconnection.con.createStatement();
            stmt.executeUpdate(insertQuery);

            System.out.println("New Order added successfully for " + getCustomerName(cust_id) + " to get the " + getPublicationByID(pub_id) + " on " + convertFrequency(freq) + "'s");

            // generating deliveries for the new order
            DeliveryDocketDB deliveryDocketDB = new DeliveryDocketDB();

            try {
                Order order = new Order(cust_id, pub_id, freq);
                ArrayList<Delivery> deliveries = deliveryDocketDB.generateDeliveriesForNewOrder(order);
                deliveryDocketDB.saveDeliveries(deliveries);
            } catch (OrderExceptionHandler e) {
                e.getMessage();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(insertQuery);
        }
    }



    public int addNewOrderCustomerID() throws OrderExceptionHandler {
        {
            ArrayList<Order> orders = getOrders();
            printOrdersWithNames(orders);

            Scanner in = new Scanner(System.in);
            int customer_id = 0;
            boolean inputValid = false;

            while (!inputValid) {
                System.out.println("Please enter the id of the customer you would like to create a new order for");

                if (in.hasNextInt()) {
                    customer_id = in.nextInt();

                    try {
                        order.validateCustomerId(customer_id);
                        // if validation was successful
                        inputValid = true;

                    } catch (OrderExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    //clear the input buffer and start again
                    in.nextLine();
                    System.out.println("Your entry was invalid, please try again...");
                }
            }
            return customer_id;
        }
    }

    public int addNewOrderPublicationID() throws OrderExceptionHandler {
        {
            PublicationView pv = new PublicationView();
            pv.displayAllPublication();

            Scanner in = new Scanner(System.in);
            int publication_id = 0;
            boolean inputValid = false;

            while (!inputValid) {
                System.out.println("Please enter the id of the publication you would like to add to the new order");
                if (in.hasNextInt()) {
                    publication_id = in.nextInt();
                    try {
                        order.validatePublicationId(publication_id);
                        // if validation was successful
                        inputValid = true;
                    } catch (OrderExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    //clear the input buffer and start again
                    in.nextLine();
                    System.out.println("Your entry was invalid, please try again...");
                }
            }
            return publication_id;
        }
    }

    public int addNewOrderFrequency() throws OrderExceptionHandler {
        {
            Scanner in = new Scanner(System.in);
            int frequency = 0;
            boolean inputValid = false;

            while (!inputValid) {
                System.out.println("Please enter the id of the day you would like the new order to go out on");
                System.out.println("1 = Monday\n2 = Tuesday\n3 = Wednesday\n4 = Thursday\n5 = Friday\n6 = Saturday\n7 = Sunday");

                if (in.hasNextInt()) {
                    frequency = in.nextInt();

                    try {
                        order.validateFrequency(frequency);
                        // if validation was successful
                        inputValid = true;
                    } catch (OrderExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    //clear the input buffer and start again
                    in.nextLine();
                    System.out.println("Your entry was invalid, please enter a number between 1 and 7...");
                }
            }
            return frequency;
        }
    }

//******************************************************************************************************
// Beginning of edit an order
//******************************************************************************************************

    public void updateOrderPublication() throws OrderExceptionHandler, SQLException {

        Order o = new Order();

        int order_id = editOrderByOrderID();
        printOrderById(order_id);

        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {

            PublicationView pv = new PublicationView();
            pv.displayAllPublication();
            System.out.println("");
            System.out.println("Please enter the ID of the new publication you would like to change to");
            if (in.hasNextInt()) {

                int newPublication_id = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    o.validatePublicationId(newPublication_id);


                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    String updateQuery = "Update orders set publication_id = " + newPublication_id + " where order_id = " + order_id;

                    Statement stmt = DBconnection.con.createStatement();
                    stmt.executeUpdate(updateQuery);

                    System.out.println("Publication on the order has been successfully updated to " + newPublication_id + " (" + getPublicationByID(newPublication_id) + ")" + " where Order ID = " + order_id);

                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }


    public void updateOrderPublicationGUI(String orderID, String publicationID) throws SQLException {

        String updateQuery = "Update orders set publication_id = " + publicationID + " where order_id = " + orderID;

        try {
            Statement stmt = DBconnection.con.createStatement();
            stmt.executeUpdate(updateQuery);


        } catch(Exception e) {
            e.getMessage();
        }
    }

    public void printOrderById(int orderID) {

        try {
            // if id is not validated, the rest of the code won't execute
            order.validateOrderId(orderID);


            //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
            String query = "Select * from orders where order_id = " + orderID + " order by order_id;";

            Statement stmt = DBconnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Order to be edited");
            System.out.printf("\n%-10s %-8s %-25s %-8s %-32s %-9s %-35s\n", "Order ID", "Cus ID", "Customer Name", "Pub ID", "Publication Name", "Freq ID", "Frequency");
            System.out.println("-----------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int order_id = rs.getInt("order_id");
                int customer_id = rs.getInt("customer_id");
                int publication_id = rs.getInt("publication_id");
                int frequency = rs.getInt("frequency");

                String day = DayOfWeek.of(frequency).toString();

                System.out.printf("%-10d %-8d %-25s %-8d %-32s %-9d %-35s\n", order_id, customer_id, getCustomerName(customer_id), publication_id, getPublicationByID(publication_id), frequency, day);
            }
        } catch (OrderExceptionHandler | SQLException e) {
            System.out.println(e.getMessage());
        }

}


    public void updateOrderFrequency() throws OrderExceptionHandler, SQLException {
        Order o = new Order();

        int order_id = editOrderByOrderID();
        printOrderById(order_id);

        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {


            System.out.println("");
            System.out.println("Please enter the ID of the new frequency you would like to change to");
            System.out.println("1 = Monday\n2 = Tuesday\n3 = Wednesday\n4 = Thursday\n5 = Friday\n6 = Saturday\n7 = Sunday");
            if (in.hasNextInt()) {

                int newFrequency_id = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    o.validateFrequency(newFrequency_id);

                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    String updateQuery = "Update orders set frequency = " + newFrequency_id + " where order_id = " + order_id;

                    Statement stmt = DBconnection.con.createStatement();
                    stmt.executeUpdate(updateQuery);

                    System.out.println("Frequency on the order has been successfully updated to " + convertFrequency(newFrequency_id) + " where Order ID = " + order_id);

                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }

    public void updateOrderFrequencyGUI(String orderID, String frequency) throws SQLException {

        String updateQuery = "Update orders set frequency = " + frequency + " where order_id = " + orderID;

        try {
            Statement stmt = DBconnection.con.createStatement();
            stmt.executeUpdate(updateQuery);


        } catch(Exception e) {
            e.getMessage();
        }
    }

    public int editOrderByOrderID() throws OrderExceptionHandler {
        {
            ArrayList<Order> orders = getOrders();
            printOrdersWithNames(orders);

            Scanner in = new Scanner(System.in);
            int order_id = 0;
            boolean inputValid = false;

            while (!inputValid) {
                System.out.println("Please enter the id of the order you would like to edit");

                if (in.hasNextInt()) {
                    order_id = in.nextInt();

                    try {
                        order.validateOrderId(order_id);
                        // if validation was successful
                        inputValid = true;

                    } catch (OrderExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    //clear the input buffer and start again
                    in.nextLine();
                    System.out.println("Your entry was invalid, please try again...");
                }
            }
            return order_id;
        }
    }



    public void displayEditOrderMenu() {
        System.out.println("\nEdit Order Menu");
        System.out.println("1: Edit Publication on an order");
        System.out.println("2: Edit Frequency on an order");
        System.out.println("3: Back to main menu");
        System.out.print("Enter your choice: ");
    }

    public void editOptions() throws OrderExceptionHandler, SQLException {

        Scanner in = new Scanner(System.in);

        int menuChoice = 0;

        final int STOP_APP = 3;

        while (menuChoice != STOP_APP) {
            displayEditOrderMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        updateOrderPublication();
                        break;
                    case 2:
                        updateOrderFrequency();
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("You entered an invalid choice, please try again...");
                }
            } else {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid choice, please try again...");
            }
        }
    }


//******************************************************************************************************
// Beginning of delete an order
//******************************************************************************************************

    public void deleteOrder() throws OrderExceptionHandler, SQLException {

        Order o = new Order();
        ArrayList<Order> orders = getOrders();
        printOrdersWithNames(orders);

        Scanner in = new Scanner(System.in);

        boolean isValid = false;

        while (!isValid) {
            System.out.println("Please enter the ID of the order you want to delete");
            if (in.hasNextInt()) {

                String query;

                int order_id = in.nextInt();

                //checks if the entered id is present in the db
                try {
                    // if id is not validated, the rest of the code won't execute
                    o.validateOrderId(order_id);

                    isValid = true;

                    //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                    query = "Delete from orders where order_id = " + order_id + ";";

                    Statement stmt = DBconnection.con.createStatement();
                    stmt.executeUpdate(query);

                    System.out.println("Order deleted where Order ID = " + order_id + ";");
                } catch (OrderExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            } else {
                in.nextLine();
                System.out.println("Input needs to be an integer");
            }
        }
    }

    public void deleteOrderGUI(String orderID) throws SQLException {

        String deleteQuery = "Delete from orders where order_id = " + orderID + ";";

        try {
            Statement stmt = DBconnection.con.createStatement();
            stmt.executeUpdate(deleteQuery);


        } catch(Exception e) {
            e.getMessage();
        }
    }
}