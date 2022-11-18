package com.newspaper.deliveryarea;

import com.newspaper.db.DBconnection;

import java.sql.*;
import java.util.Scanner;

public class DeliveryAreaMain
{
    static Scanner in = new Scanner(System.in);
    DeliveryArea da = new DeliveryArea();

    public void runDeliveryAreaMenu() throws SQLException{
        DeliveryAreaDB dav = new DeliveryAreaDB();

        // Variable used to store menu choice.
        int menuChoice = 0;

        // value that wil close the application.
        final int STOP_APP = 6;

        while (menuChoice != STOP_APP)
        {
            dav.displayMainMenu();
            if (in.hasNextInt())
            {
                menuChoice = in.nextInt();

                switch(menuChoice)
                {
                    case 1:
                        dav.displayAllCustomers(DBconnection.stmt);
                        break;

                    case 2:
                        dav.displayAllAreas(DBconnection.stmt);
                        break;

                    case 3:
                        dav.createNewDeliveryArea(DBconnection.stmt);
                        break;

                    case 4:
                        dav.editDeliveryArea(DBconnection.stmt);
                        break;

                    case 5:
                        dav.deleteDeliveryArea(DBconnection.stmt);
                        break;

                    case 6:
                        System.out.println("Returning to the Main Menu...");
                        break;
                    default:
                        System.out.println("You entered an invalid choice please try again.");
                }
            }
            else
            {
                in.nextLine();
                System.out.println("You entered an invalid choice please try again.");
            }
        }
    }


    public static void main(String[] args) throws SQLException {
        DBconnection.init_db(); //Opens the connection to the database.
        DeliveryAreaDB dav = new DeliveryAreaDB();

        // Variable used to store menu choice.
        int menuChoice = 0;

        // value that wil close the application.
        final int STOP_APP = 6;

        while (menuChoice != STOP_APP)
        {
            dav.displayMainMenu();
            if (in.hasNextInt())
            {
                menuChoice = in.nextInt();

                switch(menuChoice)
                {
                    case 1:
                        dav.displayAllCustomers(DBconnection.stmt);
                        break;

                    case 2:
                        dav.displayAllAreas(DBconnection.stmt);
                        break;

                    case 3:
                        dav.createNewDeliveryArea(DBconnection.stmt);
                        break;

                    case 4:
                        dav.editDeliveryArea(DBconnection.stmt);
                        break;

                    case 5:
                        dav.deleteDeliveryArea(DBconnection.stmt);
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println("You entered an invalid choice please try again.");
                }
            }
            else
            {
                in.nextLine();
                System.out.println("You entered an invalid choice please try again.");
            }
        }
    }
}

