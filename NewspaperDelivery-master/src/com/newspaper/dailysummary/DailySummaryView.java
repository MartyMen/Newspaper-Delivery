package com.newspaper.dailysummary;

import com.newspaper.db.DBconnection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DailySummaryView {
    DailySummary ds = new DailySummary();
    //SQL Command to find revenue per publication on a specific date.
    Date date = new Date();
    String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

    LocalDate weeklyDate = LocalDate.now().minusDays(7);
    LocalDate tomorrowDate = LocalDate.now().plusDays(1);


    int year = Calendar.getInstance().get(Calendar.YEAR);
    int lastYear = Calendar.getInstance().get(Calendar.YEAR)-1;
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    String currYear = Integer.toString(year);
    String lastYearString = Integer.toString(lastYear);
    int month = localDate.getMonthValue();
    LocalDate monthstart = LocalDate.of(year,month,1);
    LocalDate monthend = monthstart.plusDays(monthstart.lengthOfMonth()-1);

    Scanner in = new Scanner(System.in);


    public void displayDailySummary() {
        String str = "Select * from daily_summary";
        try {
            ResultSet rs = DBconnection.stmt.executeQuery(str);

            System.out.printf("\n%-12s %-20s %-15s %-10s \n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold");
            while (rs.next()) {
                int daily_summary_id = rs.getInt("daily_summary_id");
                String delivery_date = rs.getString("delivery_date");
                int total_revenue = rs.getInt("total_revenue");
                int publications_sold = rs.getInt("publications_sold");


                System.out.printf("%-12s %-20s %-15s %-10s \n", daily_summary_id, delivery_date, total_revenue, publications_sold);
            }

        } catch (
                SQLException sqle) {
            System.out.println("Error: failed to display daily summaries.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    //Doesn't need to be validated, automatic.
    public void dailyReport() throws SQLException {
        System.out.println("You choose Daily");


        Statement addNew = DBconnection.con.createStatement();


            createDailyReportByDate(modifiedDate);
            System.out.println("Daily report created for today: " + modifiedDate + "\n");




        String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date='" + modifiedDate + "';";
        try {
            ResultSet rs = DBconnection.stmt.executeQuery(str);

            System.out.printf("\n%-12s %-20s %-15s %-20s %-25s \n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue");
            while (rs.next()) {
                int daily_summary_id = rs.getInt("daily_summary_id");
                String delivery_date = rs.getString("delivery_date");
                double total_revenue = rs.getDouble("total_revenue");
                int publications_sold = rs.getInt("publications_sold");
                double publications_revenue = rs.getDouble("publications_revenue");

                total_revenue = Math.round(total_revenue * 100.0) / 100.0;
                publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;



                System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);

            }

        } catch (
                SQLException sqle) {
            System.out.println("Error: failed to display all daily summaries.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }


    }

    public void weeklyReport() throws SQLException {
        System.out.println("This is your weekly summary");
        System.out.println("From: " +weeklyDate);
        System.out.println("To: " +modifiedDate);
        String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date BETWEEN '" + weeklyDate + "' and '" + modifiedDate + "' order by delivery_date asc";
        try {
            ResultSet rs = DBconnection.stmt.executeQuery(str);

            System.out.printf("\n%-12s %-20s %-15s %-20s %-25s\n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue");
            while (rs.next()) {
                int daily_summary_id = rs.getInt("daily_summary_id");
                String delivery_date = rs.getString("delivery_date");
                double total_revenue = rs.getDouble("total_revenue");
                int publications_sold = rs.getInt("publications_sold");
                double publications_revenue = rs.getDouble("publications_revenue");

                total_revenue = Math.round(total_revenue * 100.0) / 100.0;
                publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;

                System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);
            }

        } catch (
                SQLException sqle) {
            System.out.println("Error: failed to display all daily summaries.");
            System.out.println(sqle.getMessage());
            System.out.println(str);
        }
    }

    //validated
    public void monthlyReport() throws SQLException {


        Statement addNew = DBconnection.con.createStatement();
        System.out.println("Please select the year and month you want a report for in the format YYYY-MM");
        in.nextLine();
        String delivery_month = in.next();
        boolean validMonth = ds.validateMonth(delivery_month);
        if (validMonth) {

            String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date like '" + delivery_month + "%' order by delivery_date asc;";
            try {
                ResultSet rs = DBconnection.stmt.executeQuery(str);

                System.out.printf("\n%-12s %-20s %-15s %-20s %-25s \n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue per pub");
                while (rs.next()) {
                    int daily_summary_id = rs.getInt("daily_summary_id");
                    String delivery_date = rs.getString("delivery_date");
                    double total_revenue = rs.getDouble("total_revenue");
                    int publications_sold = rs.getInt("publications_sold");
                    double publications_revenue = rs.getDouble("publications_revenue");

                    total_revenue = Math.round(total_revenue * 100.0) / 100.0;
                    publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;

                    System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);
                }

            } catch (
                    SQLException sqle) {
                System.out.println("Error: failed to display all summaries.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
        } else {
            System.out.println("Please enter a month in the format YYYY-MM.\nEnsure your date is in the range of 2011-2099 and the month is in range 1-12");
            monthlyReport();
        }


    }


    //validated
    public void chooseDate() throws SQLException {


        Statement addNew = DBconnection.con.createStatement();
        System.out.println("Please select the date you want a report for in the format YYYY-MM-DD");
        String delivery_input = in.next();
        boolean validDate = ds.validateDate(delivery_input);
        if (validDate) {

                String del="delete from daily_summary where delivery_date = '"+delivery_input+"';";
                String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date = '" + delivery_input + "';";
                try {
                    DBconnection.stmt.executeUpdate(del);
                    createDailyReportByDate(delivery_input);
                    ResultSet rs = DBconnection.stmt.executeQuery(str);

                    System.out.printf("\n%-12s %-20s %-15s %-20s %-25s\n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue");
                    while (rs.next()) {
                        int daily_summary_id = rs.getInt("daily_summary_id");
                        String delivery_date = rs.getString("delivery_date");
                        double total_revenue = rs.getDouble("total_revenue");
                        int publications_sold = rs.getInt("publications_sold");
                        double publications_revenue = rs.getDouble("publications_revenue");

                        total_revenue = Math.round(total_revenue * 100.00) / 100.00;
                        publications_revenue = Math.round(publications_revenue * 100.00) / 100.00;

                        System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);
                    }

                } catch (
                        SQLException sqle) {
                    System.out.println("Error: failed to display all daily summaries.");
                    System.out.println(sqle.getMessage());
                    System.out.println(str);

            }
        } else {
            System.out.println("Please enter your date in the format YYYY-MM-DD\n");
            chooseDate();
        }


    }

    //validated
    public void fromChosenDate() throws SQLException {


        System.out.println("Please select the date you want a report from in the format YYYY-MM-DD");
        String delivery_input = in.next();
        boolean validDate = ds.validateDate(delivery_input);
        if (validDate) {
            createDailyReportByDate(delivery_input);
            String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date BETWEEN '" + delivery_input + "' and '" + modifiedDate + "' order by delivery_date asc";
            try {
                ResultSet rs = DBconnection.stmt.executeQuery(str);

                System.out.printf("\n%-12s %-20s %-15s %-20s %-25s\n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue");
                while (rs.next()) {
                    int daily_summary_id = rs.getInt("daily_summary_id");
                    String delivery_date = rs.getString("delivery_date");
                    double total_revenue = rs.getDouble("total_revenue");
                    int publications_sold = rs.getInt("publications_sold");
                    double publications_revenue = rs.getDouble("publications_revenue");

                    total_revenue = Math.round(total_revenue * 100.0) / 100.0;
                    publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;

                    System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);
                }

            } catch (
                    SQLException sqle) {
                System.out.println("Error: failed to display all daily summaries.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
        } else {
            System.out.println("Please enter a date in the format YYYY-MM-DD\n");
            fromChosenDate();
        }
    }

    //validated
    public void chosenRange() throws SQLException {


        System.out.println("Please enter the first date in the format YYYY-MM-DD");
        String delivery_choice1 = in.next();
        boolean delchoice1 = ds.validateDate(delivery_choice1);
        if (delchoice1) {

                createDailyReportByDate(delivery_choice1);




            System.out.println("Please enter the second date in the format YYYY-MM-DD");
            String delivery_choice2 = in.next();
            boolean delchoice2 = ds.validateDate(delivery_choice2);
            if (delchoice2) {

                    createDailyReportByDate(delivery_choice2);



                String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date BETWEEN '" + delivery_choice1 + "' and '" + delivery_choice2 + "' order by delivery_date asc";
                try {
                    ResultSet rs = DBconnection.stmt.executeQuery(str);

                    System.out.printf("\n%-12s %-20s %-15s %-20s %-25s\n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue");
                    while (rs.next()) {
                        int daily_summary_id = rs.getInt("daily_summary_id");
                        String delivery_date = rs.getString("delivery_date");
                        double total_revenue = rs.getDouble("total_revenue");
                        int publications_sold = rs.getInt("publications_sold");
                        double publications_revenue = rs.getDouble("publications_revenue");

                        total_revenue = Math.round(total_revenue * 100.0) / 100.0;
                        publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;

                        System.out.printf("%-12s %-20s %-15s %-20s %-25s \n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);
                    }

                } catch (
                        SQLException sqle) {
                    System.out.println("Error: failed to display all daily summaries.");
                    System.out.println(sqle.getMessage());
                    System.out.println(str);
                }
//                }
            } else {
                System.out.println("Error with second date: Please enter in format YYYY-MM-DD");
                chosenRange();
            }
            //}
        } else {
            System.out.println("Error with first date: Please enter in format YYYY-MM-DD");
            chosenRange();
        }

    }

    //validated
    public void revenueReport() throws SQLException {


        int menuChoice = 0;

        final int STOP_APP = 8;

        while (menuChoice != STOP_APP) {
            displayReportMenu(); //display the primary menu
            if (in.hasNextInt()) {
                //get the menu choice from the user
                menuChoice = in.nextInt();

                switch (menuChoice) {
                    case 1:
                        dailyReport();
                        break;
                    case 2:
                        weeklyReport();
                        break;
                    case 3:
                        monthlyReport();
                        break;
                    case 4:
                        chooseDate();
                        break;
                    case 5:
                        fromChosenDate();
                        break;
                    case 6:
                        chosenRange();
                        break;
                    case 7:
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


    public void displayReportMenu() {
        System.out.println("\nPlease select which report you would like");
        System.out.println("1: Daily");
        System.out.println("2: Weekly");
        System.out.println("3: Monthly");
        System.out.println("4: User entry");
        System.out.println("5: From chosen date to today");
        System.out.println("6: User Chosen Range");
        System.out.println("7: Main Menu");
        System.out.print("\nEnter your choice: ");
    }


    //validated
    public void monthlyReportFile() throws SQLException, FileNotFoundException {


        Statement addNew = DBconnection.con.createStatement();
        System.out.println("Please select the month you want a report for in the format YYYY-MM");
        String delivery_month = in.next();
        boolean validMonth = ds.validateMonth(delivery_month);
        if (validMonth) {

            String str = "select daily_summary_id,delivery_date, total_revenue, publications_sold, total_revenue/publications_sold as publications_revenue from daily_summary where delivery_date like '" + delivery_month + "%';";
            try {
                ResultSet rs = DBconnection.stmt.executeQuery(str);
                PrintWriter writer1 = null;
                writer1 = new PrintWriter(new File("src\\com\\newspaper\\dailysummary\\summaryfiles\\" + delivery_month + ".txt"));

                writer1.printf("\n%-12s %-20s %-15s %-20s %-25s\n", "Summary ID", "Delivery Date", "Total revenue", "Publications Sold", "Revenue Per Pub");


                for (int i = 0; i < 32; i++) {
                    while (rs.next()) {
                        int daily_summary_id = rs.getInt("daily_summary_id");
                        String delivery_date = rs.getString("delivery_date");
                        double total_revenue = rs.getDouble("total_revenue");
                        int publications_sold = rs.getInt("publications_sold");
                        double publications_revenue = rs.getDouble("publications_revenue");

                        publications_revenue = Math.round(publications_revenue * 100.0) / 100.0;
                        total_revenue = Math.round(total_revenue*100)/100;


                        writer1.printf("%-12s %-20s %-15s %-20s %-25s\n", daily_summary_id, delivery_date, total_revenue, publications_sold, publications_revenue);

                    }
                }

                String sum = "select sum(total_revenue) as Revenue_Total,sum(publications_sold) as Publications_Sold_Total,sum(total_revenue)/sum(publications_sold) as Revenue_Per_Pub_Total from daily_summary where delivery_date like '" + delivery_month + "%'";

                ResultSet re = DBconnection.stmt.executeQuery(sum);

while(re.next()) {



    double revenueSum = re.getDouble("Revenue_Total");
    int publicationsSum = re.getInt("Publications_Sold_Total");
    double revPerPubTotal = re.getDouble("Revenue_Per_Pub_Total");
    revenueSum = Math.round(revenueSum * 100.0) / 100.0;
    revPerPubTotal = Math.round(revPerPubTotal * 100.0) / 100.0;

    writer1.println("--------------------------------------------------------------------------------------------");
    writer1.printf("\n%-25s %-30s %-35s\n", "Revenue Total", "Pubs Sold Total", "Revenue Per Total");
    writer1.printf("%-25s %-30s %-35s\n", revenueSum, publicationsSum, revPerPubTotal);


    writer1.flush();
    writer1.close();
}



            }
            catch (
                    SQLException sqle) {
                System.out.println("Error: failed to display all summaries.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }
            System.out.println("File created successfully!");

        } else {
            System.out.println("Please enter a month in the format YYYY-MM.\nEnsure your date is in the range of 2011-2099 and the month is in range 1-12");
            monthlyReportFile();
        }
    }


    int count=0;
    String st="";
    public DailySummary createDailyReportByDate(String date) throws SQLException {

        DailySummary dailySummary = new DailySummary();



            String del = "DELETE from daily_summary where delivery_date = '"+date+"';";
            DBconnection.stmt.executeUpdate(del);
        String str = "SELECT delivery.delivery_id, delivery.delivery_date, count(delivery.delivery_id) as \"publications_sold\", sum(publication.publication_cost+(publication.publication_cost*0.23))as \"total_revenue\"\n" +
                "FROM delivery, publication\n" +
                "WHERE delivery.publication_id = publication.publication_id AND delivery.delivery_status = 'delivered'\n" +
                "    AND delivery.delivery_date = '" + date + "';";






            try {

                ResultSet rs = DBconnection.stmt.executeQuery(str);


                while (rs.next()) {

                    int publications_sold = rs.getInt("publications_sold");
                    double total_revenue = rs.getDouble("total_revenue");
                    double revenue_per_publication = total_revenue / publications_sold;
                    dailySummary = new DailySummary(date, total_revenue, publications_sold, revenue_per_publication);

                    Statement addNew = DBconnection.con.createStatement();
                    addNew.executeUpdate("insert into daily_summary values(null, '" + date + "'," + total_revenue + ", " + publications_sold + ");");

                }

            } catch (
                    SQLException sqle) {
                System.out.println("Error: failed to display all daily summaries.");
                System.out.println(sqle.getMessage());
                System.out.println(str);
            }


        return dailySummary;
    }


    public static void main(String[] args) throws SQLException {




    }


    public void populateDatabase() throws SQLException {
        boolean validyear = false;
        String enterYear = "";
        String enterMonth ="";
        while(!validyear) {
            System.out.println("Please enter the year you want to populate");
             enterYear = in.next();

            validyear = ds.validateYear(enterYear);
            if(validyear)
            {
                if(enterYear.matches(currYear)||enterYear.matches(lastYearString))
                {
                    validyear = true;
                }
                else
                {
                    System.out.println("Please only enter this year or last year");
                    validyear=false;
                }
            }
            else{
                System.out.println("Invalid Year, please try again");
                validyear=false;
            }
        }

        boolean validmonth=false;
        while(!validmonth) {
            System.out.println("Please enter the month you want to populate");
            enterMonth = in.next();

            if (enterMonth.equals("1") || enterMonth.equals("01")) {
                for (int i = 1; i <= 31; i++) {
                    createDailyReportByDate(enterYear + "-01-" + i);
                    validmonth=true;

                }
            } else if (enterMonth.equals("2") || enterMonth.equals("02")) {
                for (int i = 1; i <= 28; i++) {
                    createDailyReportByDate(enterYear + "-02-" + i);
                    validmonth=true;

                }
            } else if (enterMonth.equals("3") || enterMonth.equals("03")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-03-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("4") || enterMonth.equals("04")) {
                for (int i = 1; i < 30; i++) {
                    createDailyReportByDate(enterYear + "-04-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("5") || enterMonth.equals("05")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-05-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("6") || enterMonth.equals("06")) {
                for (int i = 1; i < 30; i++) {
                    createDailyReportByDate(enterYear + "-06-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("7") || enterMonth.equals("07")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-07-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("8") || enterMonth.equals("08")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-08-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("9") || enterMonth.equals("09")) {
                for (int i = 1; i < 30; i++) {
                    createDailyReportByDate(enterYear + "-09-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("10")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-10-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("11")) {
                for (int i = 1; i < 30; i++) {
                    createDailyReportByDate(enterYear + "-11-" + i);
                    validmonth=true;
                }
            } else if (enterMonth.equals("12")) {
                for (int i = 1; i < 31; i++) {
                    createDailyReportByDate(enterYear + "-012-" + i);
                    validmonth=true;
                }
            } else {
                System.out.println("Invalid month, please try again.");
            }

        }




        String del = "delete from daily_summary where delivery_date between '" + tomorrowDate + "'  AND '" + monthend + "';";
            DBconnection.stmt.executeUpdate(del);
        System.out.println("Databse populated for month of : "+enterYear+"-"+enterMonth+"");
        }

    }





