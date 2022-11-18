package com.newspaper.deliverydocket;

import com.newspaper.db.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * @author  Yuliia Dovbak
 */

/*Base class for all items that must be included in the delivery docket.
* I created this class to ba able to store these items in the same list and reduce amount of code,
* but still separate invoice and publication deliveries*/
public class DeliveryItem {



    private int id;
    private int deliveryId;
    private int customerID;
    private String customerName;
    private String customerAddress;
    private boolean isDelivered;
    private String type; // invoice or publication

    // constructor with no id
    public DeliveryItem(int id, int customerID, String customerName, String customerAddress, String type, boolean isDelivered) throws DeliveryDocketExceptionHandler {

        // validating the input, if all ok, the exception will not be thrown
        // and the values will be initialised
        try {
            validateCustomerId(customerID);
            validateCustomerName(customerName);
            validateCustomerAddress(customerAddress);
            validateType(type);
        }
        catch (DeliveryDocketExceptionHandler e) {
            throw e;
        }

        this.id = id;
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.type = type;
        this.isDelivered = isDelivered;
    }

    // constructor with id
    public DeliveryItem(int deliveryId, int id, int customerID, String customerName, String customerAddress, String type, boolean isDelivered) throws DeliveryDocketExceptionHandler {

        // validating the input, if all ok, the exception will not be thrown
        // and the values will be initialised
        try {
            validateDeliveryId(deliveryId);
            validateCustomerId(customerID);
            validateCustomerName(customerName);
            validateCustomerAddress(customerAddress);
            validateType(type);
        }
        catch (DeliveryDocketExceptionHandler e) {
            throw e;
        }

        this.deliveryId = deliveryId;
        this.id = id;
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.type = type;
        this.isDelivered = isDelivered;
    }

    public DeliveryItem() {

    }


    private void validateType(String type) {
        // can be either invoice or publication
    }

    public void validateCustomerAddress(String customerAddress) throws DeliveryDocketExceptionHandler{

        int minLength = 18;
        int maxLength = 87;

        if (customerAddress ==  null) {
            throw new DeliveryDocketExceptionHandler("Address cannot be null");
        }
        else if(customerAddress.isBlank() || customerAddress.isEmpty()){
            throw new DeliveryDocketExceptionHandler("Address cannot be empty");
        }
        else if (customerAddress.length() < minLength) {
            throw new DeliveryDocketExceptionHandler("Address does not meet the minimum length requirements");
        }
        else if (customerAddress.length() > maxLength) {
            throw new DeliveryDocketExceptionHandler("Address exceeds the maximum length requirements");
        }
    }

    public void validateCustomerName(String customerName) throws DeliveryDocketExceptionHandler {
        int minLength = 5;
        int maxLength = 52;

        if (customerName ==  null) {
            throw new DeliveryDocketExceptionHandler("Customer name cannot be null");
        }
        else if(customerName.isBlank() || customerName.isEmpty()){
            throw new DeliveryDocketExceptionHandler("Customer name cannot be empty");
        }
        else if (customerName.length() < minLength) {
            throw new DeliveryDocketExceptionHandler("Customer name does not meet the minimum length requirements");
        }
        else if (customerName.length() > maxLength) {
            throw new DeliveryDocketExceptionHandler("Customer name exceeds the maximum length requirements");
        }
        else {
            // checking if line has any numbers
            char[] charArray = customerName.toCharArray();
            for (char c : charArray) {
                if (Character.isDigit(c)) {
                    throw new DeliveryDocketExceptionHandler("Customer name cannot consist of numbers");
                }
            }
        }
    }

    public void validateCustomerId(int customerID) throws DeliveryDocketExceptionHandler {
        String query = "select count(*) as total from customer where customer_id = " + customerID + ";";
        ResultSet rs;
        int count = - 1;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if(count == 0)
            {
                throw new DeliveryDocketExceptionHandler("Customer with id " + customerID + " does not exist");
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

        }
    }

    public void validateDeliveryId(int deliveryID) throws DeliveryDocketExceptionHandler {
        String query = "select count(*) as total from delivery where delivery_id = " + deliveryID + ";";
        ResultSet rs;
        int count = - 1;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if(count == 0)
            {
                throw new DeliveryDocketExceptionHandler("Delivery with id " + deliveryID + " does not exist");
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

        }
    }




    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void print() {
        System.out.printf("\n %-10s %-20s %-35s %-10s %-10s", this.getCustomerID(), this.getCustomerName(), this.getCustomerAddress(), this.isDelivered(), this.getType());
    }

}
