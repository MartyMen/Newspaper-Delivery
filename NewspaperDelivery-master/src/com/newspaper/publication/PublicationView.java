package com.newspaper.publication;

import com.newspaper.db.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class PublicationView {

    //imports scanner
    Scanner in = new Scanner(System.in);
    boolean validPubName = false;
    boolean validFrequency = false;
    boolean validCost = false;
    boolean validStockLevel = false;
    String editId = "";
    //creates an object from publication main and publication class for use in this class..
    PublicationMain publicationMain = new PublicationMain();
    Publication p = new Publication();


    // ******************************************************************************************************
    // Beginning of display all publications
    // ******************************************************************************************************
    //Prints out the publication table
    public void displayAllPublication() {
        String str = "Select * from publication";


        try {
            ResultSet rs = DBconnection.stmt.executeQuery(str);

            System.out.printf("\n%-12s %-25s %-15s %-10s %-10s \n", "Pub ID", "Publication Name", "Frequency", "Cost", "Stock Level");
            while (rs.next()) {
                int publication_id = rs.getInt("publication_id");
                String publication_name = rs.getString("publication_name");
                String publication_frequency = rs.getString("publication_frequency");
                double publication_cost = rs.getDouble("publication_cost");
                int publication_stock_level = rs.getInt("publication_stock_level");


                System.out.printf("%-12s %-25s %-15s %-10s %-10s \n", publication_id, publication_name, publication_frequency, publication_cost, publication_stock_level);
            }

        } catch (SQLException sqle) {
            System.out.println("Error: failed to display all Publications.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }


    // ******************************************************************************************************
    // Beginning of display a certain publication with entered ID.
    // ******************************************************************************************************

    public void displayPublication() throws SQLException {
        displayAllPublication();
        System.out.println("Please enter the ID of the publication you want to display");



        String st="";
        int count;


        String id = in.next();

        //checks if the entered id is a whole number.
        boolean validId = p.validateAWholeNumber(id);
        if (validId) {


            st = "select count(*) as total from publication where publication_id = " + id;
            ResultSet rss = DBconnection.stmt.executeQuery(st);
            count = 0;
            while (rss.next()) {
                count = rss.getInt("total");
            }
            if (count > 0) {


                //checks if the id entered is a valid ID in the list of publications, if it is, print out the associated data with that entry.
                String str = "Select * from publication where publication_id = " + id;

                try {
                    ResultSet rs = DBconnection.stmt.executeQuery(str);


                    System.out.printf("\n%-12s %-25s %-15s %-10s %-10s \n", "Pub ID", "Publication Name", "Frequency", "Cost", "Stock Level");
                    while (rs.next()) {
                        int publication_id = rs.getInt("publication_id");
                        String publication_name = rs.getString("publication_name");
                        String publication_frequency = rs.getString("publication_frequency");
                        double publication_cost = rs.getDouble("publication_cost");
                        int publication_stock_level = rs.getInt("publication_stock_level");


                        System.out.printf("%-12s %-25s %-15s %-10s %-10s \n", publication_id, publication_name, publication_frequency, publication_cost, publication_stock_level);
                    }

                } catch (SQLException sqle) {
                    System.out.println("Error: failed to display all Publications.");
                    System.out.println(str);
                }
            } else {
                //if the valid ID check fails, the below is printed.
                System.out.println("You have entered an invalid ID, please try again");
                displayPublication();
            }
        }else {
            System.out.println("You have entered an invalid ID, please try again");
            displayPublication();
        }


    }

    // ******************************************************************************************************
    // Beginning of the add a new publication method
    // ******************************************************************************************************

    public void addNewPublication() throws SQLException {
        String newPublication_Name = "";
        String newPublication_Frequency = "";
        String newPublication_Cost = "";
        String newPublication_Stock_Level = "";
        do {
            System.out.println("Please enter the new publication name");


            if (in.hasNextLine())
            {

                newPublication_Name = in.nextLine();


                if (!p.validatePublicationName(newPublication_Name))
                {
                    validPubName=false;


                }
                else {
                    validPubName=true;
                }
            }

        } while (!validPubName);




        do {
            System.out.println("Please enter the publication frequency, either 'Daily' or 'Weekly'");
            if (in.hasNextLine())
            {
               newPublication_Frequency = in.nextLine();

                if (!p.validatePublicationFrequency(newPublication_Frequency))
                {
                    validFrequency = false;
                }
                else {
//                    newPublication_Frequency=newPublication_Frequency.toLowerCase();
                    validFrequency = true;
                }
            }

        } while (!validFrequency);


        do {
            System.out.println("Please enter the publication cost");
            if (in.hasNextLine()) {
                newPublication_Cost  = in.nextLine();
                if (!p.validateANumber(newPublication_Cost)) {
                    validCost = false;
                } else {
                    validCost = true;
                }
            }
        } while (!validCost);

        do {
            System.out.println("Please enter the publication stock level");

            if (in.hasNextLine()) {
                 newPublication_Stock_Level = in.nextLine();
                p.validateAWholeNumber(newPublication_Stock_Level );

                if (!p.validateAWholeNumber(newPublication_Stock_Level )) {
                    System.out.println("Please enter a valid whole number for stock level");
                    validStockLevel = false;
                } else {
                    validStockLevel = true;

                    Statement addNew = DBconnection.con.createStatement();
                    addNew.executeUpdate("INSERT INTO publication VALUES (null, '" + newPublication_Name + "','" + newPublication_Frequency + "','" + newPublication_Cost + "','" + newPublication_Stock_Level + "')");
                    displayAllPublication();


                }
            }
        } while (!validStockLevel);






    }
    // ******************************************************************************************************
    // Beginning of the edit publication method.
    // ******************************************************************************************************

    public void editPublication() throws SQLException {

        //displays all publications so the user can see which publication they want to update.
        displayAllPublication();


        String str = "";
        int count;
        //prompts the user to enter the ID of the publication they want to edit.
        System.out.println("Please enter the ID of the publication you would like to edit");
        editId = in.next();
        //checks if the entery is a valid whole number. if it is, move on.
        boolean validNumberedString = p.validateAWholeNumber(editId);
        if (validNumberedString == true)
        {
            //checks to see if the entered number is a real entry on the database. if it is, move on.
            str = "select count(*) as total from publication where publication_id = " + editId;
            ResultSet rs = DBconnection.stmt.executeQuery(str);
            count = 0;
            while (rs.next()) {
                count = rs.getInt("total");
            }
            if (count > 0) {

//            -----------------------------------------------
//                    start of the edit menu
//            -----------------------------------------------

                int menuChoiceEditPublication = 0; // variable used to store Edit menu choice
                int stopEdit = 5; //value from menu that is used to close the Edit Student process

                while (menuChoiceEditPublication != stopEdit) {
                    editPublicationMenu(); //display the primary menu
                    if (in.hasNextInt()) {
                        //get the menu choice from the user
                        menuChoiceEditPublication = in.nextInt();
                        //switch statement to ask the user which option they want.
                        switch (menuChoiceEditPublication) {
                            case 1:
                                editpublication_name(); //The code for this method is already done for you below
                                break;
                            case 2:
                                editpublication_frequency(); //You need to code this method below
                                break;
                            case 3:
                                editpublication_cost(); //You need to code this method below
                                break;
                            case 4:
                                editpublication_stock_level(); //You need to code this method below
                                break;
                            case 5:
                                System.out.println("Edit menu is closing...");
                                return;

//
                        }
                    } else {
                        //clear the input buffer and start again
                        in.nextLine();
                        //error handling
                        System.out.println("You entered an invalid choice, please try again...");
                        editPublication();
                    }
                }


            }
            else
                System.out.println("You have entered an incorrect ID, please choose a valid ID and try again");
         editPublication();
        } else {
            System.out.println("you have entered an incorrect ID, please enter a correct ID and try again");
            editPublication();
        }

    }

    // ******************************************************************************************************
    // Edit Publication name method
    // ******************************************************************************************************
    public void editpublication_name() throws SQLException {

        System.out.println("Please enter a new name for the publication");
        in.nextLine();
        String newPublication_Name = in.nextLine();
        //validates the entry by the user, if it is valid, executes the update
        boolean validName = p.validatePublicationName(newPublication_Name);
        if (validName) {
            Statement editpublication_name = DBconnection.con.createStatement();
            editpublication_name.executeUpdate("Update publication SET publication_name = '" + newPublication_Name + "' where publication_id = '" + editId + "'");
            System.out.println("Successfully updated the publication name to " + newPublication_Name + "\n\nReturning to edit menu....");
        }
    }

    //  ------------------------------------------------------------------------------------------------------
    // Edit Publication frequency method
    //  ------------------------------------------------------------------------------------------------------
    public void editpublication_frequency() throws SQLException {
        System.out.println("Please enter a new frequency for the publication, either Daily or Weekly");
        String newPublication_Frequency = in.next();
        //validates the entry by the user, if it is valid, executes the update
        boolean validFrequency = p.validatePublicationFrequency(newPublication_Frequency);
        if (validFrequency) {
           newPublication_Frequency = newPublication_Frequency.toLowerCase();
            Statement editpublication_frequency = DBconnection.con.createStatement();
            editpublication_frequency.executeUpdate("Update publication SET publication_frequency = '" + newPublication_Frequency + "' where publication_id = '" + editId + "'");
            System.out.println("Successfully update the frequency to " + newPublication_Frequency + "\n\nReturning to edit menu....");
        }
    }

    //  ------------------------------------------------------------------------------------------------------
    // Edit Publication cost method
    //  ------------------------------------------------------------------------------------------------------
    public void editpublication_cost() throws SQLException {
        System.out.println("Please enter a new cost for the publication");
        String newPublication_Cost = in.next();
        //validates the entry by the user, if it is valid, executes the update
        boolean validNumberedString = p.validateANumber(newPublication_Cost);
        if (validNumberedString) {
            Statement editpublication_cost = DBconnection.con.createStatement();
            editpublication_cost.executeUpdate("Update publication SET publication_cost = '" + newPublication_Cost + "' where publication_id = '" + editId + "'");
            System.out.println("Successfully update the price to " + newPublication_Cost + "\n\nReturning to edit menu....");
        } else System.out.println("Your entry is not a valid number, please try again using a valid number.");
    }

    //  ------------------------------------------------------------------------------------------------------
    // Edit Publication stock level method
    // ------------------------------------------------------------------------------------------------------
    public void editpublication_stock_level() throws SQLException {
        System.out.println("Please enter a new stock level");
        String newPublication_Stock_Level = in.next();

        //validates the entry by the user, if it is valid, executes the update
        boolean validStock = p.validateANumber(newPublication_Stock_Level);
        if (validStock) {
            Statement editpublication_stock_level = DBconnection.con.createStatement();
            editpublication_stock_level.executeUpdate("Update publication SET publication_stock_level = '" + newPublication_Stock_Level + "' where publication_id = '" + editId + "'");
            System.out.println("Successfully update the stock level to " + newPublication_Stock_Level + "\n\nReturning to edit menu....");
        } else System.out.println("Your entry is not a valid number, please try again using a valid whole number.");
    }


    // ******************************************************************************************************
    // Beginning of the delete publication method.
    // ******************************************************************************************************
    public void deletePublication() throws SQLException {

        PublicationMain pm = new PublicationMain();
        int deleteCount;
        String str;
        ResultSet rs;
        displayAllPublication();

        System.out.println("Please enter the ID of the publication you would like to delete\n");
        String id = in.next();

        boolean validString = p.validateAWholeNumber(id);
        if (validString) {

            //validates that the chosen entry is in the database.
            str = "select count(*) as total from publication where publication_id = " + id;
            rs = DBconnection.stmt.executeQuery(str);
            deleteCount = 0;
            while (rs.next())
            {
                deleteCount = rs.getInt("total");
            }

            if (deleteCount > 0) {
                //asks the user if they are sure, and want to delete the entry from the database.
                boolean confirmDelete = p.askUserYesOrNo("Are you sure want to delete this publication. All deliveries, Daily Summaries and orders associated with this publication will also be deleted(yes/no)");
                if (confirmDelete) {
                    //if the user choses yes, the entry is deleted.
                    Statement deletePublication = DBconnection.con.createStatement();
                    deletePublication.executeUpdate("DELETE from publication where publication_id = " + id);
                    System.out.println("The Publication with the id of " + id + " has been deleted...... \n\nReturning to menu......");
                    return;
                }
                else
                    {
                    //if the user choses no, they are returned to the main menu
                    System.out.println("Returning to Main Menu...");
                    return;
                }

            }
            System.out.println("Please choose an entry that is in the database.");
            deletePublication();
        } else {
            //Tells the user the ID they have entered is invalid, brings them to the start of the method
            System.out.println("You have entered an incorrect ID, please check the list of IDs and try again.\n");
            deletePublication();
        }


    }


    // ******************************************************************************************************
    //Menu for the edit publicatinon method
    // ******************************************************************************************************
    public static void editPublicationMenu() {
        System.out.println("\nEdit Publication Menu");
        System.out.println("1: Edit Publication Name");
        System.out.println("2: Edit Publication Frequency");
        System.out.println("3: Edit Publication Cost");
        System.out.println("4: Edit Publication Stock level");
        System.out.println("5: Exit to Main Menu\n");
        System.out.print("Enter your choice: ");
    }

    // ******************************************************************************************************
    // Beginning of main menu for the main application
    // ******************************************************************************************************
    public static void displayMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1: Display all Publications");
        System.out.println("2: Display a Publication with ID ");
        System.out.println("3: Add new Publication");
        System.out.println("4: Edit Publication");
        System.out.println("5: Delete Publication");
        System.out.println("6: Return to Main Menu\n");
        System.out.print("Enter your choice: ");
    }
}



