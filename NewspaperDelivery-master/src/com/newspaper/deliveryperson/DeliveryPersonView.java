package com.newspaper.deliveryperson;

import com.newspaper.db.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class DeliveryPersonView {
    DeliveryPersonDB dpDB = new DeliveryPersonDB();
    DeliveryPerson dp = new DeliveryPerson();

    String editId = "";
    String firstName = "";
    String lastName = "";
    String address1 = "";
    String address2 = "";
    String town = "";
    String deliveryPhoneNumber = "";
    String dobMonth = "";
    String dobYear = "";
    String dobDay = "";
    String userName = "";
    String password = "";
    boolean validName = false;
    boolean validFName = false;
    boolean validHouseNumber = true;
    boolean validAddress2 = true;
    boolean validTown = true;
    boolean validPhone = true;
    boolean validUser = true;
    boolean validPw = false;
    boolean validDOB = false;
    Scanner in = new Scanner(System.in);


    // ******************************************************************************************************
    // Beginning of the DISPLAY Delivery Person Section
    // ******************************************************************************************************

    public void displayAllDeliveryPerson(Statement stmt) {

        String str = "Select * from delivery_person";


        try {
            ResultSet rs = stmt.executeQuery(str);
            // Sets the headings and returns the data from the DB
            System.out.printf("\n%-12s %-15s %-20s %-20s %-20s %-20s %-15s %-15s %-15s %-10s %-20s %-10s\n", "DP ID", "First Name", "Last Name", "House No.", "Street Name", "Town", "Phone Number", "Date of Birth", "Access Level", "Status", "User Name", "Password");
            while (rs.next()) {
                int delivery_person_id = rs.getInt("delivery_person_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String address1 = rs.getString("address1");
                String address2 = rs.getString("address2");
                String town = rs.getString("town");
                String delivery_phone_number = rs.getString("delivery_phone_number");
                String dateOfBirth = rs.getString("dob");
                String access_level = rs.getString("access_level");
                String delivery_status = rs.getString("delivery_status");
                String user_name = rs.getString("user_name");
                String password = rs.getString("password");

                System.out.printf("%-12d %-15s %-20s %-20s %-20s %-20s %-15s %-15s %-15s %-10s %-20s %-10s\n", delivery_person_id, first_name, last_name, address1, address2, town, delivery_phone_number, dateOfBirth, access_level, delivery_status, user_name, password);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to display all Delivery People.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }
    // ******************************************************************************************************
    // Beginning of the DISPLAY ONE Delivery Person Section
    // ******************************************************************************************************

    public void displayDeliveryPerson(Statement stmt) throws SQLException {
        int count;
        String str;
        displayAllDeliveryPerson(stmt);
        System.out.println("Please select the id of the person you would like to display");


        String id = in.next();
        if (dp.validateEntry(id)) {
            str = "select count(*) as total from delivery_Person where delivery_person_id = " + id;
            ResultSet rs = stmt.executeQuery(str);
            count = 0;
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count > 0) {
                str = "Select * from delivery_person where delivery_person_id = " + id;

                try {
                // Only prints out the required data for the entered Id
                    rs = stmt.executeQuery(str);
                    System.out.printf("\n\n%-12s %-20s %-20s %-10s %-20s %-20s %-15s %-15s %-15s %-10s %-20s %-10s\n", "DP ID", "First Name", "Last Name", "House No.", "Street Name", "Town", "Phone Number", "Date of Birth", "Access Level", "Status", "User Name", "Password");
                    while (rs.next()) {

                        int delivery_person_id = rs.getInt("delivery_person_id");
                        String first_name = rs.getString("first_name");
                        String last_name = rs.getString("last_name");
                        String address1 = rs.getString("address1");
                        String address2 = rs.getString("address2");
                        String town = rs.getString("town");
                        String delivery_phone_number = rs.getString("delivery_phone_number");
                        String dateOfBirth = rs.getString("dob");
                        String access_level = rs.getString("access_level");
                        String delivery_status = rs.getString("delivery_status");
                        String user_name = rs.getString("user_name");
                        String password = rs.getString("password");

                        System.out.printf("%-12d %-20s %-20s %-10s %-20s %-20s %-15s %-15s %-15s %-10s %-20s %-10s\n", delivery_person_id, first_name, last_name, address1, address2, town, delivery_phone_number, dateOfBirth, access_level, delivery_status, user_name, password);
                    }
                } catch (SQLException sqle) {
                    System.out.println("valid input detected - please use only 1 or 2 numbers");
                    displayDeliveryPerson(stmt);
                }
            } else {
                System.out.println("That person does not exist, please try again");
                displayDeliveryPerson(stmt);
            }
        }else
        {
            displayDeliveryPerson(stmt);
        }
    }


    // ******************************************************************************************************
    // Beginning of the ADD NEW Delivery Person Section
    // ******************************************************************************************************
//
    public void addNewDeliveryPerson(Statement stmt) throws SQLException {

        do {

        System.out.println("Please enter the persons FIRST name");

        if (in.hasNextLine()) {

            firstName = in.nextLine();
            dp.setFirstName(firstName);


            if (!dp.validateString(firstName)) {  // checks entered string and validates using validateString method
                System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                validFName = false;
            } else {
                validFName = true;
            }
        }
        } while (!validFName);

        // do while loops below used to check if entered information validates and if not, it will request the info again

        do {
            System.out.println("Please enter the persons LAST name");
            if (in.hasNextLine()) {
                lastName = in.nextLine();
                dp.setLastName(lastName);
                if (!dp.validateString(lastName)) {  // checks entered string and validates using validateString method
                    System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                    validName = false;
                } else {
                    validName = true;
                }
            }
        } while (!validName);


        do {
            System.out.println("Please enter the persons House Number");

            if (in.hasNextLine()) {
                address1 = in.nextLine();
                dp.setAddress1(address1);
                if (!dp.validateHouseNumber(address1)) { // checks entered string and validates using validateHouseNumber method
                    System.out.println("The house Number must be between 1 and 99999");
                    validHouseNumber = false;
                } else {
                    validHouseNumber = true;
                }
            }
        } while (!validHouseNumber);

        do {
            System.out.println("Please enter the persons Street name");

            if (in.hasNextLine()) {
                address2 = in.nextLine();
                dp.setAddress2(address2);

                if (!dp.validateStringWithNumbers(address2)) { // checks entered string and validates using validateStringWithNumbers method
                    System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                    validAddress2 = false;
                } else {
                    validAddress2 = true;
                }
            }
        } while (!validAddress2);

        do {
            System.out.println("Please enter the persons Town");

            if (in.hasNextLine()) {
                town = in.nextLine();
                dp.setTown(town);

                if (!dp.validateStringWithNumbers(town)) {  // checks entered string and validates using validateString method
                    System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                    validTown = false;
                } else {
                    validTown = true;
                }
            }
        } while (!validTown);


        System.out.println("Please enter the Phone Number i.e. 087 3987656");

        do {
            if (in.hasNextLine()) {
                deliveryPhoneNumber = in.nextLine();
                dp.setDeliveryPhoneNumber(deliveryPhoneNumber);

                if (!dp.validatePhoneNumber(deliveryPhoneNumber)) { // checks entered string and validates using validatePhoneNumber method
                    System.out.println("The phone number must be in the format 087 8888888");
                    validPhone = false;

                } else {
                    validPhone = true;
                }
            } else {
                validPhone = false;
            }
        } while (!validPhone);


        System.out.println("Please enter the Persons DOB");
        do {
            System.out.println("First, what year were they born  (1901 to current year, 4 numbers required)");
            if (in.hasNext()) {
                dobYear = in.next();
                if(dp.validateYear(dobYear)){ // checks entered string and validates using validateYear method
                    validDOB = true;
                }
            }
        } while (!validDOB);
        validDOB = false;
        while (!validDOB) {
            System.out.println("Next, what month were they born in? (1 - 12, 2 Numbers required)");
            if (in.hasNext()) {
                dobMonth = in.next();
                if(dp.validateMonth(dobMonth)){  // checks entered string and validates using validateMonth method
                    validDOB = true;
                }
            }
        }
        validDOB = false;
        while (!validDOB) {
            System.out.println("finally the date they were born (1 - 31, 2 numbers required)");
            if (in.hasNext()) {
                dobDay = in.next();
                if(dp.validateDate(dobDay)){ // checks entered string and validates using validateDate method
                    validDOB = true;
                }
                else {
                    validDOB = false;
                }
            }

            else validDOB = false;
        }
        String dobFinal = dobYear + "-" + dobMonth + "-" + dobDay;
        dp.setDateOfBirth(dobFinal);


        do {
            System.out.println("Please enter the persons Username - between 2 & 20 characters");

            if (in.hasNext()) {
                userName = in.next();
                dp.setUserName(userName);

                if (!dp.validateString(userName)) {  // checks entered string and validates using validateString method
                    System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                    validUser = false;
                } else {
                    validUser = true;
                }
            }
        } while (!validUser);

        do {
            System.out.println("Please enter the persons Password - must be 4 characters");

                if (in.hasNext()) {
                    password = in.next();
                    dp.setPassword(password);
                    if (password.length() != 4) {
                        System.out.println("Invalid entry - please enter 4 characters");
                        validPw = false;
                    } else {
                        validPw = true;
                    }
                } else {
                    System.out.println("\"Invalid entry - please enter 4 characters");
                    validPw = false;
                }
        } while (!validPw);


        Statement addNewPerson = DBconnection.con.createStatement();
        addNewPerson.executeUpdate("insert into delivery_Person values (null ,'" + dp.getFirstName() + "','" + dp.getLastName() + "','" + dp.getAddress1() + "','" + dp.getAddress2() + "','" + dp.getTown() + "','" + dp.getDeliveryPhoneNumber() + "','" + dp.getDateOfBirth() + "','" + 2 + "','" + "true" + "','" + dp.getUserName() + "','" + dp.getPassword() + "')");

    }

    // ******************************************************************************************************
    // Beginning of the EDIT Delivery Person Section
    // ******************************************************************************************************




    public void editDeliveryPerson(Statement stmt) throws SQLException {
        DeliveryPersonView dpv = new DeliveryPersonView();
        String str;
        int count;
        displayAllDeliveryPerson(stmt);
        System.out.println("Please enter the id of the person you would like to edit");
        editId = in.next();
        if (dp.validateEntry(editId)) {
            str = "select count(*) as total from delivery_Person where delivery_person_id = " + editId;
            ResultSet rs = stmt.executeQuery(str);
            count = 0;
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count > 0) {

                System.out.println("Please enter the id of the attribute you would like to edit");
                int menuChoiceEditPerson = 0; // variable used to store Edit menu choice
                int stopEdit = 12; //value from menu that is used to close the Edit process

                while (menuChoiceEditPerson != stopEdit) {
                    editDeliveryPersonMenu(); //display the primary menu
                    if (in.hasNextInt()) {
                        //get the menu choice from the user
                        menuChoiceEditPerson = in.nextInt();

                        switch (menuChoiceEditPerson) {
                            case 1:
                                editPersonFirstName(); //The code for this method is already done for you below
                                break;
                            case 2:
                                editPersonLastName(); //You need to code this method below
                                break;
                            case 3:
                                editPersonAddress1(); //You need to code this method below
                                break;
                            case 4:
                                editPersonAddress2(); //You need to code this method below
                                break;
                            case 5:
                                editPersonTown(); //You need to code this method below
                                break;
                            case 6:
                                editPersonPhoneNumber(); //You need to code this method below
                                break;
                            case 7:
                                editPersonDob(); //You need to code this method below
                                break;
                            case 8:
                                editPersonAccessLevel(); //You need to code this method below
                                break;
                            case 9:
                                editPersonStatus(); //You need to code this method below
                                break;
                            case 10:
                                editPersonUserName(); //You need to code this method below
                                break;
                            case 11:
                                editPersonPassword(); //You need to code this method below
                                break;
                            case 12:
                                System.out.println("Returning to main Delivery Menu......");
                                dpv.displayAllDeliveryPerson(stmt);  // close the connection to the database when finished program
                                break;
                            default:
                                System.out.println("You entered an invalid choice, please try again...");
                        }
                    } else {
                        //clear the input buffer and start again
                        in.nextLine();
                        System.out.println("You entered an invalid choice, please try again...");
                    }
                }
            }
        }
    }



    // ******************************************************************************************************
//    // Beginning of the Edit Person Method Section
//    // ******************************************************************************************************

    public void editPersonFirstName() throws SQLException {
        System.out.println("Please enter a new First Name");
        in.nextLine();
        String newName = in.nextLine();
        if (!dp.validateString(newName)) {
            System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
            validName = false;
        } else {
            validName = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET first_name = '" + newName + "' where delivery_person_id = '" + editId + "'");
        }
    }


    public void editPersonLastName () throws SQLException {
        System.out.println("Please enter a new Last Name");
        String newName = in.next();
        if (!dp.validateString(newName)) {
            System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
            validName = false;
        } else {
            validName = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET last_name = '" + newName + "' where delivery_person_id = '" + editId + "'");
        }
    }


    public void editPersonAddress1() throws SQLException {
        System.out.println("Please enter a new House Number");
        String newHouse = in.next();
        if (!dp.validateHouseNumber(newHouse)) {
            System.out.println("The house Number must be between 1 and 99999");
        }
        else {
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET address1 = '" + newHouse + "' where delivery_person_id = '" + editId + "'");
        }
    }

    public void editPersonAddress2() throws SQLException {
        in.nextLine();
        System.out.println("Please enter a new Street Name");
        if (in.hasNextLine()) {
            String newName = in.nextLine();
            if (!dp.validateStringWithNumbers(newName)) {
                System.out.println("Names must be between 1 to 20 characters");
                validName = false;
            } else {
                validName = true;
                Statement editPerson = DBconnection.con.createStatement();
                editPerson.executeUpdate("Update delivery_person SET address2 = '" + newName + "' where delivery_person_id = '" + editId + "'");
            }
        }
    }
    public void editPersonTown() throws SQLException {
        in.nextLine();
        System.out.println("Please enter a new Town");
        String newName = in.nextLine();
        if (!dp.validateStringWithNumbers(newName)) {
            System.out.println("Names must be between 1 to 20 characters");
            validName = false;
        } else {
            validName = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET town = '" + newName + "' where delivery_person_id = '" + editId + "'");
        }
    }

    public void editPersonPhoneNumber() throws SQLException {
        in.nextLine();
        System.out.println("Please enter a new Phone Number in the format 087 8888888");
        if (in.hasNextLine()) {
            String newPhone = in.nextLine();

            if (dp.validatePhoneNumber(newPhone)) {
                Statement editPerson = DBconnection.con.createStatement();
                editPerson.executeUpdate("Update delivery_person SET delivery_phone_number = '" + newPhone + "' where delivery_person_id = '" + editId + "'");
            } else {
                System.out.println("incorrect values entered for phone number");
            }
        }
    }


    public void editPersonDob() throws SQLException {
        do
        {
            System.out.println("First, what year were they born  (1900 to current year, 4 numbers required)");
            if (in.hasNext()) {
                dobYear = in.next();
                if(dp.validateYear(dobYear)){
                    validDOB = true;
                }
                else{
                    validDOB = false;
                }
            }
        } while (!validDOB);
        validDOB = false;
        while (!validDOB) {
            System.out.println("Next, what month were they born in? (1 - 12, 2 Numbers required)");
            if (in.hasNext()) {
                dobMonth = in.next();
                if(dp.validateMonth(dobMonth)){
                    validDOB = true;
                }
                else{
                    validDOB = false;
                }

            }else validDOB = false;
        }
        validDOB = false;
        while (!validDOB) {
            System.out.println("finally the date they were born (1 - 31, 2 numbers required)");
            if (in.hasNext()) {
                dobDay = in.next();
                if(dp.validateDate(dobDay)){
                    validDOB = true;
                }
                else validDOB = false;
            }
            else validDOB = false;
        }
        String dobFinal = dobYear + "-" + dobMonth + "-" + dobDay;

        Statement editPersonDOB = DBconnection.con.createStatement();
        editPersonDOB.executeUpdate("update delivery_person SET dob = '"+ dobFinal+"' where delivery_person_id = '"+editId+"'");
    }


    public void editPersonAccessLevel() throws SQLException {
        System.out.println("Please enter a new access level - 1 for admin, 2 for Delivery access");
        String newAccess = in.next();
        if (newAccess.equals("1") || newAccess.equals("2")) {
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET access_level = '" + newAccess + "' where delivery_person_id = '" + editId + "'");
        } else {
            System.out.println("Invalid input, please enter 1 for admin, 2 for Delivery access");
        }
    }

    public void editPersonStatus() throws SQLException {
        System.out.println("Please enter a new status - true = active, false = inactive");
        String newStatus = in.next();
        if (newStatus.equals("true") || newStatus.equals("false")) {
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET delivery_status = '" + newStatus + "' where delivery_person_id = '" + editId + "'");
        } else {
            System.out.println("Invalid input, please enter true for active or false for inactive");
        }
    }

    public void editPersonUserName() throws SQLException {
        System.out.println("Please enter a new User Name - 20 characters max");
        String newName = in.next();
        if (!dp.validateString(newName)) {
            System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
            validName = false;
        } else {
            validName = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_person SET user_name = '" + newName + "' where delivery_person_id = '" + editId + "'");
        }
    }

    public void editPersonPassword() throws SQLException {
        boolean valid;
        do {
            try {
                System.out.println("Please enter a new Password - 4 Numbers");
                String newPw = in.next();
                if(newPw.length() == 4) {
                    Statement editPerson = DBconnection.con.createStatement();
                    valid = true;
                    editPerson.executeUpdate("Update delivery_person SET password = '" + newPw + "' where delivery_person_id = '" + editId + "'");
                }
                else{
                    System.out.println("invalid entry, please enter 4 Numbers only");
                    valid = false;
                }
            }catch (Exception e){
                System.out.println("invalid entry, please enter 4 Numbers only");
                valid = false;
            }
            }while(!valid);
    }

//    // ******************************************************************************************************
//    // Beginning of the DELETE Delivery Person Section
//    // ******************************************************************************************************
//
               public void deleteDeliveryPerson (Statement stmt) throws SQLException {
                   int deleteCount;
                   String str;
                   ResultSet rs;
                   DeliveryPersonDB dpm = new DeliveryPersonDB();
                   displayAllDeliveryPerson(stmt);
                   System.out.println("Please enter the id number of the delivery person that you want to delete");

                   String id = in.next();
                   if (dp.validateEntry(id)) {
                       str = "select count(*) as total from delivery_Person where delivery_person_id = " + id;

                       rs = stmt.executeQuery(str);
                       deleteCount = 0;
                       while (rs.next()) {
                           deleteCount = rs.getInt("total");
                       }

                       if (deleteCount > 0) {

                           try {
                               Statement deletePerson = DBconnection.con.createStatement();
                               deletePerson.executeUpdate("delete from delivery_Person where delivery_person_id =" + id + "");
                               System.out.println("Delivery Person with id: " + id + " has been deleted");
                           } catch (Exception e) {
                               System.out.println("unable to delete delivery person");
                           }
                       } else {
                           System.out.println("That person does not exist, please try again");
                       }
                   }

               }

//********************* MENU DISPLAYS ****************************************

    public static void displayMainMenu()
    {
        System.out.println("\nDelivery Person Main Menu");
        System.out.println("1: Display all Delivery People");
        System.out.println("2: Display a particular Delivery Person ");
        System.out.println("3: Create a new Delivery Person ");
        System.out.println("4: Edit a Person's details ");
        System.out.println("5: Delete a Delivery Person ");
        System.out.println("6: Return to Main Menu\n");
        System.out.print("Enter your choice: ");
    }

    public static void editDeliveryPersonMenu() {
        System.out.println("\nEdit Person Menu");
        System.out.println("1: Edit Person's First Name");
        System.out.println("2: Edit Person's Last Name");
        System.out.println("3: Edit Person's House Number");
        System.out.println("4: Edit Person's Street name");
        System.out.println("5: Edit Person's Town");
        System.out.println("6: Edit Person's Phone Number");
        System.out.println("7: Edit Person Date of Birth");
        System.out.println("8: Edit Person's Access level");
        System.out.println("9: Edit Person's Status");
        System.out.println("10: Edit Person's Username");
        System.out.println("11: Edit Person's Password");
        System.out.println("12: Exit to Main Menu\n");
        System.out.print("Enter your choice: ");
    }
//
//
}


