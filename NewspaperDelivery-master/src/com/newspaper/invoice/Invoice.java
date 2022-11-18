package com.newspaper.invoice;

import com.newspaper.customer.Customer;
import com.newspaper.customer.CustomerDB;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.deliverydocket.Delivery;
import com.newspaper.deliverydocket.DeliveryDocketExceptionHandler;
import com.newspaper.deliverydocket.DeliveryItem;
import com.newspaper.deliverydocket.Utility;

import java.time.LocalDate;
import java.util.ArrayList;

public class Invoice {
    private int invoiceId;
    private int customerId;
    private String invoiceDate;
    private double price;
    private String invoicePaid;

    private String invoiceDelivered;


    private ArrayList<InvoiceItem> invoiceItems;

    public Invoice(int invoiceId, int cusId, String invDate, double price, String invoicePaid) {
        this.invoiceId = invoiceId;
        this.customerId = cusId;
        this.invoiceDate = invDate;
        this.price = price;
        this.invoicePaid = invoicePaid;
    }

    public Invoice(int invoiceId, int cusId, String invDate, double price, String invoicePaid, String invoiceDelivered) {
        this.invoiceId = invoiceId;
        this.customerId = cusId;
        this.invoiceDate = invDate;
        this.price = price;
        this.invoicePaid = invoicePaid;
        this.invoiceDelivered = invoiceDelivered;
    }

    public Invoice(int cusId, String invDate, double price, String invoicePaid, String invoiceDelivered) {
        this.customerId = cusId;
        this.invoiceDate = invDate;
        this.price = price;
        this.invoicePaid = invoicePaid;
        this.invoiceDelivered = invoiceDelivered;
    }

    public Invoice() {

    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInvoicePaid() {
        return invoicePaid;
    }

    public void setInvoicePaid(String invoicePaid) {
        this.invoicePaid = invoicePaid;
    }

    public String getInvoiceDelivered() {
        return invoiceDelivered;
    }

    public void setInvoiceDelivered(String invoiceDelivered) {
        this.invoiceDelivered = invoiceDelivered;
    }

    public ArrayList<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(ArrayList<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }


    @Override
    public String toString() {
        // utility class has some helper methods, here used for getting name of the publication by id
        Utility utility = new Utility();
        StringBuilder sb = new StringBuilder();
        try {
            CustomerDB customerDB = new CustomerDB();
            Customer c = customerDB.getCustomerById(getCustomerId());

            // required datw format : 3 March 2021, Sunday
            LocalDate localDate = utility.convertDate(getInvoiceDate());
            String day = "" + localDate.getDayOfMonth();
            String month = "" + localDate.getMonth();
            String year = String.valueOf(localDate.getYear());
            String dayOfWeek = "" + localDate.getDayOfWeek();
            String date = day + " " + month + " " + year + ", " + dayOfWeek;


            sb.append("\n_____________________________________________________________________________________________________________");
            sb.append("\n+-----------------------------------------------------------------------------------------------------------+");
            sb.append("\n|                                                INVOICE                                                    |");
            sb.append("\n+-----------------------------------------------------------------------------------------------------------+");
            sb.append(String.format("\n| %-25s %-79s |", "DATE: ", date));
            sb.append(String.format("\n| %-25s %-79s |", "Customer Name: ",  c.getFirstName() + " " + c.getLastName()));

            // check if there are any invoice items
            if (invoiceItems.size() > 0) {

                sb.append("\n|                                                                                                           |");
                sb.append("\n|                                                                                                           |");
                sb.append(String.format("\n| %-15s %-30s %-20s %-37s |", "Amt", "Publication name", "Cost", "Total Cost with Vat"));
                sb.append("\n+------------------------------------------------------------------------------------------------------------");
                for (InvoiceItem item : this.invoiceItems) {

                    double vat =  Math.round(item.getTotalCost() * 0.23 * 100.0) / 100.0;
                    sb.append(String.format("\n| %-15s %-30s %-20s %-38s|", item.getAmt(), item.getPubName(), item.getPubCost(), item.getTotalCost() + " + (23% VAT " + vat + ")"));

                }
                sb.append("\n|                                                                                                           |");
                //sb.append("\n| TOTAL: " + Math.round(getPrice() * 100.0) / 100.0 +"                                                                                              |");
                sb.append(String.format("\n| %-15s %-89s |", "TOTAL: ", Math.round(getPrice() * 100.0) / 100.0));
            } else {
                sb.append("\n|                                                                                                           |");
                sb.append("\n|Customer did not receive any deliveries this month.                                                        |");
            }

            sb.append("\n+-----------------------------------------------------------------------------------------------------------+");
            sb.append("\n-------------------------------------------------------------------------------------------------------------");


        } catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }

        return sb.toString();
    }


// Class for info, All database fields + comparisons.
    // ARRAY / ARRAYLIST of all publications, As objects.
}


