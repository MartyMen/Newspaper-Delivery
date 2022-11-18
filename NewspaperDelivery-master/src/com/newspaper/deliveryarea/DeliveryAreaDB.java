package com.newspaper.deliveryarea;

import com.newspaper.db.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class DeliveryAreaDB
{
    String editId = "";

    public void displayAllAreas(Statement stmt)
    {
        //1: Query the database for all areas
        //2: Display the result set in an appropriate manner
        String str = "Select * from delivery_area";

        try {
            ResultSet rs = stmt.executeQuery(str);
            System.out.printf("\n%-20s %-25s %-20s %-20s\n", "Delivery Area ID", "Name", "Description", "Delivery Person Id");
            while (rs.next()) {
                int delivery_area_id = rs.getInt("delivery_area_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String dpID = rs.getString("delivery_person_id");

                System.out.printf("%-20s %-25s %-20s %-20s\n", delivery_area_id, name, description, dpID);
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to areas.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    public ArrayList <DeliveryArea> getAllAreas(Statement stmt)
    {
        //1: Query the database for all areas
        //2: Display the result set in an appropriate manner
        String str = "Select * from delivery_area";
        ArrayList <DeliveryArea> deliveryAreas = new ArrayList<>() ;

        try {
            ResultSet rs = stmt.executeQuery(str);
            while (rs.next()) {
                int delivery_person_id = rs.getInt("delivery_person_id");
                int delivery_area_id = rs.getInt("delivery_area_id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                // create an object of com.newspaper.deliveryarea.DeliveryArea and add it into the array list
                DeliveryArea area = new DeliveryArea(delivery_area_id, name, description, delivery_person_id);
                deliveryAreas.add(area);
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to get areas.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
        return deliveryAreas;
    }


    public void displayAllCustomers(Statement stmt)
    {
        //1: Query the database for all areas
        //2: Display the result set in an appropriate manner
        String str = "Select * from customer order by address2";

        try {
            ResultSet rs = stmt.executeQuery(str);
            System.out.printf("\n%-12s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-15s %-12s\n", "com.newspaper.customer.Customer id", "First Name", "Last Name", "House no.", "Estate", "Town", "Eircode", "Phone Number", "Holiday Start Date", "Holiday End Date", "com.newspaper.customer.Customer Status");
            while (rs.next()) {
                int customer_id = rs.getInt("customer_id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String address1 = rs.getString("address1");
                String address2 = rs.getString("address2");
                String town = rs.getString("town");
                String eircode = rs.getString("eircode");
                String phone_number = rs.getString("phone_number");
                String holiday_start_date = rs.getString("holiday_start_date");
                String holiday_end_date = rs.getString("holiday_end_date");
                boolean customer_status = rs.getBoolean("customer_status");

                System.out.printf("%-12s %-15s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-15s %-12s\n", customer_id, first_name, last_name, address1, address2, town, eircode, phone_number, holiday_start_date, holiday_end_date, customer_status);
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to areas.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    public void createNewDeliveryArea(Statement stmt) throws SQLException //NEEDS FINISHING
    {
        DeliveryAreaMain dam = new DeliveryAreaMain();
        DeliveryArea da = new DeliveryArea();
        String newName, description, editId;
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Please enter Delivery Area name: ");
            if (in.hasNextLine()) {
                newName = in.nextLine();
                da.validateString(newName);
                if (!da.validateString(newName)) {
                    System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
                    da.validName = false;
                } else {
                    da.validName = true;
                    da.setDAreaName(newName);
                }
            }
        } while (!da.validName);

        do {
            System.out.println("Please enter an Area Description: ");
            description = in.nextLine();
            if (!da.validateDesc(description))
            {
                System.out.println("Descriptions cannot contain numbers and must be between 1 to 14 characters");
                da.validDesc = false;
            }
            else
            {
                da.validDesc = true;
                da.setDescription(description);
            }
        }  while (!da.validDesc);

        do {
            System.out.println("Please enter the delivery person's id: ");
            editId = in.next();

            if (!da.validateEntry(editId))
            {
                System.out.println();
                da.validEntry = false;
            }
            else
                {
                int id = parseInt(editId);
                da.setDeliveryPersonId(id);
                String str = "select count(*) as total from delivery_Person where delivery_person_id = " + id;
                ResultSet rs = stmt.executeQuery(str);
                int count = 0;
                while (rs.next())
                {
                    count = rs.getInt("total");
                }
                if (count > 0) {
                    str = "Select * from delivery_person where delivery_person_id = " + id;

                    try
                    {
                        da.validEntry = true;
                        Statement addNewArea = DBconnection.con.createStatement();
                        addNewArea.executeUpdate("insert into delivery_area values (null ,'" + da.getDAreaName() + "','" + da.getDescription() + "','" + da.getDeliveryPersonId() + "')");
                    }
                    catch (SQLException sqle)
                    {
                        System.out.println("invalid input detected - please use only 1 or 2 numbers");
                        displayAllAreas(stmt);
                    }
                }
            }
        }   while (!da.validEntry);
    }

    public void deleteDeliveryArea(Statement stmt) throws SQLException // NEEDS VALIDATION
    {
        String str;
        ResultSet rs;
        Scanner in = new Scanner(System.in);
        DeliveryAreaMain dam = new DeliveryAreaMain();
        displayAllAreas(stmt);
        System.out.println("Please enter the id of the Delivery Area you would like to delete: ");
        int deleteId = in.nextInt();

        if (deleteId < 0)
        {
            System.out.println("Please Input a valid ID");
        }
        if (deleteId > 0)
        {
            str = "Select count(*) as total from delivery_area where delivery_area_id = " + deleteId;
            Statement deletePerson = DBconnection.con.createStatement();
            deletePerson.executeUpdate("delete from delivery_area where delivery_area_id ="+deleteId+"");
            System.out.println("Delivery Area with id: "+deleteId+" has been deleted.");
        }
    }

    public void editDeliveryArea(Statement stmt) throws SQLException {
        DeliveryArea da = new DeliveryArea();
        String str;
        int count;
        Scanner in = new Scanner(System.in);
        displayAllAreas(stmt);
        System.out.println("Please enter the id of the area you would like to edit");
        editId = in.next();
        if (da.validateEntry(editId))
        {
            str = "select count(*) as total from delivery_area where delivery_area_id = " + editId;
            ResultSet rs = stmt.executeQuery(str);
            count = 0;
            while (rs.next())
            {
                count = rs.getInt("total");
            }
            if (count > 0)
            {
                System.out.println("Please enter the id corresponding to the attribute you would like to edit");
                int deliveryAreaEditMenu = 0; // variable used to store Edit menu choice
                int stopEdit = 4; //value from menu that is used to close the Edit process

                while (deliveryAreaEditMenu != stopEdit)
                {
                    editDeliveryAreaMenu(); //display the primary menu
                    if (in.hasNextInt())
                    {
                        //get the menu choice from the user
                        deliveryAreaEditMenu = in.nextInt();

                        switch (deliveryAreaEditMenu)
                        {
                            case 1:
                                editDeliveryAreaName(); //The code for this method is already done for you below
                                break;
                            case 2:
                                editDeliveryAreaDescription(); //You need to code this method below
                                break;
                            case 3:
                                editDeliveryPersonId(stmt); //You need to code this method below
                                break;
                            case 4:
                                System.out.println("Returning to main Delivery Menu......");
                                displayAllAreas(stmt);
                                break;
                            default:
                                System.out.println("You entered an invalid choice, please try again...");
                        }
                    }
                    else
                    {
                        //clear the input buffer and start again
                        in.nextLine();
                        System.out.println("You entered an invalid choice, please try again...");
                    }
                }
            }
        }
    }

    // ******************************************************************************************************
    // Beginning of the Edit Area Method Section
    // ******************************************************************************************************

    public void editDeliveryAreaName() throws SQLException
    {
        DeliveryArea da = new DeliveryArea();
        System.out.println("Please enter a new Delivery Area Name: ");
        Scanner in = new Scanner(System.in);
        String newName = in.nextLine();
        if (!da.validateString(newName))
        {
            System.out.println("Names cannot contain numbers and must be between 1 to 20 characters");
            da.validName = false;
        }
        else
        {
            da.validName = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_area SET name = '" + newName + "' where delivery_area_id = '" + editId + "'");
        }
    }

    public void editDeliveryAreaDescription() throws SQLException
    {
        DeliveryArea da = new DeliveryArea();
        System.out.println("Please enter a new Delivery Area Description: ");
        Scanner in = new Scanner(System.in);
        String newDesc = in.nextLine();
        if (!da.validateDesc(newDesc))
        {
            System.out.println("Descriptions cannot contain numbers and must be between 1 to 14 characters");
            da.validDesc = false;
        }
        else
        {
            da.validDesc = true;
            Statement editPerson = DBconnection.con.createStatement();
            editPerson.executeUpdate("Update delivery_area SET description = '" + newDesc + "' where delivery_area_id = '" + editId + "'");
        }
    }


    public void editDeliveryPersonId(Statement stmt) throws SQLException
    {
        DeliveryArea da = new DeliveryArea();
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Please   enter the new Delivery Person id: ");
            String replacementId = in.nextLine();
            if (!da.validateEntry(replacementId))
            {
                da.validEntry = false;
            }
            else
                {
                int id = parseInt(replacementId);
                if(id < 0 || id > 100)
                {
                    System.out.println("ID must be greater than 0 and less than 100");
                    da.validEntry = false;
                }

                if(doesDeliveryPersonExist(id))
                {
                    Statement editPerson = DBconnection.con.createStatement();
                    editPerson.executeUpdate("Update delivery_area SET delivery_person_id = '" + id + "' where delivery_area_id = '" + editId + "'");
                    System.out.println("DB was updated");
                    da.validEntry = true;
                }
                else
                {
                    System.out.println("That Delivery ID does not exist.");
                    da.validEntry = false;
                }

/*
                String str = "select count(*) as total from delivery_area where delivery_person_id = " + id;
                ResultSet rs = stmt.executeQuery(str);
                int count = 0;
                while (rs.next())
                {
                    count = rs.getInt("total");

                    if (count > 0)
                    {
                        da.validEntry = true;
                        Statement editPerson = com.newspaper.db.DBconnection.con.createStatement();
                        editPerson.executeUpdate("Update delivery_area SET delivery_person_id = '" + id + "' where delivery_area_id = '" + editId + "'");
                    }
                    else
                    {
                        System.out.println("That Delivery ID does not exist.");
                        da.validEntry = false;
                    }
                }
*/

            }
        }
        while (!da.validEntry) ;
    }

    public boolean doesDeliveryPersonExist(int deliveryPersonId) {
        String str = "Select * from delivery_area";

        try {
            ResultSet rs = DBconnection.stmt.executeQuery(str);
            while (rs.next()) {

                if(deliveryPersonId == rs.getInt("delivery_area_id"))
                {
                    return true;
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error: failed to areas.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }

        return false;
    }

    public static void displayMainMenu()
    {
        System.out.println("\n Main Menu ");
        System.out.println("1: Display all Customers");
        System.out.println("2: Display all Delivery Areas");
        System.out.println("3: Create New Delivery Area");
        System.out.println("4: Update Delivery Area Name");
        System.out.println("5: Delete a Delivery Area");
        System.out.println("6: Return to Main Menu");
    }

    public static void editDeliveryAreaMenu() {
        System.out.println("\nEdit Delivery Area Menu");
        System.out.println("1: Edit Delivery Area Name: ");
        System.out.println("2: Edit Delivery Area Descriptions: ");
        System.out.println("3: Edit Delivery Area, Delivery Person: ");
        System.out.println("4: Return to main menu: ");
    }
}
