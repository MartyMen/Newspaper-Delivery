package com.newspaper.deliverydocket;

import com.newspaper.customer.CustomerDB;
import com.newspaper.customer.CustomerExceptionHandler;
import com.newspaper.customer.CustomerView;
import com.newspaper.db.DBconnection;
import com.newspaper.deliveryarea.DeliveryArea;
import com.newspaper.invoice.InvoiceGenerator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author  Yuliia Dovbak
 */

public class DeliveryView {


    private Scanner in;
    private Utility utility;
    private DeliveryDocketDB deliveryDocketDB;

    public DeliveryView() {
        in = new Scanner(System.in);
        utility = new Utility();
        deliveryDocketDB = new DeliveryDocketDB();
    }


    public void deliveryMainPage() {
        int menuChoice = 0; // variable used to store main menu choice
        final int STOP_APP = 9; //value from menu that is used to quit the application


        // initialising view
        try {

            // running the menu
            while (menuChoice != STOP_APP) {
                // display the delivery menu
                displayDeliveryMenu();

                if (in.hasNextInt()) {

                    //get the menu choice from the user
                    menuChoice = in.nextInt();

                    switch (menuChoice) {
                        case 1 -> {
                            // create a delivery docket
                            creatingDeliveryDocket();
                        }
                        case 2 -> {
                            // read a delivery docket
                            readDeliveryDocket();
                        }
                        case 3 -> {
                            // update delivery docket
                            updatingDeliveryDocket();
                        }
                        case 4 -> {
                            // delete delivery docket
                            deleteDeliveryDocket();
                        }
                        case 5 -> {
                            // see all customer deliveries
                            seeAllCustomerDeliveries();
                        }
                        case 6 -> {
                            // see all publication deliveries
                            seeAllPublicationDeliveries();
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
        } catch (Exception e) {
            System.out.println("Error in the first menu");
            System.out.println(e.getMessage());
        }

        //in.close();
    }

    // ask user to enter delivery person id
    public void creatingDeliveryDocket() {
        // 1. Display all delivery people and the delivery areas they work on
        // 2. Ask user to enter id of the delivery person
        // 3. Ask user to enter the date of the delivery docket
        // 4. Check if deliveries for that date are available
        // 5. Generate deliveries if they are not in the DB
        // 6. Create delivery docket file and show on console

        // 1. Display all delivery people and the delivery areas they work on
        utility.displayDeliveryPeopleWithDeliveryAreas();

        // 2. Ask user to enter id of the delivery person
        boolean isValid = false;
        int deliveryPersonId = askUserToEnterDeliveryPersonId();

        // 3. Ask user to enter the date of the delivery docket
        String date = askUserToEnterDate();
        System.out.println("Generating delivery docket...");

        // 4. Check if deliveries for that date are available
        // 5. Generate deliveries if they are not in the DB

        deliveryDocketDB.generateDeliveriesIfNeeded(date);


        // 6. Create delivery docket file and show on console
        try {

            DeliveryDocket docket = deliveryDocketDB.createDeliveryDocketFor(deliveryPersonId, date);
            System.out.println(docket);
            System.out.println("\n***Saving...");
            System.out.println("***Delivery dockets will be available to print after program closes");
            deliveryDocketDB.createDeliveryDocketFile(docket);
        } catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }

    }

    public void readDeliveryDocket() {

        int deliveryAreaId = askUserToEnterDeliveryAreaId();
        String date = askUserToEnterDate();
        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = utility.getDeliveryArea(deliveryAreaId);

            ArrayList<DeliveryItem> deliveries = deliveryDocketDB.getAllDeliveryItemsFor(area.getId(), date);
            DeliveryDocket docket = new DeliveryDocket(deliveries, date, area.getId(), area.getDAreaName(), utility.getDeliveryPersonName(area.getDeliveryPersonId()));
            System.out.println(docket);
            deliveryDocketDB.createDeliveryDocketFile(docket);
        }
        catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    public void updatingDeliveryDocket() {
        // 1. Display all delivery people and the delivery areas they work on
        // 2. Ask user to enter id of the delivery person
        // 3. Ask user to enter the date of the delivery docket
        // 4. Read in the delivery docket, show to user
        // 5. Ask the user, does he want to update the delivery status of all deliveries
        //    at once, or update by id.
        // 6. If all at once: make an update and show updated delivery docket
        //    If by one: ask user to enter ids of the delivery records to be updated

        // 1. Display all delivery people and the delivery areas they work on
        utility.displayDeliveryPeopleWithDeliveryAreas();

        // 2. Ask user to enter id of the delivery person
        boolean isValid = false;
        int deliveryPersonId = askUserToEnterDeliveryPersonId();

        // 3. Ask user to enter the date of the delivery docket
        String date = askUserToEnterDate();

        // 3.1 Check if this is future date
        if (!utility.isInFuture(date)) {

            // 4. Read in the delivery docket, show to user
            try {
                deliveryDocketDB.generateDeliveriesIfNeeded(date);

                // need to create it if it wasn't created yet on this machine
                DeliveryDocket docket = deliveryDocketDB.createDeliveryDocketFor(deliveryPersonId, date);
                System.out.println(docket);
                deliveryDocketDB.createDeliveryDocketFile(docket);

            } catch (DeliveryDocketExceptionHandler e) {
                System.out.println(e.getMessage());
            }

            // 4. Check if deliveries were not delivered yet
            try {
                int isDelivered = deliveryDocketDB.isFullyDelivered(deliveryPersonId, date);
                if ( isDelivered == -1) {
                    // 5. Ask the user, does he want to update the delivery status of all deliveries
                    //    at once, or update by id.
                    isValid = false;
                    int updateChoice = -1;
                    while (!isValid) {
                        System.out.println("Two options available now: \n" +
                                " 1: Update all deliveries to 'delivered' status \n" +
                                " 2: Update deliveries by id\n" +
                                " 9: Cancel the update, go to main menu");
                        if (in.hasNextInt()) {
                            updateChoice = in.nextInt();
                            isValid = true;
                        } else {
                            System.out.println("Your choice should be 1, 2, or 9");
                        }
                    }

                    if (updateChoice == 1) {
                        // update all deliveries to delivered 'status'
                        try {
                            deliveryDocketDB.updateDeliveriesStatus(deliveryPersonId, date);
                            System.out.println("Update was successful");
                        } catch (DeliveryDocketExceptionHandler e) {
                            System.out.println(e.getMessage());
                        }

                    } else if (updateChoice == 2) {
                        updatingDeliveriesByID(deliveryPersonId, date);
                    }
                }
                else if (isDelivered == 0) {
                    System.out.println("\n NO NEED TO UPDATE: No deliveries for this day");
                }
                else {
                    System.out.println("\n NO NEED TO UPDATE: All deliveries in this delivery docket were delivered.");
                }
            } catch (DeliveryDocketExceptionHandler e) {
                System.out.println("Error: in line deliveryDocketDB.isFullyDelivered (deliveryPersonId, date)");
            }

        }
        else {
            System.out.println("You cannot change delivery status of deliveries in the future.");
            System.out.println("Changing delivery status is available for today or past dates only.");
        }


    }

    public void updatingDeliveriesByID(int deliveryPersonId, String date) {
        boolean keepGoing = true;

        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = deliveryDocketDB.getDeliveryArea(deliveryPersonId);

            // get all deliveries for delivery docket
            ArrayList<DeliveryItem> deliveries = deliveryDocketDB.getAllDeliveryItemsFor(area.getId(), date);

            while (keepGoing) {
                in.nextLine();
                System.out.println("(Enter 0 to stop) Enter id of the delivery that you want to update: ");
                if (in.hasNextInt()) {
                    int deliveryId = in.nextInt();
                    // check if we keep going
                    if (deliveryId != 0) {

                        // find the correct delivery
                        DeliveryItem deliveryItem = null;
                        for (DeliveryItem item : deliveries) {
                            if (item.getDeliveryId() == deliveryId) {
                                deliveryItem = item;
                            }
                            if (item.getId() == deliveryId) {
                                deliveryItem = item;
                            }
                        }

                        // if delivery was found and its not delivered yet, update the delivery
                        if (deliveryItem == null) {
                            System.out.println("Delivery with delivery id " + deliveryId + " is not found");
                        }
                        else if (deliveryItem.isDelivered()) {
                            System.out.println("Delivery with delivery id " + deliveryId + " is delivered");
                        }
                        else {
                            // updating the delivery
                            String deliveryStatus = getStatus();
                            deliveryDocketDB.updateDeliveryStatus(deliveryItem, deliveryStatus);
                            System.out.println("Update successful");
                        }
                    }
                    else {
                        keepGoing = false;
                    }
                }
                else
                {
                    System.out.println("Enter only numbers.");
                }
            }
        }
        catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }
        // show final result
        System.out.println("\n*** Delivery docket after updating: \n");
        refreshAndPrintDeliveryDocketFile(deliveryPersonId, date);
    }

    public String getStatus() {
        String status = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.println("Update delivery status to ");
            System.out.println("1 - delivered");
            System.out.println("2 - not delivered");
            if (in.hasNextInt()) {
                int choice = in.nextInt();

                if (choice == 1) {
                    isValid = true;
                    status = "delivered";
                }
                else if (choice == 2) {
                    isValid = true;
                    status = "not delivered";
                }
                else {
                    System.out.println("Invalid input");
                }
            }
            else {
                in.next();
                System.out.println("Invalid input");
            }
        }
        return status;
    }

    public void deleteDeliveryDocket() {
        int deliveryAreaId = askUserToEnterDeliveryAreaId();
        String date = askUserToEnterDate();
        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = utility.getDeliveryArea(deliveryAreaId);
            String deliveryAreaName = area.getDAreaName();
            String deliveryPersonName = utility.getDeliveryPersonName(area.getDeliveryPersonId());
            String fileName = deliveryPersonName + "_" + deliveryAreaName + "_" + date + ".txt";
            deliveryDocketDB.deleteFileIfExists(fileName);
        }
        catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    public void refreshAndPrintDeliveryDocketFile(int deliveryPersonId, String date ) {

        try {
            // get the delivery area id where the delivery person is working
            DeliveryArea area = deliveryDocketDB.getDeliveryArea(deliveryPersonId);

            // get all deliveries for delivery docket
            ArrayList<DeliveryItem> deliveries = deliveryDocketDB.getAllDeliveryItemsFor(area.getId(), date);

            DeliveryDocket docket = new DeliveryDocket(deliveries, date, area.getId(), area.getDAreaName(), utility.getDeliveryPersonName(deliveryPersonId));
            System.out.println(docket);
            deliveryDocketDB.createDeliveryDocketFile(docket);
        }
        catch (DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());;
        }
    }

    public int askUserToEnterDeliveryPersonId ()
    {
        boolean isValid = false;
        int deliveryPersonId = -1;
        // getting id of the delivery person
        while (!isValid) {
            System.out.println("\nEnter id of the delivery person: ");
            in.nextLine();
            if (in.hasNextInt()) {
                deliveryPersonId = in.nextInt();
                // checking if id exists

                boolean deliveryPersonExists = utility.deliveryPersonExists(deliveryPersonId);
                boolean deleveryPersonActive = utility.deliveryPersonActive(deliveryPersonId);
                if (deliveryPersonExists && deleveryPersonActive) {
                    isValid = true;
                } else if (!deliveryPersonExists) {
                    System.out.println("Delivery person with id " + deliveryPersonId + " does not exist");
                } else {
                    System.out.println("Delivery person with id " + deliveryPersonId + " is not available to work(status 'inactive')");
                }


            } else {
                System.out.println("Delivery person id should be a number");

            }
        }

        return deliveryPersonId;
    }


    public int askUserToEnterDeliveryAreaId ()
    {
        boolean isValid = false;
        int deliveryAreaId = -1;
        // show delivery areas
        utility.displayDeliveryAreas();

        // getting id of the delivery area
        while (!isValid) {
            System.out.println("\nEnter id of the delivery area: ");
            in.nextLine();
            if (in.hasNextInt()) {
                deliveryAreaId = in.nextInt();
                // checking if id exists

                if (utility.deliveryAreaExists(deliveryAreaId)) {
                    isValid = true;
                }  else {
                    System.out.println("Delivery area with id " + deliveryAreaId + " does not exist.");
                }


            } else {
                System.out.println("Delivery area id should be a number");

            }
        }

        return deliveryAreaId;
    }


    public String askUserToEnterDate() {

        DeliveryDocket deliveryDocket = new DeliveryDocket(); // to access the validation methods
        String date = "";
        boolean inputValid = false;
        in.nextLine();

        while (!inputValid) {
            System.out.println("Enter the date of the delivery docket (Example: 2021-08-27): ");
            if (in.hasNextLine()) {
                date = in.nextLine();
                try {
                    deliveryDocket.validateDate(date);
                    // if validation was successful
                    inputValid = true;
                } catch (DeliveryDocketExceptionHandler e) {
                    System.out.println(e.getMessage());
                }
                catch (Exception e) {
                    System.out.println("Date format incorrect");
                }
            } else {
                //clear the input buffer and start again
                in.nextLine();
                System.out.println("You entered an invalid date, please try again...");
            }
        }
        return date;
    }

    public void seeAllCustomerDeliveries() {
        try {
            //print customers
            CustomerDB customerDB = new CustomerDB();
            CustomerView view = new CustomerView();
            view.printCustomers( customerDB.fetchCustomers());

            int customerId = askUserToEnterCustomerID();
            utility.displayAllDeliveriesOfCustomer(customerId);
        } catch ( CustomerExceptionHandler | DeliveryDocketExceptionHandler e) {
            System.out.println(e.getMessage());
        }
    }

    public void seeAllPublicationDeliveries() {

        int publicationId = askUserToEnterPublicationID();
        utility.displayAllDeliveriesOfPublication(publicationId);

    }


    public int askUserToEnterCustomerID() {
        boolean isValid = false;
        int customerID = 0;

        // getting id if the customer
        while (!isValid) {
            System.out.println("Enter id of the customer: ");
            if (in.hasNextInt()) {
                customerID = in.nextInt();
                // checking if student exists
                if (utility.ifCustomerExists(customerID)) {
                    isValid = true;
                } else {
                    System.out.println("Customer with id " + customerID + " doesn't exist");
                }
            } else {
                in.next();
                System.out.println("Customer id should be a number");
            }
        }
        return customerID;
    }

    public int askUserToEnterPublicationID() {
        boolean isValid = false;
        int publicationId = 0;
        utility.displayAllPublications();

        // getting id if the customer
        while (!isValid) {
            System.out.println("Enter id of the publication: ");
            if (in.hasNextInt()) {
                publicationId = in.nextInt();
                // checking if publication exists
                if (utility.publicationExists(publicationId)) {
                    isValid = true;
                } else {
                    System.out.println("Publication with id " + publicationId + " doesn't exist");
                }
            } else {
                in.next();
                System.out.println("Publication id should be a number");
            }
        }
        return publicationId;
    }


    public void displayDeliveryMenu() {
        System.out.println("\n Delivery Menu");
        System.out.println("1: Create Delivery Docket");
        System.out.println("2: Show Delivery Docket");
        System.out.println("3: Update Delivery Docket");
        System.out.println("4: Delete Delivery Docket");
        System.out.println("5: See all deliveries of a customer");
        System.out.println("6: See all deliveries of a publication");
        System.out.println("9: Return to Main Menu\n");
        System.out.print("Enter your choice: ");
    }

    public static void main(String[] args) {
        // run the menu
        DBconnection.init_db();
        DeliveryView view = new DeliveryView();
        view.deliveryMainPage();
    }
}
