package com.newspaper.deliverydocket;
/**
 * @author
 */

import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryArea;
import com.newspaper.invoice.InvoiceGenerator;
import com.newspaper.order.Order;
import com.newspaper.order.OrderExceptionHandler;

import java.io.File;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import static com.newspaper.db.DBconnection.stmt;

/**
 * @author Yuliia Dovbak
 */
public class DeliveryDocketDB {


    public static void main(String[] args) throws DeliveryDocketExceptionHandler {


    }

    private Utility ut;

    public DeliveryDocketDB() {
        ut = new Utility();
    }

    /**
     * Creates delivery dockets for a delivery person on specfied date
     *
     * @param deliveryPersonId the id of the delivery person
     * @param date             String representing date "2021-03-25"
     * @return an object of DeliveryDocket class with delivery area, delivery person and respective deliveries
     * @throws DeliveryDocketExceptionHandler is thrown in case of error
     */
    public DeliveryDocket createDeliveryDocketFor(int deliveryPersonId, String date) throws DeliveryDocketExceptionHandler {

        // get the delivery area id where the delivery person is working
        DeliveryArea area = getDeliveryArea(deliveryPersonId);

        // get all deliveries for delivery docket
        ArrayList<DeliveryItem> deliveries = getAllDeliveryItemsFor(area.getId(), date);

        String deliveryPersonName = ut.getDeliveryPersonName(deliveryPersonId);

        // create empty delivery docket object
        DeliveryDocket docket = new DeliveryDocket(deliveries, date, area.getId(), area.getDAreaName(), deliveryPersonName);
        return docket;
    }

    /**
     * Takes the DeliveryDocket object and saves it in a file
     *
     * @param docket the object of DeliveryDocket that has to be saved
     */
    public void createDeliveryDocketFile(DeliveryDocket docket) {

        // create delivery docket text file
        File docketFile = new File("src\\com\\newspaper\\deliverydocket\\deliverydocketfiles\\" + docket.getDeliveryPersonName() + "_" + docket.getDeliveryAreaName() + "_" + docket.getDate() + ".txt");
        //File docketFile = new File( docket.getDeliveryPersonName() + "_" + docket.getDeliveryAreaName() + "_" + docket.getDate() + ".txt");

        try {
            PrintWriter pw = new PrintWriter(docketFile);
            pw.print(docket);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteFileIfExists(String textFileName) {

        File myObj = new File("src\\com\\newspaper\\deliverydocket\\deliverydocketfiles\\" + textFileName);
        if (myObj.delete()) {
            System.out.println("File: " + myObj.getName() + " was deleted successfully");
        } else {
            System.out.println("File " + myObj.getName() + " was not found.");
        }
    }

    /**
     * Method fetches all deliveries of a specified delivery area and date from the DB
     *
     * @param deliveryAreaId parameter of search for deliveries
     * @param date           parameter of search for deliveries
     * @return an array lost of all deliveries
     * @throws DeliveryDocketExceptionHandler is thrown in case of error
     */
    public ArrayList getAllDeliveryItemsFor(int deliveryAreaId, String date) throws DeliveryDocketExceptionHandler {

        // first convert the date into Date object
        LocalDate currDate = ut.convertDate(date);
        // create lists of delivery items
        ArrayList<DeliveryItem> deliveries = new ArrayList<>();

        // check if date is not null
        if (currDate != null) {

            // check if its the first day of the month
            // if it is, delivery docket should include invoices
            if (currDate.getDayOfMonth() == 1) {
                int prevMonth = currDate.getMonthValue() - 1;
                ArrayList<InvoiceDeliveryItem> invoices = getInvoicesForDeliveryArea(deliveryAreaId, prevMonth);
                deliveries.addAll(invoices);
            }

            // add all publications
            ArrayList<PublicationDeliveryItem> publications = getPublicationDeliveriesForDeliveryArea(deliveryAreaId, date);
            deliveries.addAll(publications);
        }
        return deliveries;
    }


    /**
     * Method fetches all invoices for specified delivery area
     *
     * @param deliveryAreaId invoices will be fetched for this delivery area
     * @param month          invoices will be fetched for this delivery month
     * @return an array list of invoices
     * @throws DeliveryDocketExceptionHandler is thrown in case of error
     */
    public ArrayList getInvoicesForDeliveryArea(int deliveryAreaId, int month) throws DeliveryDocketExceptionHandler {
        ArrayList<InvoiceDeliveryItem> invoices = new ArrayList<>();

        String query = "SELECT invoice.invoice_id,\n" +
                "\t\tcustomer.customer_id,   \n" +
                "\t\tcustomer.first_name, \n" +
                "\t\tcustomer.last_name, \n" +
                "\t\tcustomer.address1,\n" +
                "\t\tcustomer.address2, \n" +
                "\t\tcustomer.town, \n" +
                "\t\tinvoice.invoice_date,  \n" +
                "\t\tinvoice.is_delivered,\n" +
                "\t\tdelivery_area.name\n" +
                "FROM invoice, customer, delivery_area\n" +
                "WHERE invoice.customer_id = customer.customer_id\n" +
                "\tAND customer.delivery_area_id = delivery_area.delivery_area_id\n" +
                "\tAND MONTH(invoice.invoice_date) = " + month + "\n" +
                "\tAND  delivery_area.delivery_area_id = " + deliveryAreaId + ";";
        ResultSet rs;
        try {
            PreparedStatement stmt = DBconnection.con.prepareStatement(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int invoiceId = rs.getInt("invoice.invoice_id");
                int customerId = rs.getInt("customer.customer_id");
                String customerName = rs.getString("customer.first_name") + " " + rs.getString("customer.last_name");
                String customerAddress = rs.getInt("customer.address1") + " " + rs.getString("customer.address2") + ", " + rs.getString("customer.town");
                String deliveryStatus = rs.getString("invoice.is_delivered");

                // in database we have "delivered or nor delivered" values for delivery status.
                // Here we need to convert it into true or false for easier manipulation with the data
                boolean isDelivered = false;
                if (deliveryStatus.equals("true")) {
                    isDelivered = true;
                }

                invoices.add(new InvoiceDeliveryItem(invoiceId,
                        customerId,
                        customerName,
                        customerAddress,
                        isDelivered));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

            throw new DeliveryDocketExceptionHandler("Error: failed to read deliveries.");
        } catch (DeliveryDocketExceptionHandler e) {
            throw e;
        }

        return invoices;
    }

    // get all deliveries for delivery docket

    /**
     * Fetches all deliveries for delivery docket
     *
     * @param deliveryAreaId publications will be fetched for this delivery area
     * @param day            publications will be fetched for this day
     * @return an array list of all publication deliveries
     * @throws DeliveryDocketExceptionHandler is thrown in case of error
     */
    public ArrayList getPublicationDeliveriesForDeliveryArea(int deliveryAreaId, String day) throws DeliveryDocketExceptionHandler {
        ArrayList<PublicationDeliveryItem> deliveries = new ArrayList<>();

        String query = "SELECT  delivery.delivery_id, delivery.delivery_date, customer.customer_id, customer.first_name, customer.last_name, customer.address1, customer.address2, customer.town, delivery.publication_id, publication.publication_name , delivery.delivery_status, delivery_area.name\n" +
                "FROM publication, customer, delivery, delivery_area\n" +
                "WHERE delivery.publication_id = publication.publication_id\n" +
                " AND delivery.customer_id = customer.customer_id\n" +
                " AND customer.delivery_area_id = delivery_area.delivery_area_id\n" +
                " AND customer.delivery_area_id = " + deliveryAreaId + "\n" +
                " AND delivery.delivery_date = \"" + day + "\"\n" +
                " GROUP BY delivery.delivery_id;";
        ResultSet rs;
        try {
            PreparedStatement stmt = DBconnection.con.prepareStatement(query);
            rs = stmt.executeQuery(query);
            while (rs.next()) {

                int deliveryId = rs.getInt("delivery.delivery_id");
                int publicationId = rs.getInt("delivery.publication_id");
                int customerId = rs.getInt("customer.customer_id");
                String customerName = rs.getString("customer.first_name") + " " + rs.getString("customer.last_name");
                String customerAddress = rs.getInt("customer.address1") + " " + rs.getString("customer.address2") + ", " + rs.getString("customer.town");
                String deliveryStatus = rs.getString("delivery.delivery_status");
                boolean isDelivered = false;
                if (deliveryStatus.equals("delivered")) {
                    isDelivered = true;
                }

                deliveries.add(new PublicationDeliveryItem(deliveryId,
                        publicationId,
                        customerId,
                        customerName,
                        customerAddress,
                        isDelivered));
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

            throw new DeliveryDocketExceptionHandler("Error: failed to read deliveries.");
        }
        return deliveries;
    }


    /************************************************************************************
     GENERATION OF DELIVERIES
     *************************************************************************************/


    /**
     * Method checks if any generation of new delivery records is needed
     *
     * @param date method checks the existence of deliveries with this date
     */
    public void generateDeliveriesIfNeeded(String date) {

        // convert the String date to LocalDate format
        LocalDate deliveryDate = ut.convertDate(date);

        // get the month out of the date
        int month = deliveryDate.getMonthValue();

        // generating invoices
        InvoiceGenerator generator = new InvoiceGenerator();
        generator.generateInvoicesIfNeeded(month);

        // check if deliveries for this month weren't generated before
        if (!deliveriesForThisMonthExist(month)) {

            // generating delivery records and saving them in the DB
            ArrayList<Delivery> deliveries = generateDeliveriesForMonth(month);
            saveDeliveries(deliveries);
        }
    }


    /**
     * Generate deliveries for next month using the orders in the DB
     *
     * @param month for this month deliveries will be generated
     * @return an array list of all deliveries that were generated from the DB
     */
    public ArrayList<Delivery> generateDeliveriesForMonth(int month) {

        // delivery record consist of id, customerId, publicationID,delivery_date, status
        // this list will contain all generated deliveries
        ArrayList<Delivery> deliveryItems = new ArrayList<>();

        try {
            // get list of orders to be able to create deliveries
            ArrayList<Order> orders = ut.getOrders();
            String deliveryStatus = "not delivered"; // default value
            // padding month to always be 2 digits (e.g. 1 > 01, 2 > 02)
            String m;
            if (String.valueOf(month).length() == 1) {
                m = "0" + month;
            } else {
                m = "" + month;
            }
            LocalDate startDate = ut.convertDate("2021-" + m + "-01");
            YearMonth thisYearMonth = YearMonth.of(2021, month);
            LocalDate endDate = thisYearMonth.atEndOfMonth();
            // loop through orders
            for (Order order : orders) {
                int customer_id = order.getCustomer_id();
                int publication_id = order.getPublication_id();
                int freq = order.getFrequency();
                // this loop is starting and the beginning of the month,
                // on every day it checks if that day corresponds to frequency
                // if yes, the new Delivery with that date is added to the DB
                for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                    // check if date corresponds to frequency
                    if (ut.getDayNumber(date) == freq) {
                        deliveryItems.add(new Delivery(customer_id, publication_id, date, deliveryStatus));
                    }
                }
            }
        } catch (OrderExceptionHandler e) {
            System.out.println(e.getMessage());
        }
        return deliveryItems;
    }


    /**
     * Generates the deliveries for the order in parameters. Is used when user creates a new order in the program
     *
     * @param order object storing info about new order
     * @return an array list of all deliveries that were generated from the new order
     */
    public ArrayList<Delivery> generateDeliveriesForNewOrder(Order order) {

        // delivery record consist of id, customerId, publicationID,delivery_date, status
        // this list will contain all generated deliveries
        ArrayList<Delivery> deliveryItems = new ArrayList<>();

        String deliveryStatus = "not delivered"; // default value
        // get all months that we need to generate deliveries for
        // which is every month data from today`s date
        ArrayList<Integer> months = ut.getMonths();
        //System.out.println("Months" + months);
        for (Integer month : months) {

            // determine start date and end date of each month
            String m;
            if (String.valueOf(month).length() == 1) {
                m = "0" + month;
            } else {
                m = "" + month;
            }
            LocalDate startDate = ut.convertDate("2021-" + m + "-01");
            YearMonth thisYearMonth = YearMonth.of(2021, month);
            LocalDate endDate = thisYearMonth.atEndOfMonth();

            //System.out.println("Start date" + startDate);
            //System.out.println("End date" + endDate);

            int customer_id = order.getCustomer_id();
            int publication_id = order.getPublication_id();
            int freq = order.getFrequency();
            // this loop is starting and the beginning of the month,
            // on every day it checks if that day corresponds to frequency
            // if yes, the new Delivery with that date is added to the DB
            for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                // check if date corresponds to frequency
                if (ut.getDayNumber(date) == freq) {
                    deliveryItems.add(new Delivery(customer_id, publication_id, date, deliveryStatus));
                }
            }

        }
        return deliveryItems;
    }

    /**
     * This method is saving the deliveries to the DB
     *
     * @param deliveries an array list of all deliveries that should be inserted into Delivery table in the DB
     */
    public void saveDeliveries(ArrayList<Delivery> deliveries) {
        // run saveDelivery( method on every delivery in the ArrayList)
        try {
            for (Delivery d : deliveries) {
                saveDelivery(d);
            }
        } catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method creates an INSERT SQL query for the delivery and executes the update
     *
     * @param delivery object storing all the data that should be saved in the DB
     * @throws DeliveryDocketExceptionHandler is thrown in case of mistake with the DB connection
     */
    public void saveDelivery(Delivery delivery) throws DeliveryDocketExceptionHandler {

        // sql query
        String insertQuery = "INSERT INTO delivery VALUES (null, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = DBconnection.con.prepareStatement(insertQuery);
            pstmt.setInt(1, delivery.getCustomerId());
            pstmt.setInt(2, delivery.getPublicationId());
            pstmt.setString(3, delivery.getDate());
            pstmt.setString(4, delivery.getDeliveryStatus());

            int rows = pstmt.executeUpdate();

            //System.out.println("Adding new delivery record was successful");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(insertQuery);
            throw new DeliveryDocketExceptionHandler("Error: failed to add a delivery record");
        }

    }

    /**
     * Method checks if deliveries for the specified month already exist in the DB
     *
     * @param month integer representing month of the year (1 - Jan, 2 - Feb ... )
     * @return true if deliveries exist, false if not
     */
    public boolean deliveriesForThisMonthExist(int month) {
        boolean exists = false;

        String query = "SELECt count(*) as total\n" +
                "FROM delivery\n" +
                "WHERE MONTH(delivery_date) = " + month + ";";
        ResultSet rs;
        int count = -1;
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count > 0) {
                exists = true;
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

        }

        return exists;
    }

    /**
     * Method gets the object of delivery area where the delivery person works
     *
     * @param deliveryPersonId the id of the delivery person
     * @return an object of DelivryArea
     * @throws DeliveryDocketExceptionHandler if delivery person with deliveryPersonId is not registered on any area
     */
    public DeliveryArea getDeliveryArea(int deliveryPersonId) throws DeliveryDocketExceptionHandler {

        DeliveryArea area = new DeliveryArea();
        String query = "SELECT *\n" +
                "FROM delivery_area\n" +
                "WHERE delivery_person_id = " + deliveryPersonId + ";";
        ResultSet rs;
        int deliveryAreaId = -1;
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                deliveryAreaId = rs.getInt("delivery_area_id");
                String name = rs.getString("name");

                area.setId(deliveryAreaId);
                area.setDAreaName(name);
            }
            if (deliveryAreaId == -1) {
                throw new DeliveryDocketExceptionHandler("Delivery person with id " + deliveryPersonId + " is inactive");
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }
        return area;
    }


    /************************************************************************************
     UPDATING OF DELIVERIES
     *************************************************************************************/

    public int isFullyDelivered(int deliveryPersonId, String date) throws DeliveryDocketExceptionHandler {
        int allDelivered = 1;    // default
        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = getDeliveryArea(deliveryPersonId);

            // get all deliveries for delivery docket
            ArrayList<DeliveryItem> deliveries = getAllDeliveryItemsFor(area.getId(), date);

            if (deliveries.size() == 0)
            {
                allDelivered = 0;
            }
            else {
                for (DeliveryItem item : deliveries) {
                    if (!item.isDelivered()) {
                        allDelivered = -1;
                    }
                }
            }

        } catch (DeliveryDocketExceptionHandler e) {
            throw e;
        }

        // 1 if all delivered, -1 if at least one is not delivered, 0 if no deliveries
        return allDelivered;
    }

    /**
     * Method is called from view and updates the delivery status to 'delivered' of
     * all the deliveries that are included in the delivery docket
     *
     * @param deliveryPersonId the delivery person that delivers that delivery docket
     * @param date             date of delivery docket
     * @throws DeliveryDocketExceptionHandler is case of exception
     */
    public void updateDeliveriesStatus(int deliveryPersonId, String date) throws DeliveryDocketExceptionHandler {
        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = getDeliveryArea(deliveryPersonId);

            // get all deliveries for delivery docket
            ArrayList<DeliveryItem> deliveries = getAllDeliveryItemsFor(area.getId(), date);

            // update the db
            changeDeliveriesStatusToDelivered(deliveries);

            // update the delivery docket file
            deliveries = getAllDeliveryItemsFor(area.getId(), date);
            DeliveryDocket docket = new DeliveryDocket(deliveries, date, area.getId(), area.getDAreaName(), ut.getDeliveryPersonName(deliveryPersonId));
            System.out.println(docket);
            createDeliveryDocketFile(docket);
        } catch (DeliveryDocketExceptionHandler e) {
            throw e;
        }

    }


    /**
     * Method updates all delivery records in the database and sets delivery_status to 'delivered'
     *
     * @param deliveries list of deliveries that are updated in the db
     * @throws DeliveryDocketExceptionHandler is thrown if there is an SQL error
     */
    public void changeDeliveriesStatusToDelivered(ArrayList<DeliveryItem> deliveries) throws DeliveryDocketExceptionHandler {
        // list may contain both invoices and publications
        for (DeliveryItem item : deliveries) {
            updateDeliveryStatus(item, "delivered");
        }
    }

    /**
     * Updates the delivery item in the db
     *
     * @param item the item that has to be updated
     * @throws DeliveryDocketExceptionHandler thrown in case of error
     */
    public void updateDeliveryStatus(DeliveryItem item, String status) throws DeliveryDocketExceptionHandler {

        boolean isDelivered = status.equals("delivered");
        if (item.getType().equals("invoice")) {
            // invoice update
            String updateQuery = "UPDATE invoice\n" +
                    "SET is_delivered='" + isDelivered +"'\n" +
                    "WHERE invoice_id=" + item.getId();

            try {
                stmt.executeUpdate(updateQuery);
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(updateQuery);
                throw new DeliveryDocketExceptionHandler("Failed to update invoice delivery record");
            }
        } else if (item.getType().equals("publication")) {
            // publication delivery update
            String updateQuery = "UPDATE delivery\n" +
                    "SET delivery_status='" + status + "'\n" +
                    "WHERE delivery_id=" + item.getDeliveryId();

            try {
                stmt.executeUpdate(updateQuery);
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(updateQuery);
                throw new DeliveryDocketExceptionHandler("Failed to update publication delivery record");
            }
        }

    }

}
