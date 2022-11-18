package com.newspaper.dailysummary;

import com.newspaper.db.DBconnection;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DailySummaryMain {

    public void runSummaryMenu() throws SQLException, FileNotFoundException {
        Scanner in  = new Scanner(System.in);

        DailySummary ds = new DailySummary();

        DailySummaryView dsv = new DailySummaryView();

        int menuChoice = 0;

        final int STOP_APP = 4;

        while (menuChoice != STOP_APP) {
            ds.displayDailySummaryMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        dsv.revenueReport();
                        break;
                    case 2:
                        dsv.monthlyReportFile();
                        break;
                    case 3:
                        dsv.populateDatabase();
                        break;
                    case 4:
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



    public static void main(String[] args) throws SQLException, FileNotFoundException {

        DBconnection.init_db();  // open the connection to the database
        Scanner in  = new Scanner(System.in);

        DailySummary ds = new DailySummary();

        DailySummaryView dsv = new DailySummaryView();

        int menuChoice = 0;

        final int STOP_APP = 4;

        while (menuChoice != STOP_APP) {
            ds.displayDailySummaryMainMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        dsv.revenueReport();
                        break;
                    case 2:
                        dsv.monthlyReportFile();
                        break;
                    case 3:
                        dsv.populateDatabase();
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
        return;
    }

}



