package com.newspaper.customer;

import com.newspaper.db.DBconnection;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * @author  Yuliia Dovbak
 */
public class CustomerView {

    public static void main(String[] args) {

    }

    private Scanner in;

    public CustomerView() {
        in = new Scanner(System.in);
    }


    public void customerMainPage() {
        int menuChoice = 0; // variable used to store main menu choice
        final int STOP_APP = 9; //value from menu that is used to quit the application
        int customerID; // setting variable to temporary customer info storage
        CustomerDB customerDB;
        CustomerView view = new CustomerView();
        Scanner in = new Scanner(System.in);

        // initialising view
        try {
            // if view can be initialised with no errors, then connection
            // with db is set correctly
            customerDB = new CustomerDB();

            // running the menu
            while (menuChoice != STOP_APP) {
                view.displayCustomerMenu(); //display the primary customer menu
                if (in.hasNextInt()) {
                    //get the menu choice from the user
                    menuChoice = in.nextInt();
                    customerDB.fetchCustomers(); // resetting local copy of customers
                    switch (menuChoice) {
                        case 1 -> {
                            ArrayList<Customer> customers = customerDB.fetchCustomers();
                            printCustomers(customers);
                        }
                        case 2 -> {
                            customerID = view.askUserToEnterCustomerID(customerDB);
                            System.out.println(customerDB.getCustomerById(customerID));
                            break;
                        }
                        case 3 -> view.addNewCustomer(customerDB);
                        case 4 -> {
                            customerID = view.askUserToEnterCustomerID(customerDB);
                            System.out.println(customerDB.getCustomerById(customerID));    // print customer details
                            view.editCustomer(customerID, customerDB); //You need to code this method below
                        }
                        case 5 -> {
                            customerID = view.askUserToEnterCustomerID(customerDB);
                            view.deactivateCustomer(customerID, customerDB); //You need to code this method below
                        }
                        case 6 -> {
                            customerID = view.askUserToEnterCustomerID(customerDB);
                            view.deleteCustomer(customerID, customerDB); //You need to code this method below
                        }
                        case 9 -> {
                            System.out.println("Returning to the Main Menu...");
                        }
                        default -> System.out.println("You entered an invalid choice, please try again...");
                    }
                } else {
                    //clear the input buffer and start again
                    in.nextLine();
                    System.out.println("You entered an invalid choice, please try again...");
                }
            }
        }
        catch (CustomerExceptionHandler e) {
            System.out.println("Error in the first menu");
            System.out.println(e.getMessage());
        }
    }

    public void addNewCustomer(CustomerDB customerDB) {
        boolean isValid = false;

        while(!isValid) {
            String firstName = askUserToEnterName("first name");
            String lastName = askUserToEnterName("last name");
            int address1 = askUserToEnterAddress1();
            String address2 = askUserToEnterAddress2("street");
            String town = askUserToEnterAddress2("town");
            String eircode = askUserToEnterEircode();
            String phoneNumber = askUserToEnterPhone();
            String holidayStartDate = null;
            String holidayendDate = null;

            // getting holiday info
            boolean keepAskingHoliday = true;
            while (keepAskingHoliday) {
                // if customer has a holiday in the future, initialise the holiday fields
                if (askCustomerAboutHoliday()) {
                    holidayStartDate = askUserToEnterHolidayStart();
                    holidayendDate = askUserToEnterHolidayEnd();

                    // validating date start and end
                    Customer c = new Customer();
                    try {
                        c.validateHoliday(holidayStartDate, holidayendDate);
                        keepAskingHoliday = false;
                    }
                    catch (CustomerExceptionHandler e) {
                        System.out.println(e.getMessage());
                    }

                }
                else {
                    // customer doesn't have a holiday
                    keepAskingHoliday = false;
                    holidayStartDate = null;
                    holidayendDate = null;
                }
            }

            // status of a new customer is active by default
            boolean status = true;

            try {
                ArrayList<Integer> IDs = customerDB.printAllDeliveryAreas();
                int deliveryAreaId = askUserToEnterDeliveryAreaID(IDs);

                // creating new customer object
                Customer registerCustomer = new Customer(firstName, lastName, address1, address2, town, eircode, phoneNumber, holidayStartDate, holidayendDate, status, deliveryAreaId);

                // if customer can be created, attempting insert into the db
                customerDB.insertCustomer(registerCustomer);
                isValid = true;
            }
            catch ( CustomerExceptionHandler e) {
                System.out.println("***" + e.getMessage() + "***");
                System.out.println("There was an error with adding new customer. Please try again");
            }
        }
    }

    public void deactivateCustomer(int customerId, CustomerDB customerDB) {
        // soft delete
        try {
            customerDB.deactivateCustomer(customerId);
            System.out.println(" Customer with ID " + customerId + " was deactivated successfully");
        }
        catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCustomer(int customerId, CustomerDB customerDB) {
        // soft delete
        try {
            customerDB.deleteCustomer(customerId);
            //System.out.println(" Customer with ID " + customerId + " was deleted successfully");
        }
        catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    public void editCustomer(int customerId, CustomerDB customerDB) {

        int menuChoice = 0;         // variable used to store main menu choice
        final int STOP_APP = 99;    // value from menu that is used to quit the application


        // before editing the customer, fetch current list of customers
        try {
            customerDB.fetchCustomers();
        }
        catch (CustomerExceptionHandler e) {
            System.out.println(e.getMessage());
        }

        // accessing the object of customer that has to be edited
        Customer customer = customerDB.getCustomerById(customerId);

        // if customer was found
        if (customer != null) {

            // running the menu
            while (menuChoice != STOP_APP) {
                displayCustomerEditMenu(); //display the primary customer menu
                if (in.hasNextInt())
                {
                    //get the menu choice from the user
                    menuChoice = in.nextInt();
                    switch (menuChoice) {
                        case 1 -> {
                            // edit first name
                            String fname = askUserToEnterName("first name");
                            customer.setFirstName(fname);
                        }
                        case 2 -> {
                            // edit first name
                            String lname = askUserToEnterName("last name");
                            customer.setLastName(lname);
                        }
                        case 3 -> {
                            // edit address1
                            int addless1 = askUserToEnterAddress1();
                            customer.setAddress1(addless1);
                        }
                        case 4 -> {
                            // edit address2
                            String address2 = askUserToEnterAddress2("address line 2");
                            customer.setAddress2(address2);
                        }
                        case 5 -> {
                            // edit town
                            String town = askUserToEnterAddress2("town");
                            customer.setTown(town);
                        }
                        case 6 -> {
                            // edit eircode
                            String eircode = askUserToEnterEircode();
                            customer.setEircode(eircode);
                        }
                        case 7 -> {
                            // edit phone number
                            String phone = askUserToEnterPhone();
                            customer.setPhoneNumber(phone);
                        }
                        case 8 -> {
                            // edit status
                            boolean status = askUserToEnterStatus(customer.getStatus());
                            customer.setStatus(status);
                        }
                        case 9 -> {
                            // printing all delivery tables
                            try {
                                ArrayList<Integer> IDs = customerDB.printAllDeliveryAreas();
                                int deliveryAreaId = askUserToEnterDeliveryAreaID(IDs);
                                customer.setDeliveryAreaId(deliveryAreaId);
                            }
                            catch (CustomerExceptionHandler e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case 10 -> {
                            // editing holidays
                            boolean keepAskingHoliday = true;
                            String holidayStartDate = null;
                            String holidayendDate = null;
                            while (keepAskingHoliday) {
                                // if customer has a holiday in the future, initialise the holiday fields
                                if (askCustomerAboutHoliday()) {
                                    holidayStartDate = askUserToEnterHolidayStart();
                                    holidayendDate = askUserToEnterHolidayEnd();

                                    // validating date start and end
                                    Customer c = new Customer();
                                    try {
                                        c.validateHoliday(holidayStartDate, holidayendDate);
                                        keepAskingHoliday = false;
                                    }
                                    catch (CustomerExceptionHandler e) {
                                        System.out.println(e.getMessage());
                                    }

                                }
                                else {
                                    // customer doesn't have a holiday
                                    keepAskingHoliday = false;
                                    holidayStartDate = null;
                                    holidayendDate = null;
                                }
                            }
                            // setting new holidays
                            customer.setHolidayStartDate(holidayStartDate);
                            customer.setHolidayEndDate(holidayendDate);
                        }
                        case 99 -> {
                            System.out.println("Finishing update...");
                            try {
                                customerDB.updateCustomer(customerId, customer, DBconnection.stmt);
                                System.out.println("Returning to main menu ... ");
                            }
                            catch (CustomerExceptionHandler e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        default -> System.out.println("You entered an invalid choice, please try again...");
                    }
                }
                else {
                    //clear the input buffer and start again
                    System.out.println("You entered an invalid choice, please try again...");
                }

            }
        }

    }


    public int askUserToEnterCustomerID(CustomerDB view) {
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        int customerID = 0;

        // getting id if the customer
        while (!isValid)
        {
            System.out.println("Enter id of the customer: ");
            if(in.hasNextInt())
            {
                customerID = in.nextInt();
                // checking if student exists
                if(view.ifCustomerExists(customerID))
                {
                    isValid = true;
                }
                else
                {
                    System.out.println(" Customer with id " + customerID + " doesn`t exist");
                }
            }
            else
            {
                in.next();
                System.out.println(" Customer id should be a number");
            }
        }
        return customerID;
    }

    public int askUserToEnterDeliveryAreaID(ArrayList<Integer> deliveryAreaIDs) {
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        int deliveryAreaID = 0;

        // getting id if the customer
        while (!isValid)
        {
            System.out.println("\nEnter id of the delivery area: ");
            if(in.hasNextInt())
            {
                deliveryAreaID = in.nextInt();
                // checking if id exists
                if(deliveryAreaIDs.contains(deliveryAreaID))
                {
                    isValid = true;
                }
                else
                {
                    System.out.println("Delivery area with id " + deliveryAreaID + " doesn`t exist");
                }
            }
            else
            {
                in.nextLine();
                System.out.println("Delivery area id should be a number");
            }
        }
        return deliveryAreaID;
    }


    public String askUserToEnterName(String initial)
    {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String name = "";
        boolean inputValid = false;
        while (!inputValid)
        {
            System.out.println("Enter customer " + initial + " (25 chars or less): ");
            if (in.hasNextLine())
            {
                name = in.nextLine();
                try {
                    c.validateName(name, initial);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid " + initial + " name, please try again...");
            }
        }
        return name;
    }


    public int askUserToEnterAddress1()
    {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        int address1 = 0;
        boolean inputValid = false;

        while (!inputValid)
        {
            System.out.println("Enter customer address line 1 (number of house or apartment): ");
            if (in.hasNextInt())
            {
                address1 = in.nextInt();
                try {
                    c.validateAddress1(address1);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid address line 1, please try again...");
            }
        }
        return address1;
    }

    public String askUserToEnterEircode() {

        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String eircode = "";
        boolean inputValid = false;

        while (!inputValid)
        {
            System.out.println("Enter customer eircode (Example: A11AA11): ");
            if (in.hasNextLine())
            {
                eircode = in.nextLine();
                try {
                    c.validateEircode(eircode);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid eircode, please try again...");
            }
        }

        return eircode;
    }


    // used for Address line2 and Town inputs
    public String askUserToEnterAddress2(String addressType)
    {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String address = "";
        boolean inputValid = false;
        while (!inputValid)
        {
            System.out.println("Enter customer " + addressType + " (35 chars or less): ");
            if (in.hasNextLine())
            {
                address = in.nextLine();
                try {
                    c.validateAddress(address, addressType);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid " + addressType + ", please try again...");
            }
        }
        return address;
    }

    public String askUserToEnterPhone() {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String phoneNumber = "";
        boolean inputValid = false;

        while (!inputValid)
        {
            System.out.println("Enter customer phone number (Example: 086 8190499): ");
            if (in.hasNextLine())
            {
                phoneNumber = in.nextLine();
                try {
                    c.validatePhoneNumber(phoneNumber);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid phone number , please try again...");
            }
        }
        return phoneNumber;
    }

    // if a customer has a holiday, return true. If not, return false
    public boolean askCustomerAboutHoliday() {
        Scanner in = new Scanner(System.in);
        String answer;
        boolean inputValid = false;
        boolean hasHoliday = false;

        while (!inputValid)
        {
            System.out.println("Does a customer have a planned holiday and doesn't want to receive the newspapers during that period? (yes/no)");
            if (in.hasNextLine())
            {
                // if customer reply is "yes" or "no", save it and exit the loo0
                answer = in.nextLine();
                if (answer.equals("yes") || answer.equals("Yes")) {
                    inputValid = true;
                    hasHoliday = true;
                }
                else if(answer.equals("no") || answer.equals("No")) {
                    inputValid = true;
                    hasHoliday = false;
                }
                else {
                    System.out.println("You entered an invalid answer, please use \"yes\" or \"no\"...");
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid answer, please use \"yes\" or \"no\"...");
            }
        }
        return hasHoliday;
    }

    public String askUserToEnterHolidayStart() {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String holidayStart = "";
        boolean inputValid = false;

        while (!inputValid)
        {
            System.out.println("Enter holiday start date (Example: 2021-08-27): ");
            if (in.hasNextLine())
            {
                holidayStart = in.nextLine();
                try {
                    c.validateDate(holidayStart);
                    // if validation was successful
                    inputValid = true;
                } catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid holiday start date, please try again...");
            }
        }
        return holidayStart;
    }

    public String askUserToEnterHolidayEnd() {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String holidayEnd = "";
        boolean inputValid = false;

        while (!inputValid)
        {
            System.out.println("Enter holiday end date (Example: 2021-08-27): ");
            if (in.hasNextLine())
            {
                holidayEnd = in.nextLine();
                try {
                    c.validateDate(holidayEnd);
                    // if validation was successful
                    inputValid = true;
                }catch (CustomerExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid holiday end date, please try again...");
            }
        }
        return holidayEnd;
    }

    public boolean askUserToEnterStatus(boolean currStatus) {
        Scanner in = new Scanner(System.in);
        Customer c = new Customer();
        String answer;

        boolean inputValid = false;
        while (!inputValid)
        {
            String status = currStatus ? "active" : "inactive";
            System.out.println("Current customer status is " + status + ". Do you want to change it? (yes/no)");
            if (in.hasNextLine())
            {
                // if customer reply is "yes" or "no", save it and exit the loo0
                answer = in.nextLine();
                if (answer.equals("yes") || answer.equals("Yes")) {
                    inputValid = true;
                    currStatus = !currStatus;
                }
                else if(answer.equals("no") || answer.equals("No")) {
                    // status stays the same
                }
                else {
                    System.out.println("You entered an invalid answer, please use \"yes\" or \"no\"...");
                }
            }
            else
            {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid answer, please use \"yes\" or \"no\"...");
            }
        }

        return currStatus;
    }


    /**
     * Method for printing all customer objects out into console window
     */
    public void printAllActiveCustomers(ArrayList<Customer> customers) {
        System.out.printf("\n%-5s %-25s %-45s %-15s %-10s %-10s\n", "ID", "Name", "Address", "Phone", "Status", "Delivery Area ID");
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getStatus() == true) {
                String customerStatus = "active";
                System.out.printf("%-5d %-25s %-45s %-15s %-10s %-10d\n", customers.get(i).getCustomerId(), customers.get(i).getFirstName() + " " + customers.get(i).getLastName(), (customers.get(i).getAddress1() + " " + customers.get(i).getAddress2() + ", " + customers.get(i).getTown()), customers.get(i).getPhoneNumber(), customerStatus, customers.get(i).getDeliveryAreaId());
            }

        }
    }

    /**
     * Method for printing customer objects out into console window
     */
    public void printCustomers(ArrayList<Customer> customers) {
        System.out.printf("\n%-5s %-25s %-35s %-15s %-10s %-20s %-20s %-20s\n", "ID", "Name", "Address", "Phone", "Status", "Delivery Area ID", "Holiday start", "Holiday end");
        for (int i = 0; i < customers.size(); i++) {
            String status = customers.get(i).getStatus() ? "active" : "inactive";
            String holidayStart = "n/a";
            String holidayEnd = "n/a";
            if (customers.get(i).getHolidayStartDate() != null) {
                holidayStart = customers.get(i).getHolidayStartDate();
                holidayEnd = customers.get(i).getHolidayEndDate();
            }
            System.out.printf("%-5d %-25s %-35s %-15s %-10s %-20d %-20s %-20s\n", customers.get(i).getCustomerId(), customers.get(i).getFirstName() + " " + customers.get(i).getLastName(), (customers.get(i).getAddress1() + " " + customers.get(i).getAddress2() + ", " + customers.get(i).getTown()), customers.get(i).getPhoneNumber(), status, customers.get(i).getDeliveryAreaId(), holidayStart, holidayEnd);
        }
    }


    public void displayCustomerMenu () {
        System.out.println("\n Customer Menu");
        System.out.println("1: Display all Customers");
        System.out.println("2: Display Customer by ID");
        System.out.println("3: Create a New Customer ");
        System.out.println("4: Edit Customer's Details ");
        System.out.println("5: Deactivate a Customer ");
        System.out.println("6: Delete a Customer Record");
        System.out.println("9: Return to Main Menu\n");
        System.out.print("Enter your choice: ");
    }

    public void displayCustomerEditMenu () {
        System.out.println("1: Edit First Name");
        System.out.println("2: Edit Last Name");
        System.out.println("3: Edit House/Apartment No");
        System.out.println("4: Edit Street");
        System.out.println("5: Edit Town");
        System.out.println("6: Edit Eircode");
        System.out.println("7: Edit Phone Number");
        System.out.println("8: Edit Status");
        System.out.println("9: Edit Delivery Area");
        System.out.println("10: Edit Holiday Dates");
        System.out.println("99: Finish Editing\n");
        System.out.print("Enter your choice: ");
    }
}
