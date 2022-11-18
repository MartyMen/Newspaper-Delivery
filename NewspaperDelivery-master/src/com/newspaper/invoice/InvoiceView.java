package com.newspaper.invoice;

import com.newspaper.customer.CustomerDB;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.customer.CustomerView;
import com.newspaper.db.DBconnection;
import com.newspaper.deliverydocket.DeliveryView;
import com.newspaper.deliverydocket.Utility;

import java.sql.SQLException;
import java.util.Scanner;

public class InvoiceView {

    public static void main(String[] args) {
        InvoiceView view = new InvoiceView();
        DBconnection.init_db();
        view.runMenu();
    }

    private Utility utility = new Utility();
    private DeliveryView helper = new DeliveryView();
    Scanner in = new Scanner(System.in);
    InvoiceGenerator invoiceDB = new InvoiceGenerator();

    public void runMenu() {
        InvoiceDB invoice = new InvoiceDB();

        // Variable used to store menu choice.
        int menuChoice = 0;

        // value that wil close the application.
        final int STOP_APP = 10;

        while (menuChoice != STOP_APP) {
            invoice.displayMainMenu();
            if (in.hasNextInt()) {
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        // create invoice
                        createInvoice();
                        break;
                    case 2:
                        // read invoice //was CreateInvoice;
                        createInvoice();
                        break;

                    case 3:
                        // update invoice
                        try {
                            invoice.paidUpdate(DBconnection.stmt);
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        // delete invoice
                        deleteInvoice();
                        break;

                    case 5:
                        invoice.getCustomerNameFromId(DBconnection.stmt);
                        break;

                    case 6:
                        invoice.getCusAddressFromInvoiceId(DBconnection.stmt);
                        break;

                    case 7:
                        seeCustomerInvoices();
                        break;
                    case 8:
                        seeInvoiceByCusName();
                        break;
                    case 9:
                        seeInvoiceByDate();
                        break;
                }
            } else {
                in.nextLine();
                System.out.println("You entered an invalid choice please try again.");
            }
        }
    }


    public void createInvoice() {
        // 1. Ask user to enter id of the customer
        // 2. Ast user to enter the month he wants to create invoice for
        // 3. Run generation of invoices for that month if its not there yet
        // 4. Create invoice file, save in file and output to console window
        //print customers

        try {
            CustomerDB customerDB = new CustomerDB();
            CustomerView view = new CustomerView();
            view.printCustomers(customerDB.fetchCustomers());

            int customerId = helper.askUserToEnterCustomerID();
            int month = askUserToEnterMonth();

            invoiceDB.createInvoice(customerId, month);
        } catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }

    }

    public int askUserToEnterMonth() {
        boolean isValid = false;
        int month = -1;
        // getting month
        while (!isValid) {
            System.out.println("\nEnter the month (1 - 12), deliveries of which will be included in invoice: ");
            in.nextLine();
            if (in.hasNextInt()) {
                month = in.nextInt();
                // checking if id exists

                if (month > 0 && month < 13) {
                    isValid = true;
                } else {
                    System.out.println("Month " + month + " is invalid, please enter the correct month number (1-12)");
                }


            } else {
                System.out.println("Month should be the number (1 - 12)");

            }
        }

        return month;
    }


    public void deleteInvoice() {
//        try {
//            CustomerDB customerDB = new CustomerDB();
//            CustomerView view = new CustomerView();
//            view.printCustomers( customerDB.fetchCustomers());
//
//        }
//        catch (CustomerExceptionHandler e) {
//            System.out.println(e.getMessage());
//        }

        InvoiceDB invoiceDB = new InvoiceDB();
        int customerId = invoiceDB.getCustomerFromInvoice(DBconnection.stmt);
        int invoiceId = invoiceDB.getInvoiceId(customerId);
        invoiceDB.printInvoice(invoiceId);

        boolean isValid = false;
        while (!isValid) {
            System.out.println("\nDeleting invoices may bring some legal issues, are you sure you want to delete this invoice? ");
            System.out.println("1 - Yes");
            System.out.println("2 - Cancel");
            if (in.hasNextInt()) {
                int choice = in.nextInt();
                if (choice == 1) {
                    isValid = true;
                    try {
                        deleteInvoiceById(invoiceId);
                    } catch (CustomerExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }

                } else if (choice == 2) {
                    //cancel, exit the loop
                    isValid = true;
                    System.out.println("\nDeleting invoice was cancelled");
                } else {
                    System.out.println("\nInvalid input");
                }
            } else {
                in.next();
                System.out.println("\nInvalid input");
            }
        }

    }

    public void seeCustomerInvoices() {
//        try {
//            CustomerDB customerDB = new CustomerDB();
//            CustomerView view = new CustomerView();
//            view.printCustomers( customerDB.fetchCustomers());
//
//        }
//        catch (CustomerExceptionHandler e) {
//            System.out.println(e.getMessage());
//        }

        InvoiceDB invoiceDB = new InvoiceDB();
        int customerId = invoiceDB.getCustomerFromInvoice(DBconnection.stmt);
//        int invoiceId = invoiceDB.getInvoiceId(customerId);
//        invoiceDB.printInvoice(invoiceId);
    }


    public void deleteInvoiceById(int invoiceId) throws CustomerExceptionHandler {
        // checking if invoice with Id invoiceId exists in the db
        Utility utility = new Utility();
        if (utility.ifInvoiceExists(invoiceId)) {
            String updateQuery = "DELETE FROM invoice WHERE invoice_id = " + invoiceId + ";";
            try {
                DBconnection.stmt.executeUpdate(updateQuery);
                System.out.println("  Invoice with Id " + invoiceId + " was successfully deleted from the DB");
            } catch (SQLException sqle) {
                throw new CustomerExceptionHandler(sqle.getMessage() + "\n" + sqle);
            }
        } else {
            throw new CustomerExceptionHandler("There is no invoice with id " + invoiceId + " in the database");
        }
    }

    public void seeInvoiceByCusName() {
        // 1. Ask user to enter id of the customer
        // 2. Ast user to enter the month he wants to create invoice for
        // 3. Run generation of invoices for that month if its not there yet
        // 4. Create invoice file, save in file and output to console window
        //print customers
        InvoiceDB invoice = new InvoiceDB();
        int customerId = invoice.getCustomerIDFromName();

        int month = askUserToEnterMonth();
        System.out.println(customerId);

        invoiceDB.createInvoice(customerId, month);


    }

    public void seeInvoiceByDate() {
        int month = askUserToEnterMonth();
        InvoiceDB db = new InvoiceDB();
        db.displayAllInvoicesByDate(month);

    }
}
