package com.newspaper.invoice;

import com.newspaper.customer.Customer;
import com.newspaper.customer.CustomerDB;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.*;

import java.io.File;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
/**
 * @author  Yuliia Dovbak
 */
public class InvoiceGenerator {

    public static void main(String[] args) {
        DBconnection.init_db();
        InvoiceGenerator generator = new InvoiceGenerator();
        //generator.generateInvoicesIfNeeded(3);

        //ArrayList<InvoiceItem> deliveries = generator.getAllDeliveriesOfCustomerOfMonth(43, 3);

        generator.createInvoice(43, 3);
        generator.createInvoice(42, 3);
        generator.createInvoice(40, 3);
        generator.createInvoice(415, 3);
        generator.createInvoice2(1,3);
    }

    Utility utility = new Utility();
    DeliveryDocketDB deliveryDocketDB = new DeliveryDocketDB();
    
    
    public Invoice createInvoice(int customerId, int month) {
        Invoice invoice = null;

        // if deliveries for this month don't exist yet, you can create an invoice yt as well
        if (!deliveryDocketDB.deliveriesForThisMonthExist(month)) {
            System.out.println("Deliveries for this month are not available yet. Please generate and deliver them first.");
        }
        else {
            // generate invoices
            generateInvoicesIfNeeded(month);

            try {
                // get the invoice from db
                invoice = getInvoice(customerId,month);

                if (invoice != null) {
                    // get the deliveries
                    ArrayList<InvoiceItem> deliveries = getAllDeliveriesOfCustomerOfMonth(customerId, month);
                    invoice.setInvoiceItems(deliveries);

                    // print invoice to cli
                    System.out.println(invoice);

                    // save to file
                    createInvoiceFile(invoice);
                }
                else {
                    System.out.println("Invoice for this customer does not exist");
                }
            }
            catch (DeliveryDocketExceptionHandler e) {
                System.out.println(e.getMessage());
            }

        }
        return invoice;
    }

    public Invoice createInvoice2(int customerId, int month)
    {
        Invoice invoice = null;

        // if deliveries for this month don't exist yet, you can create an invoice yt as well
        if (!deliveryDocketDB.deliveriesForThisMonthExist(month)) {
        }
        else {
            // generate invoices
            generateInvoicesIfNeeded(month);

            try {
                // get the invoice from db
                invoice = getInvoice(customerId,month);

                if (invoice != null) {
                    // get the deliveries
                    ArrayList<InvoiceItem> deliveries = getAllDeliveriesOfCustomerOfMonth(customerId, month);
                    invoice.setInvoiceItems(deliveries);

                    // print invoice to cli
//                    System.out.println(invoice);

                    // save to file
                    createInvoiceFile(invoice);
                }
            }
            catch (DeliveryDocketExceptionHandler e) {
//                System.out.println(e.getMessage());
            }

        }
        return invoice;
    }

    public ArrayList<InvoiceItem> getAllDeliveriesOfCustomerOfMonth(int customerId, int month) {
        ArrayList<InvoiceItem> invoicItems = new ArrayList<>();

        String sqlQuery = "SELECT customer.customer_id, customer.first_name, customer.last_name, delivery.delivery_date as 'date of delivery', publication.publication_name, count(publication.publication_name) as 'count', publication.publication_cost\n" +
                "FROM customer, delivery, publication\n" +
                "WHERE customer.customer_id = delivery.customer_id \n" +
                "\tAND delivery.publication_id = publication.publication_id\n" +
                "\tAND MONTH(delivery.delivery_date) = " + month + "\n" +
                "\tAND customer.customer_id = " + customerId + "\n" +
                "\tAND delivery.delivery_status=\"delivered\"\n" +
                "\tGROUP BY publication.publication_name;";

        ResultSet rs;
        try {
            PreparedStatement stmt = DBconnection.con.prepareStatement(sqlQuery);
            rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                String name = rs.getString("publication_name");
                double pubCst = rs.getDouble("publication_cost");
                int amt = rs.getInt("count");
                double totalCost = pubCst * amt;

                invoicItems.add(new InvoiceItem(name, pubCst, amt, totalCost));

            }
        }
        catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(sqlQuery);

        }
        return invoicItems;
    }


    public void generateInvoicesIfNeeded(int month) {
        // generate only if invoices dont exist yet
        if (!utility.ifInvoicesExist(month)) {
            // every customer has an invoice, so getting all customers from the DB
            try {
                CustomerDB customerDB = new CustomerDB();
                ArrayList<Customer> customers = customerDB.fetchCustomers();

                for (Customer customer : customers) {
                    // if customer is active, generate invoice for him
                    if (customer.getStatus()) {
                        //generating invoice
                        String sqlQuery = "\tSELECT delivery.delivery_date as 'date of delivery', publication.publication_name, sum(publication.publication_cost + (publication.publication_cost * 0.23)) as 'cost'\n" +
                                "FROM customer, delivery, publication\n" +
                                "WHERE customer.customer_id = delivery.customer_id \n" +
                                "\tAND delivery.publication_id = publication.publication_id\n" +
                                "AND delivery.delivery_status = \"delivered\"" +
                                "\tAND MONTH(delivery.delivery_date) = " + month + "\n" +
                                "AND customer.customer_id = " + customer.getCustomerId() + ";";


                        ResultSet rs;
                        try {
                            PreparedStatement stmt = DBconnection.con.prepareStatement(sqlQuery);
                            rs = stmt.executeQuery(sqlQuery);
                            while (rs.next()) {
                                double totalCost = rs.getDouble("cost");
                                String invoicePaid = "unpaid";
                                String invoiceDelivered = "false";
                                YearMonth thisYearMonth = YearMonth.of(2021, month);
                                LocalDate endDate = thisYearMonth.atEndOfMonth();

                                Invoice invoice = new Invoice(customer.getCustomerId(), endDate.toString(), totalCost, invoicePaid, invoiceDelivered);
                                saveInvoiceToDb(invoice);
                            }

                        } catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                            System.out.println(sqlQuery);

                        } catch (DeliveryDocketExceptionHandler e) {
                            throw e;
                        }
                    }
                }
            }
            catch (CustomerExceptionHandler | DeliveryDocketExceptionHandler e) {
                System.out.println(e.getMessage());
            }

        }
    }


    public void saveInvoiceToDb(Invoice invoice) throws DeliveryDocketExceptionHandler {

        // sql query
        String insertQuery = "INSERT INTO invoice VALUES (null, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = DBconnection.con.prepareStatement(insertQuery);
            pstmt.setInt(1, invoice.getCustomerId());
            pstmt.setString(2, invoice.getInvoiceDate());
            pstmt.setFloat(3, (float) invoice.getPrice());
            pstmt.setString(4, invoice.getInvoicePaid());
            pstmt.setString(5, invoice.getInvoiceDelivered());

            int rows = pstmt.executeUpdate();

            //System.out.println("Adding new invoice record was successful");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(insertQuery);
            throw new DeliveryDocketExceptionHandler("Error: failed to add an invoice record");
        }
    }


    public Invoice getInvoice(int customerId, int month) throws DeliveryDocketExceptionHandler {
        Invoice invoice = null;

        if (utility.ifInvoicesExist(customerId, month)) {
            String query = "select * from invoice\n" +
                    "where customer_id = " +  customerId + "\n" +
                    "and month(invoice_date) = " + month + ";";
            ResultSet rs;
            try {
                rs = DBconnection.stmt.executeQuery(query);
                while (rs.next()) {

                    int invoiceId = rs.getInt("invoice_id");
                    int cusId = rs.getInt("customer_id");
                    String date = rs.getString("invoice_date");
                    double price = rs.getDouble("price");
                    String paid = rs.getString("price_paid");
                    String delivered = rs.getString("is_delivered");

                    invoice = new Invoice(invoiceId, cusId, date, price, paid, delivered);
                }

            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(query);
            }
        }
        else {
            throw new DeliveryDocketExceptionHandler("Invoice for this customer does not exist");
        }

        return invoice;
    }

    public void createInvoiceFile(Invoice invoice) {

        try {
            CustomerDB customerDB = new CustomerDB();
            Customer c = customerDB.getCustomerById(invoice.getCustomerId());

            // create delivery docket text file
            File docketFile = new File("out\\production\\NewspaperDelivery\\com\\newspaper\\invoice\\invoicefiles\\" + invoice.getCustomerId() + "_"+ c.getFirstName() + "_" + c.getLastName() + "_" + invoice.getInvoiceDate() + ".txt");
            //File docketFile = new File( docket.getDeliveryPersonName() + "_" + docket.getDeliveryAreaName() + "_" + docket.getDate() + ".txt");

            try {
                PrintWriter pw = new PrintWriter(docketFile);
                pw.print(invoice);
                pw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }


    }
}
