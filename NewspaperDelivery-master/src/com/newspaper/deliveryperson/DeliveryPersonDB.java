package com.newspaper.deliveryperson;

import com.newspaper.db.DBconnection;
import com.sun.tools.javac.Main;

import java.sql.*;
import java.util.Scanner;

public class DeliveryPersonDB {

    static Scanner in = new Scanner(System.in);

    public void deliveryPersonMainPage() throws SQLException{
        DeliveryPersonView dpv = new DeliveryPersonView();
        //Main main = new Main();
        int menuChoice = 0; // variable used to store main menu choice
        final int STOP_APP = 6; //value from menu that is used to quit the application


        // MAIN MENU DESIGN FOR DELIVERY PERSON
       while (menuChoice != STOP_APP) {
            dpv.displayMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        dpv.displayAllDeliveryPerson(DBconnection.stmt);
                        break;
                    case 2:
                        dpv.displayDeliveryPerson(DBconnection.stmt);
                        break;
                    case 3:
                        dpv.addNewDeliveryPerson(DBconnection.stmt);
                        break;
                   case 4:
                        dpv.editDeliveryPerson(DBconnection.stmt);
                        break;
                    case 5:
                        dpv.deleteDeliveryPerson(DBconnection.stmt);
                       break;
                    case 6:
                        return; // Back to main menu
                    default:
                        System.out.println("You entered an invalid choice, please try again...");
               }
            } else {
                in.nextLine();
                System.out.println("You entered an invalid choice, please try again...");
            }
        }
    }
}



