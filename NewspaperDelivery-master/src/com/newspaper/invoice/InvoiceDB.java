package com.newspaper.invoice;

import com.newspaper.customer.CustomerDB;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.customer.CustomerView;
import com.newspaper.customer.Customer;
import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryArea;
import com.newspaper.deliveryarea.DeliveryAreaDB;
import com.newspaper.deliverydocket.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static com.newspaper.db.DBconnection.stmt;
import static java.lang.Double.parseDouble;

public class InvoiceDB {
    DeliveryAreaDB dav = new DeliveryAreaDB();
    DeliveryArea da = new DeliveryArea();
    Scanner in = new Scanner(System.in);
    Invoice invoice = new Invoice();
    boolean validName = false;
    boolean validDesc = false;
    boolean validEntry = false;
    int id = 0;
    String CusID;

    public void generateInvoice(Statement stmt) throws SQLException, FileNotFoundException {
        PrintWriter writer1;
        String firstName = "";
        String lastName = "";
        String address1 = "";
        String address2 = "";
        String town = "";
        String pubName = "";
        String pubCost = "";
        String deliveryDate = "";

        boolean valid = false;
        getCustomerFromInvoice(stmt);
        System.out.println("Select the invoice which you would like to print: ");
        String invoiceEdit = in.next();
        String str = "Select * from invoice where invoice_id = " + invoiceEdit;
        ResultSet rs1 = stmt.executeQuery(str);

        while (rs1.next()) {
            int invoice_id = rs1.getInt("invoice_id");
            int customer_id = rs1.getInt("customer_id");
            String invoice_date = rs1.getString("invoice_date");
            String price = rs1.getString("price");

            String invoice_paid = rs1.getString("price_paid");
            String str2 = "Select first_name, last_name, address1, address2, town " +
                    "from customer, invoice " +
                    "where invoice.invoice_id = " + invoiceEdit + " AND invoice.customer_id = customer.customer_id;";

            String str3 = "SELECT publication.publication_name, publication.publication_cost, delivery.delivery_date, invoice.is_delivered, customer.first_name, customer.last_name\n" +
                    "FROM delivery, customer, publication, invoice\n" +
                    "WHERE delivery.customer_id = customer.customer_id\n" +
                    "\tAND invoice.is_delivered = 'true'" +
                    "\tAND delivery.publication_id = publication.publication_id\n" +
                    "\tAND customer.customer_id =" + customer_id +
                    "\tAND MONTH(delivery.delivery_date) = " + customer_id;

            if (id > 100 || id < 0) // VALIDATION HERE GETS CUSTOMER NAMES
            {
                System.out.println("ERROR");
            } else {
                try {
                    Statement stmt2 = DBconnection.con.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(str2);
                    while (rs2.next()) {
                        firstName = rs2.getString("first_name");
                        lastName = rs2.getString("last_name");
                        address1 = rs2.getString("address1");
                        address2 = rs2.getString("address2");
                        town = rs2.getString("town");
                    }
                    Statement stmt3 = DBconnection.con.createStatement();
                    ResultSet rs3 = stmt3.executeQuery(str3);
                    while (rs3.next()) {
                        pubName = rs3.getString("publication_name");
                        pubCost = rs3.getString("publication_cost");
                        deliveryDate = rs3.getString("delivery_date");
                    }
                } catch (SQLException sqle) {
                    System.out.println("Error: failed to areas.");
                    System.out.println(sqle.getMessage());
                }
                double priceBeforeVat = parseDouble(price);
                System.out.printf("%-12d %-20s %-15s %-20s %-25s %-20s %-20s %-20s %-20f\n", invoice_id, invoice_date, firstName, lastName, address1, address2, town, price, vatAddition(priceBeforeVat));
                System.out.printf("%-12s %-20s\n", pubName, pubCost);

                writer1 = new PrintWriter(new File("src\\com\\newspaper\\invoice\\invoicefiles\\" + invoiceEdit + ".txt"));
                writer1.printf("\n%-12s %-20s %-15s %-20s %-25s %-20s %-20s %-20s %-20s\n", "Invoice ID", "Invoice Date", "First Name", "Last Name", "Address1", "Address 2", "Town", "Value Before Vat", "Value after Vat");
                writer1.printf("%-12d %-20s %-15s %-20s %-25s %-20s %-20s %-20s %-20f\n", invoice_id, invoice_date, firstName, lastName, address1, address2, town, price, vatAddition(priceBeforeVat));
                writer1.close();
            }
        }
    }

    public void displayAllInvoices(Statement stmt) {
        String str = "Select * from invoice;";
        try {
            ResultSet rs = stmt.executeQuery(str);
            System.out.printf("\n%-20s %-25s %-20s %-20s %-20s\n", "Invoice ID", "Customer ID", "Invoice Date", "Price", "Invoice Status");
            while (rs.next()) {
                int invoice_id = rs.getInt("invoice_id");
                int cus_id = rs.getInt("customer_id");
                String date = rs.getString("invoice_date");
                String price = rs.getString("price");
                String invoiceStatus = rs.getString("price_paid");

                System.out.printf("%-20s %-25s %-20s %-20s %-20s\n", invoice_id, cus_id, date, price, invoiceStatus);
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to invoices.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }

    }

    public void displayAllInvoicesByDate(int month) {
        String query = "select * from invoice where Month(invoice_date) = " + month + ";";
        try {
            ResultSet rs = stmt.executeQuery(query);

            // if result set is not empty, we print the table
            if(rs.next()) {

                System.out.printf("\n%-20s %-25s %-20s %-20s %-20s\n", "Invoice ID", "Customer ID", "Invoice Date", "Price", "Invoice Status");
                while (rs.next()) {
                    int invoice_id = rs.getInt("invoice_id");
                    int cus_id = rs.getInt("customer_id");
                    String date = rs.getString("invoice_date");
                    String price = rs.getString("price");
                    String invoiceStatus = rs.getString("price_paid");

                    System.out.printf("%-20s %-25s %-20s %-20s %-20s\n", invoice_id, cus_id, date, price, invoiceStatus);
                }
            }
            else {
                System.out.println("There are no invoices for month " + month + " yet.");
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to invoices.");
            System.out.println(sqle.getMessage());
            System.out.println(query);
        }

    }

    public int getCustomerFromInvoice(Statement stmt) {
        // print customers
        String cID = "";
        int id = 0;
        try {
            CustomerDB customerDB = new CustomerDB();
            CustomerView view = new CustomerView();
            view.printCustomers(customerDB.fetchCustomers());
        } catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }
        boolean isvalid = false;
        while (!isvalid) {
            System.out.println("Please select the Customer ID to see their invoices.");
                cID = in.nextLine();
            if (!da.validateEntry(cID)) {
                System.out.println();
                validDesc = false;
            } else
            {
                id = Integer.parseInt(cID);
                Utility utility = new Utility();


                    if (utility.ifCustomerExists(id)) {
                        isvalid = true;
                    } else {
                        System.out.println("Customer with id" + id + " does not exist");
                    }

                    String str = "Select * from invoice where customer_id = " + id;
                    try {
                        ResultSet rs = stmt.executeQuery(str);
                        System.out.printf("\n%-20s %-25s %-20s %-20s %-20s\n", "Invoice ID", "Customer ID", "Invoice Date", "Price", "Invoice Paid");
                        while (rs.next()) {
                            int invoice_id = rs.getInt("invoice_id");
                            int customer_id = rs.getInt("customer_id");
                            String invoice_date = rs.getString("invoice_date");
                            String price = rs.getString("price");
                            String invoice_paid = rs.getString("price_paid");
                            System.out.printf("%-20s %-25s %-20s %-20s %-20s\n", invoice_id, customer_id, invoice_date, price, invoice_paid);
                        }
                    } catch (SQLException sqle) {
                        System.out.println("Error: Failed to get Invoice.");
                        System.out.println(sqle.getMessage());
                        System.out.println(str);
                    }
                }


        }
        return id;
    }


    public void getCustomerNameFromId(Statement stmt) {
        System.out.println("Please enter the customer ID: ");
        String id = in.nextLine();
        if (!da.validateEntry(id)) {
            System.out.println();
            validDesc = false;
        } else {
            String str = "Select * from customer where customer_id = " + id;
            try {
                ResultSet rs = stmt.executeQuery(str);
                System.out.printf("\n%-20s %-25s %-20s\n", "Customer ID", "First Name", "Last Name");
                while (rs.next()) {
                    int customer_id = rs.getInt("customer_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    System.out.printf("%-20s %-25s %-20s\n", customer_id, firstName, lastName);
                }
            } catch (SQLException sqle) {
                System.out.println("Error: No customer with that ID.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
        }
    }

    public void getCusAddressFromInvoiceId(Statement stmt) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the Invoice ID: ");
        String id = in.nextLine();

        if (!da.validateEntry(id)) {
            System.out.println();
            validDesc = false;
        } else {

            String str = "Select address1, address2, town " +
                    "from customer, invoice " +
                    "where invoice.invoice_id = " + id + " AND invoice.customer_id = customer.customer_id;";
            try {
                ResultSet rs = stmt.executeQuery(str);
                while (rs.next()) {
                    String add1 = rs.getString("address1");
                    String add2 = rs.getString("address2");
                    String town = rs.getString("town");
                    System.out.printf("%-20s %-25s %-25s\n", add1, add2, town);
                }
            } catch (SQLException sqle) {
                System.out.println("Error: failed to get customer address.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
        }
    }


    public void deleteInvoice(Statement stmt) throws SQLException {
        String str;
        ResultSet rs;
        Scanner in = new Scanner(System.in);
        displayAllInvoices(stmt);
        System.out.println("Please enter the id of the invoice you would like to delete: ");
        String deleteId = in.nextLine();

        if (!da.validateEntry(deleteId)) {
            System.out.println();
            validDesc = false;
        }
        else {
            Statement deletePerson = DBconnection.con.createStatement();
            deletePerson.executeUpdate("delete from invoice where invoice_id =" + deleteId + "");
            System.out.println("Invoice with id: " + deleteId + " has been deleted.");
        }
    }
    public void printPublications(Statement stmt) throws SQLException
    {
        String cusid;
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter customer ID to check for publications customer has received.");
        cusid = in.nextLine();
        if (!da.validateEntry(cusid)) {
            System.out.println();
            validDesc = false;
        }
        else {
            String str1 = "SELECT publication.publication_name, publication.publication_cost, delivery.delivery_date, invoice.is_delivered, customer.first_name, customer.last_name\n" +
                    "FROM delivery, customer, publication, invoice\n" +
                    "WHERE delivery.customer_id = customer.customer_id\n" +
                    "\tAND invoice.is_delivered = 'true'"+
                    "\tAND delivery.publication_id = publication.publication_id\n" +
                    "\tAND customer.customer_id =" + cusid +  ";";
            ResultSet rs = stmt.executeQuery(str1);
            System.out.printf("\n%-25s %-25s %-5s\n", "Publication Name", "Publication Cost", "Delivery Date");
            while (rs.next())
            {
                String pubName = rs.getString("publication_name");
                String pubCost = rs.getString("publication_cost");
                String deliveryDate = rs.getString("delivery_date");
                System.out.printf("%-25s %-25s %-5s\n", pubName, pubCost, deliveryDate);
            }
        }
    }

    public void paidUpdate(Statement stmt) throws SQLException //TODO Validate
    {
        boolean valid = false;
        getCustomerFromInvoice(stmt);
        int invoiceId = 0;

        while (!valid) {
            System.out.println("Select the invoice ID which you would like to edit: ");
            if (in.hasNextInt()) {
                invoiceId = in.nextInt();
                Utility utility = new Utility();
                if (utility.ifInvoiceExists(invoiceId)) {
                    valid = true;
                } else {
                    displayAllInvoices(stmt);
                    System.out.println("Invoice with id " + invoiceId + " does not exist. Please choose the option from the table above");
                }
            } else {
                in.next();
                System.out.println("Invoice ID can have numbers only");
            }
        }


        String str = "Select * from invoice where invoice_id =" + invoiceId;
        valid = false;
        try {
            ResultSet rs = stmt.executeQuery(str);

            System.out.printf("\n%-20s %-25s %-20s %-20s %-20s\n", "Invoice ID", "Customer ID", "Invoice Date", "Price", "Invoice Paid");

            while (rs.next()) {
                int invoice_id = rs.getInt("invoice_id");
                int customer_id = rs.getInt("customer_id");
                String invoice_date = rs.getString("invoice_date");
                String price = rs.getString("price");
                String invoice_paid = rs.getString("price_paid");
                System.out.printf("%-20s %-25s %-20s %-20s %-20s\n", invoice_id, customer_id, invoice_date, price, invoice_paid);

                while (!valid) {
                    System.out.println("Do you want to change the Invoice Status to paid?");
                    System.out.println("Press 1 for Paid:");
                    System.out.println("Press 2 for Unpaid:");
                    String yesNo = in.next();

                    if (yesNo.equals("1")) {
                        String setPaid = "update invoice set price_paid = 'paid' where invoice_id =" + invoiceId + ";";
                        stmt.executeUpdate(setPaid);
                        System.out.println("Invoice " + invoice_id + " for customer " + id + " has been set to paid.");
                        valid = true;

                    } else if (yesNo.equals("2")) {
                        String setUnpaid = "update invoice set price_paid = 'unpaid' where invoice_id =" + invoiceId + ";";
                        stmt.executeUpdate(setUnpaid);
                        System.out.println("Invoice " + invoice_id + " for customer " + id + " has been set to unpaid.");
                        valid = true;
                    } else {
                        System.out.println("Sorry that was not an option, please try again: ");
                    }
                }
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public double vatAddition(double taxfree)
    {
        double aftervat;
        aftervat = ((taxfree / 100) * 23) + taxfree;
        double rounded = Math.round(aftervat * 100.0) / 100.0;
        return rounded;
    }


    public void printInvoice(int invoiceId) {

        String str = "Select * from invoice where invoice_id =" + invoiceId;
        try {
            ResultSet rs = stmt.executeQuery(str);

            System.out.printf("\n%-20s %-25s %-20s %-20s %-20s\n", "Invoice ID", "Customer ID", "Invoice Date", "Price", "Invoice Paid");

            while (rs.next()) {
                int invoice_id = rs.getInt("invoice_id");
                int customer_id = rs.getInt("customer_id");
                String invoice_date = rs.getString("invoice_date");
                String price = rs.getString("price");
                String invoice_paid = rs.getString("price_paid");
                System.out.printf("%-20s %-25s %-20s %-20s %-20s\n", invoice_id, customer_id, invoice_date, price, invoice_paid);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int getInvoiceId(int customerId) {
        boolean valid = false;
        int invoiceId = 0;

        while (!valid) {
            System.out.println("Select the invoice ID which you would like to edit: ");
            if (in.hasNextInt()) {
                invoiceId = in.nextInt();
                Utility utility = new Utility();
                if (utility.ifInvoiceExists(invoiceId)) {
                    valid = true;
                }
                else {
                    System.out.println("Invoice with id " + invoiceId + " does not exist. Please choose the option from the table above");
                }
            }
            else {
                in.next();
                System.out.println("Invoice ID can have numbers only");
            }
        }
        return invoiceId;
    }

    // BII6 VIEW THE INVOICE (INVOICE NUMBER, CUSTOMER NAME, CUSTOMER ADDRESS, LIST OF PUBLICATIONS)
    public void displayInvoiceID(Statement stmt) throws SQLException
    {
        int cusid;
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter customer ID to find Invoce ID.");
        cusid = in.nextInt();
        String str1 = "SELECT customer.customer_id, invoice.invoice_id\n" +
                "FROM invoice, customer\n" +
                "WHERE invoice.customer_id = customer.customer_id\n" +
                "\tAND customer.customer_id =" + cusid;

        if (cusid > 100 || cusid < 0) {
            System.out.println("There was an error");
        } else {
            Statement printInvoiceID = DBconnection.con.createStatement();
            ResultSet rs = stmt.executeQuery(str1);
            while (rs.next()) {
                String cusnum = rs.getString("customer_id");
                String invoicenum = rs.getString("invoice_id");
                System.out.printf("%-25s %-25s\n", cusnum, invoicenum);
            }

        }
    }

//    public void getInvoiceFromCustomerName (String firstName, String lastName)
//    {
//        System.out.println("Please Enter Customer First Name: ");
//        String fName = in.nextLine();
//
//            String str = "Select * from customer where customer_id = " + id;
//            try {
//                ResultSet rs = stmt.executeQuery(str);
//                System.out.printf("\n%-20s %-25s %-20s\n", "Customer ID", "First Name", "Last Name");
//                while (rs.next()) {
//                    int customer_id = rs.getInt("customer_id");
//                    String firstName = rs.getString("first_name");
//                    String lastName = rs.getString("last_name");
//                    System.out.printf("%-20s %-25s %-20s\n", customer_id, firstName, lastName);
//                }
//            } catch (SQLException sqle) {
//                System.out.println("Error: Customers.");
//                System.out.println(sqle.getMessage());
//                System.out.println(str);
//            }
//        }
//    }

    public int getCustomerIDFromName() {
        int customerId = 0;
        System.out.println("Please Enter Customer First Name: ");
        String firstName = in.nextLine();
        if (!da.validateDeliveryAreaName(firstName)) {
            System.out.println();
            validDesc = false;
        }
        else {
            System.out.println("Please Enter Customer Last Name: ");
            String lastName = in.nextLine();
            if(!da.validateDeliveryAreaName(lastName)) {
                System.out.println();
                validDesc = false;
            }
            else{
            String str = "select * from customer where first_name = '"+firstName+"' AND last_name = '"+lastName+"';";
            try {
                ResultSet rs = stmt.executeQuery(str);
                while (rs.next()) {
                    customerId = rs.getInt("customer_id");

                }
            } catch (SQLException sqle) {
                System.out.println("Error: No customer with that ID.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
            }
        }
        return customerId;
    }

    public static void displayMainMenu() {
        System.out.println("\n Invoice Menu ");
        System.out.println("1: Create Invoice");
        System.out.println("2: Read Invoice");
        System.out.println("3: Edit Invoice (paid or not paid)");
        System.out.println("4: Delete Invoice");
        System.out.println("5: Get Customer Name from Invoice ID");
        System.out.println("6: Get Customer Address from Invoice");
        System.out.println("7: See all invoices of a customer by id");
        System.out.println("8: See all invoices of a customer by name");
        System.out.println("9: See all invoices by date");
        System.out.println("10: Return to Main Menu");
        System.out.println("\nPlease, enter your choice: ");
    }

    public static void invoiceView() {
        System.out.println("\n Invoice View Menu");
        System.out.println("1. View Invoice Number");
        System.out.println("2. View Customer Name");
        System.out.println("3. View Customer Address");
        System.out.println("4. View the Publications");
    }

    public static void displayUpdateMenu() {
        System.out.println("\n Main Menu ");
        System.out.println("1: Get a customers com.newspaper.invoice.Invoice");
        System.out.println("2: Get com.newspaper.customer.Customer Name");
        System.out.println("3: Get com.newspaper.customer.Customer's Address from com.newspaper.invoice.Invoice.");
        System.out.println("4: Update Delivery Area Name");
        System.out.println("5: Delete a Delivery Area");
        System.out.println("6: Return to Main Menu");
    }
}

