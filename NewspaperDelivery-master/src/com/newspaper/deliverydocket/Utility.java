package com.newspaper.deliverydocket;

import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryArea;
import com.newspaper.order.Order;
import com.newspaper.order.OrderExceptionHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.newspaper.db.DBconnection.stmt;

/**
 * @author  Yuliia Dovbak
 */
public class Utility {

    public String getPublicationByID(int id) throws DeliveryDocketExceptionHandler {
        String publicationName = "";
        if (publicationExists(id)) {
            String query = "SELECT publication_name " +
                    "FROM publication\n" +
                    "WHERE publication_id = " + id + ";";
            ResultSet rs;
            try {
                rs = DBconnection.stmt.executeQuery(query);
                while (rs.next()) {
                    publicationName = rs.getString("publication_name");
                }

            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(query);
            }
        }

        return publicationName;

    }

    public boolean deliveryPersonExists(int deliveryPersonId) {
        String query = "select count(*) as total from delivery_person where delivery_person_id = " + deliveryPersonId + ";";
        return exists(query);
    }

    public boolean deliveryAreaExists(int deliveryAreaId) {
        String query = "select count(*) as total from delivery_area where delivery_area_id = " + deliveryAreaId + ";";
        return exists(query);
    }

    public boolean deliveryPersonActive(int deliveryPersonId) {
        String query = "select count(*) as total from delivery_person where delivery_person_id = " + deliveryPersonId + "" +
                " and delivery_status = 'true';";
        return exists(query);
    }


    /**
     * Method checks if publication exists in the DB by ID
     * @param publicationId The Id of publication that has to be checked
     * @return true if it exists, false if not
     * @throws DeliveryDocketExceptionHandler throwm in case of error
     */
    public boolean publicationExists(int publicationId) {
        String query = "select count(*) as total from publication where publication_id = " + publicationId + ";";
        return exists(query);
    }

    /**
     * Method checks if delivery with specified customer id exists
     * @param customerId customer id that w search for
     * @return true if it exists, false if no
     */
    public boolean customerDeliveryExists(int customerId){
        String query = "select count(*) as total from delivery where customer_id = " + customerId + ";";
        return exists(query);
    }

    /**
     * Method checks if delivery with specified customer id exists
     * @param publicationId customer id that w search for
     * @return true if it exists, false if no
     */
    public boolean publicationDeliveryExists(int publicationId){
        String query = "select count(*) as total from delivery where publication_id = " + publicationId + ";";
        return exists(query);
    }

    /**
     * Method is checking if customer with customerId  already exists
     *
     * @param customerId customer if from the db
     * @return true if this customer already exists in the db, false if not
     */
    public boolean ifCustomerExists(int customerId) {
        String query = "select count(*) as total from customer where customer_id = " + customerId + ";";
        return exists(query);
    }

    public boolean ifInvoicesExist(int month) {
        String query = "select count(*) as total from invoice where MONTH(invoice_date) = " + month +";";
        return exists(query);
    }

    public boolean ifInvoicesExist(int customerID, int month) {
        String query = "select count(*) as total from invoice where MONTH(invoice_date) = " + month +" AND customer_id = " + customerID + ";";
        return exists(query);
    }

    public boolean ifInvoiceExists(int invoiveId) {
        String query = "select count(*) as total from invoice where invoice_id = " + invoiveId + ";";
        return exists(query);
    }


    /**
     * Method is checking if a certain record exists in the database, the only requirement is that
     * SQL statement should only select 'count(*) as total' in it
     * @param query
     * @return
     */
    public boolean exists(String query) {
        // set the flag
        boolean exists = false;

        ResultSet rs;
        int count = - 1;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("total");
                exists = true;
            }
            if(count == 0)
            {
                exists = false;
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

        }

        return exists;
    }

    /**
     * Method that accesses the DB, gets all the order records and returns it in a form of
     * ArrayList of Order objects
     * @return An ArrayList of Orders
     * @throws OrderExceptionHandler is thrown in case of error with DB or validation of the data
     */
    public ArrayList<Order> getOrders() throws OrderExceptionHandler {
        // array list for saving all the objects of the Order class
        ArrayList<Order> orders = new ArrayList<>();

        String query = "Select * from orders";
        ResultSet rs;
        try {
            Statement stmt = DBconnection.con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int customer_id = rs.getInt("customer_id");
                int publication_id = rs.getInt("publication_id");
                int freq = rs.getInt("frequency");

                orders.add(new Order(customer_id, publication_id, freq));
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

    public Order getOrder(int customerId, int publicationId) throws OrderExceptionHandler {
        // array list for saving all the objects of the Order class
        Order order = new Order();

        String query = "Select * from orders where customer_id = " + customerId + "" +
                " and publication_id = " + publicationId + ";";
        ResultSet rs;
        try {
            Statement stmt = DBconnection.con.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int customer_id = rs.getInt("customer_id");
                int publication_id = rs.getInt("publication_id");
                int freq = rs.getInt("frequency");

                order = new Order(customer_id, publication_id, freq);
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

            throw new OrderExceptionHandler("Error: failed to read orders.");
        } catch (OrderExceptionHandler e) {
            throw e;
        }
        return order;
    }

    /**
     * Method returns the day of the week (1-7) for the cpecisied date
     * @param date The date from which we are extracting the day of the week
     * @return A value in range from 1 to 7 representing days from Monday to Sunday
     */
    public int getDayNumber(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day.getValue();
    }

    /**
     * Method is displaying a table of "Delivery Person id " - "Delivery Person name" - " Delivery Area"
     */
    public void displayDeliveryPeopleWithDeliveryAreas() {

        String str = "SELECt delivery_person.delivery_person_id, delivery_person.first_name, delivery_person.last_name,  delivery_area.name\n" +
                "FROM delivery_person, delivery_area\n" +
                "WHERE delivery_person.delivery_person_id = delivery_area.delivery_person_id\n" +
                "ORDER BY delivery_person.delivery_person_id;";

        try {
            Statement stmt = DBconnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(str);
            // Sets the headings and returns the data from the DB
            System.out.printf("\n%-10s %-15s %-15s %-20s \n", "DP ID", "First Name", "Last Name", "Delivery Area");
            System.out.println("-----------------------------------------------------------------------------------");
            while (rs.next()) {
                int delivery_person_id = rs.getInt("delivery_person_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String daName = rs.getString("name");

                System.out.printf("%-10s %-15s %-15s %-20s \n", delivery_person_id, first_name, last_name, daName);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to display all Delivery People.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    public void displayDeliveryAreas() {

        String str = "SELECT * from delivery_area;";

        try {
            Statement stmt = DBconnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(str);
            // Sets the headings and returns the data from the DB
            System.out.printf("\n%-20s %-20s \n", "ID", "Delivery Area Name");
            System.out.println("-------------------------------------------------");
            while (rs.next()) {
                int deliveryAreaId = rs.getInt("delivery_area_id");
                String name = rs.getString("name");

                System.out.printf("%-20s %-20s\n", deliveryAreaId, name);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to display all Delivery Areas.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    /**
     * Method gets all the months in the Delivery table
     * @return An arraylist of months
     */
    public ArrayList<Integer> getMonths() {
        ArrayList<Integer> months = new ArrayList<>();

        // query is getting all unique months from the db
        String query = "SELECT DISTINCT MONTH(delivery_date) as 'month'\n" +
                "FROM delivery;";
        try {
            Statement stmt = DBconnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int month = rs.getInt("month");
                months.add(month);
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }

        return months;
    }


    /**
     * Method gets the name of the Delivery Person
     * @param deliveryPersonId if of the person from the DB
     * @return String value with first name and last name
     * @throws DeliveryDocketExceptionHandler thrown in case of error in SQL connection
     */
    public String getDeliveryPersonName(int deliveryPersonId) throws DeliveryDocketExceptionHandler {

        String name = "";
        // check if delivery person exists
        if (deliveryPersonExists(deliveryPersonId)) {
            String query = "SELECT first_name, last_name\n" +
                    "FROM delivery_person\n" +
                    "WHERE delivery_person_id = " + deliveryPersonId + ";";
            ResultSet rs;
            try {
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    name = rs.getString("first_name") + " " + rs.getString("last_name");
                }

            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(query);
            }
        }
        return name;
    }

    /**
     * Method prints all the deliveries of the customer
     * @param customerId the id of the customer, whose deliveries are printed
     * @throws DeliveryDocketExceptionHandler is thrown in cas of error
     */
    public void displayAllDeliveriesOfCustomer(int customerId) throws DeliveryDocketExceptionHandler {


        // first check if delivery with that id exists in the delivery table
        // if it does, we display the list of deliveries
        // if not, we show a respective msg
        if(customerDeliveryExists(customerId)) {
            String query = "SELECT customer.customer_id, customer.first_name, customer.last_name, publication.publication_name, delivery.delivery_date as 'date of delivery'\n" +
                    "FROM customer, delivery, publication\n" +
                    "WHERE customer.customer_id = delivery.customer_id \n" +
                    "\tAND delivery.publication_id = publication.publication_id\n" +
                    "\tAND customer.customer_id = " + customerId +
                    "\t ORDER BY delivery.delivery_date ASC;";;

            try {
                Statement stmt = DBconnection.con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                // Sets the headings and returns the data from the DB
                System.out.printf("\n%-8s %-20s %-25s %-20s \n", "Cus ID", "Name", "Publication", "Date of delivery");
                System.out.println("---------------------------------------------------------------------------------");
                while (rs.next()) {
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    String publication = rs.getString("publication_name");
                    String date = rs.getString("date of delivery");

                    System.out.printf("%-8s %-20s %-25s %-20s \n", customerId, name, publication, date);
                }

            } catch (SQLException sqle) {
                System.out.println("Error: failed to display all Deliveries");
                System.out.println(sqle.getMessage());
                System.out.println(query);
            }
        }
        else {
            System.out.println("\nCustomer with id " + customerId + " currently does not receive any deliveries");
        }
    }

    public void displayAllDeliveriesOfPublication(int publicationId) {

        // first check if delivery with that id exists in the delivery table
        // if it does, we display the list of deliveries
        // if not, we show a respective msg
        if(publicationDeliveryExists(publicationId)) {
            String query = "SELECT publication.publication_name, customer.first_name, customer.last_name, delivery.delivery_status ,delivery.delivery_date as 'date of delivery'\n" +
                    "FROM customer, delivery, publication\n" +
                    "WHERE customer.customer_id = delivery.customer_id \n" +
                    "\tAND delivery.publication_id = publication.publication_id\n" +
                    "\tAND publication.publication_id = " + publicationId + "" +
                    "\t ORDER BY delivery.delivery_date ASC;";

            try {
                Statement stmt = DBconnection.con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                // Sets the headings and returns the data from the DB
                System.out.printf("\n%-30s %-25s %-20s %-20s\n","Publication", "Customer Name",  "Date of delivery", "Delivery Status");
                System.out.println("---------------------------------------------------------------------------------");
                while (rs.next()) {
                    String name = rs.getString("first_name") + " " + rs.getString("last_name");
                    String publication = rs.getString("publication_name");
                    String date = rs.getString("date of delivery");
                    String deliveryStatus = rs.getString("delivery_status");

                    System.out.printf("%-30s %-25s %-20s %-20s\n", publication, name, date, deliveryStatus);
                }

            } catch (SQLException sqle) {
                System.out.println("Error: failed to display all Deliveries");
                System.out.println(sqle.getMessage());
                System.out.println(query);
            }
        }
        else {
            System.out.println("\nPublication with id " + publicationId + " currently does not receive any deliveries");
        }
    }

    public void displayAllPublications() {
        String query = "select * from publication;";

        try {

            ResultSet rs = stmt.executeQuery(query);

            System.out.printf("\n %-20s %-25s %-20s %-20s \n","Publication ID", "Name",  "Frequency", "Cost");
            System.out.println("---------------------------------------------------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("publication_id");
                String name = rs.getString("publication_name");
                String freq = rs.getString("publication_frequency");
                String cost = rs.getString("publication_cost");

                System.out.printf("%-20d %-25s %-20s %-20s\n", id, name, freq, cost);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to display all Publications");
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }
    }

    /**
     * Method converts date from String to LocalDate
     * @param date String parameter reprsenting date
     * @return LocalDate object containing date from params
     */
    public LocalDate convertDate(String date) {
        // create a formater
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-M-d");
        LocalDate currDate = null;
        // null is acceptable for holiday
        if (date != null) {
            // checking the format of start date
            try {
                currDate = LocalDate.parse(date, formatter );
            } catch (Exception e) {
                throw e;
            }
        }
        return currDate;
    }

    public boolean isInFuture(String date) {
        LocalDate userDate = convertDate(date);
        LocalDate todaysDate = LocalDate.now();
        if (userDate.isAfter(todaysDate)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Method gets the object of delivery area where the delivery person works
     * @param deliveryAreaId the id of the delivery person
     * @return an object of DelivryArea
     * @throws DeliveryDocketExceptionHandler if delivery person with deliveryPersonId is not registered on any area
     */
    public DeliveryArea getDeliveryArea(int deliveryAreaId) throws DeliveryDocketExceptionHandler {

        DeliveryArea area = new DeliveryArea();
        String query = "SELECT *\n" +
                "FROM delivery_area\n" +
                "WHERE delivery_area_id = " + deliveryAreaId + ";";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                deliveryAreaId = rs.getInt("delivery_area_id");
                String name = rs.getString("name");
                int deliveryPersonId = rs.getInt("delivery_person_id");

                area.setId(deliveryAreaId);
                area.setDAreaName(name);
                area.setDeliveryPersonId(deliveryPersonId);
            }
            if (deliveryAreaId == -1) {
                throw new DeliveryDocketExceptionHandler("Delivery area with id " + deliveryAreaId + " does not exist");
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }
        return area;
    }

}
