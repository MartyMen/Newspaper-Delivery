package com.newspaper.publication;

import com.newspaper.db.DBconnection;

import java.sql.*;
import java.util.Scanner;

public class PublicationMain {

    public void publicationMainPage() throws  SQLException{

        Scanner in  = new Scanner(System.in);

        Publication publication = new Publication();

        PublicationView pv = new PublicationView();

        int menuChoice = 0;

        final int STOP_APP = 7;

        while (menuChoice != STOP_APP) {
            pv.displayMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        pv.displayAllPublication();
                        break;
                    case 2:
                        pv.displayPublication();
                        break;
                    case 3:
                        pv.addNewPublication();
                        break;
                    case 4:
                        pv.editPublication();
                        break;
                    case 5:
                        pv.deletePublication();
                        break;
                    case 6:
                        return;
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

    public static void main(String[] args) throws SQLException {

        DBconnection.init_db();  // open the connection to the database
        Scanner in  = new Scanner(System.in);

        Publication publication = new Publication();

        PublicationView pv = new PublicationView();

        int menuChoice = 0;

        final int STOP_APP = 7;

        while (menuChoice != STOP_APP) {
            pv.displayMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        pv.displayAllPublication();
                        break;
                    case 2:
                        pv.displayPublication();
                        break;
                    case 3:
                        pv.addNewPublication();
                        break;
                    case 4:
                        pv.editPublication();
                       break;
                    case 5:
                        pv.deletePublication();
                        break;
//                    case 6:
//                        System.out.println("Program is closing...");
//                        pv.cleanup_resources();  // close the connection to the database when finished program
//                        break;
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



