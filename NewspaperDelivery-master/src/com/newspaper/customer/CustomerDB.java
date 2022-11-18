package com.newspaper.customer;

import com.newspaper.db.DBconnection;

import java.sql.*;
import java.util.ArrayList;
/**
 * @author  Yuliia Dovbak
 */

/**
 * Running mysqlserver on mac
 * -mysql.server start
 * -mysql -u root -p
 * -mysql.server stop
 */
public class CustomerDB {

    private ArrayList<Customer> customers; // local copy of all customers in the db

    // constructor
    public CustomerDB()  throws CustomerExceptionHandler{
        fetchCustomers();
    }


    // getters and setters
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }


    /**
     * Method is retrieving the data about the customers and returns ArrayList of   Customer objects
     * @return an ArrayList of   Customer objects from the database
     */
    public ArrayList<Customer> fetchCustomers() throws CustomerExceptionHandler {
        // array list for saving all the objects of the   Customer class
        ArrayList<Customer> customersList = new ArrayList<>();

        String query = "Select * from customer";
        ResultSet rs;
        try {
            rs = DBconnection.stmt.executeQuery(query);
            while (rs.next()) {

                int id = rs.getInt("customer_id");

                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int address1 = rs.getInt("address1");
                String address2 = rs.getString("address2");
                String town = rs.getString("town");
                String eircode = rs.getString("eircode");
                String phonenumber = rs.getString("phone_number");
                String holidayStartDate = rs.getString("holiday_start_date");
                String holidayEndDate = rs.getString("holiday_end_date");
                boolean status = rs.getBoolean("customer_status");
                int deliveryAreaId = rs.getInt("delivery_area_id");

                customersList.add(new Customer(
                        id,
                        firstName,
                        lastName,
                        address1,
                        address2,
                        town,
                        eircode,
                        phonenumber,
                        holidayStartDate,
                        holidayEndDate,
                        status,
                        deliveryAreaId)
                );
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            System.out.println(query);

            throw new CustomerExceptionHandler("Error: failed to read all customers.");
        } catch (CustomerExceptionHandler customerException) {
            throw customerException;
        }

        // saving customers locally
        customers = customersList;

        return customersList;
    }


    /**
     * Method is inserting new customer into the db
     *
     * @param customer object containing all the data about the customer.
     *                 This data is accessed through getters and setters
     *                 and inserted into the db
     */
    public void insertCustomer(Customer customer) throws CustomerExceptionHandler{

        // checking if the new record is not duplicate
        if (!ifCustomerExists(customer)) { // if false

            // sql query
            String insertQuery = "INSERT INTO customer VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                PreparedStatement pstmt = DBconnection.con.prepareStatement(insertQuery);
                pstmt.setString(1, customer.getFirstName());
                pstmt.setString(2, customer.getLastName());
                pstmt.setInt(3, customer.getAddress1());
                pstmt.setString(4, customer.getAddress2());
                pstmt.setString(5, customer.getTown());
                pstmt.setString(6, customer.getEircode());
                pstmt.setString(7, customer.getPhoneNumber());
                pstmt.setString(8, customer.getHolidayStartDate());
                pstmt.setString(9, customer.getHolidayEndDate());
                pstmt.setString(10, customer.getStatus() + "");
                pstmt.setInt(11, customer.getDeliveryAreaId());

                int rows = pstmt.executeUpdate();

                System.out.println("Adding new customer record was successful");
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(insertQuery);
                throw new CustomerExceptionHandler("Error: failed to add a customer record");
            }
        } else {
            // outputting message about duplicate
            throw new CustomerExceptionHandler(customer.getFirstName() + " " + customer.getLastName() + " record already exists in the database");
        }
    }

    /**
     * Method is setting customer status to "inactive" or false
     *
     * @param customerId the if of customer that has to be deactivated
     */
    public void deactivateCustomer(int customerId) throws CustomerExceptionHandler {

        // checking if customer with Id customerId exists in the db
        if (ifCustomerExists(customerId)) {
            String updateQuery = "UPDATE customer\n" +
                    "SET customer_status = false " +
                    "WHERE customer_id = " + customerId + ";";
            try {
                DBconnection.stmt.executeUpdate(updateQuery);
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(updateQuery);
                throw new CustomerExceptionHandler("Failed to deactivate customer record");
            }
        } else {
            throw new CustomerExceptionHandler("There is no customer with id " + customerId + " in the database");
        }
    }

    /**
     * Method for deleting customer record from DB
     * @param customerId is the id of the customer record that has to be deleted
     * @throws CustomerExceptionHandler exception is thrown in case of error with the database
     */
    public void deleteCustomer(int customerId) throws CustomerExceptionHandler {

        // checking if customer with Id customerId exists in the db
        if (ifCustomerExists(customerId)) {
            String updateQuery = "DELETE FROM customer WHERE customer_id = " + customerId + ";";
            try {
                DBconnection.stmt.executeUpdate(updateQuery);
                System.out.println("  Customer with Id " + customerId + " was successfully deleted from the DB");
            } catch (SQLException sqle) {
                throw new CustomerExceptionHandler(sqle.getMessage() + "\n" + sqle);
            }
        } else {
            throw new CustomerExceptionHandler("There is no customer with id " + customerId + " in the database");
        }
    }

    /**
     * Method updates customer information
     * @param customerId the id of the customer record that must be updated
     * @param c the customer object with containing data for update
     * @param stmt Statement object to access the db
     * @throws CustomerExceptionHandler exception is thrown if update query wfailed
     */
    public void updateCustomer(int customerId, Customer c,  Statement stmt) throws CustomerExceptionHandler {

        // checking if customer with Id customerId exists in the db
        if (ifCustomerExists(customerId)) {

            // setting holidays
            String holidayStart = null;
            String holidayEnd = null;

            if (c.getHolidayStartDate() != null)
                holidayStart = "\"" + c.getHolidayStartDate() + "\"";
            if (c.getHolidayEndDate() != null)
                holidayEnd = "\"" + c.getHolidayEndDate() + "\"";

            // if customer exists, then update is possible
            String updateQuery = "UPDATE customer " +
                    "SET first_name = \"" + c.getFirstName() +
                    "\", last_name = \"" + c.getLastName() +
                    "\", address1 = " + c.getAddress1() +
                    ", address2 = \"" + c.getAddress2() +
                    "\", town = \"" + c.getTown() +
                    "\", eircode = \"" + c.getEircode() +
                    "\", phone_number = \"" + c.getPhoneNumber() +
                    "\", holiday_start_date = " + holidayStart +
                    ", holiday_end_date = " + holidayEnd +
                    ", customer_status = \"" + c.getStatus() +
                    "\", delivery_area_id = " + c.getDeliveryAreaId() +
                    " WHERE customer_id = " + customerId + ";";
            try {
                stmt.executeUpdate(updateQuery);
                System.out.println("  Customer with Id " + customerId + " was successfully updated");
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
                System.out.println(updateQuery);
                throw new CustomerExceptionHandler("Failed to update customer record");
            }
        } else {
            throw new CustomerExceptionHandler("There is no customer with id " + customerId + " in the database");
        }
    }

    /**
     * Method is checking if such customer already exists. The check is performed by checking name and address
     *
     * @param newCustomer the customer object that needs to be checked if duplicate
     * @return true if this customer already exists in the db, false if not
     */
    public boolean ifCustomerExists(Customer newCustomer) {

        // accessing data of new customer
        String firstName = newCustomer.getFirstName();
        String lastName = newCustomer.getLastName();
        int address = newCustomer.getAddress1();

        for (Customer c : customers) {
            if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName) && c.getAddress1() == address) {
                return true;
            }
        }

        // if return didn't happen in the foreach loop, then this is not duplicate
        return false;
    }

    public ArrayList<Integer> printAllDeliveryAreas() throws CustomerExceptionHandler{

        String query = "Select * from delivery_area";
        ResultSet rs;
        ArrayList<Integer> deliveryAreaIDs = new ArrayList<>();
        try {
            rs = DBconnection.stmt.executeQuery(query);
            System.out.printf("\n%-10s %-25s %-45s\n", "ID", "Delivery area name", "Description");
            while (rs.next()) {

                int id = rs.getInt("delivery_area_id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                System.out.printf("\n%-10d %-25s %-45s", id, name, desc);

                // saving all delivery area ids
                deliveryAreaIDs.add(id);
            }

        } catch (SQLException sqle) {
            throw new CustomerExceptionHandler(sqle.getMessage() + "\n" + query);
        }

        return deliveryAreaIDs;
    }

    /**
     * Method is checking if customer with customerId  already exists
     *
     * @param customerId customer if from the db
     * @return true if this customer already exists in the db, false if not
     */
    public boolean ifCustomerExists(int customerId) {

        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) {
                return true;
            }
        }

        // if return didn't happen in the foreach loop, then this is not duplicate
        return false;
    }

    /**
     * Method is checking if customer with customerId  already exists
     *
     * @param customerId customer if from the db
     * @return true if this customer already exists in the db, false if not
     */
    public Customer getCustomerById(int customerId) {

        for (Customer c : customers) {
            if (c.getCustomerId() == customerId) {
                return c;
            }
        }
        return null;
    }


}
