package com.newspaper.dailysummary;

import com.newspaper.db.DBconnection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailySummary {

    private String delivery_date;
    private double total_revenue;
    private int publications_sold;
    private int daily_summary_id;
    private double revenue_per_publication;

    @Override
    public String toString() {
        return "delivery date, total revenue, publications sold, revenue per pub\n" +
                 delivery_date +"       "+ total_revenue+"      "+publications_sold+"       "+revenue_per_publication;

    }

    public DailySummary(String delivery_date, double total_revenue, int publications_sold, double revenue_per_publication) {
        this.delivery_date = delivery_date;
        this.total_revenue = total_revenue;
        this.publications_sold = publications_sold;
        this.revenue_per_publication = revenue_per_publication;
    }
    public DailySummary() {

    }


    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public void setTotal_revenue(double total_revenue) {
        this.total_revenue = total_revenue;
    }

    public void setPublications_sold(int publications_sold) {
        this.publications_sold = publications_sold;
    }

    public void setDaily_summary_id(int daily_summary_id) {
        this.daily_summary_id = daily_summary_id;
    }


    public int getDaily_summary_id() {
        return daily_summary_id;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public double getTotal_revenue() {
        return total_revenue;
    }

    public int getPublications_sold() {
        return publications_sold;
    }

    public double getRevenue_per_publication() {
        return revenue_per_publication;
    }

    public double setPublications_Revenue() {
        return revenue_per_publication;
    }


    public static void displayDailySummaryMainMenu() {
        System.out.println("\nSummary Menu");
        System.out.println("1: Revenue Report");
        System.out.println("2: Save Monthly Report");
        System.out.println("3: Populate Database");
        System.out.println("4: Exit Menu\n");
        System.out.print("Enter your choice: ");
    }


    public static boolean validateDate(String date) {
        if (date.matches("^20[0-2][0-9]-((0[1-9])|(1[0-2]))-([0-2][0-9]|3[0-1])$")) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean validateYear(String year) {
        if (year.matches("^20((1[1-9])|([2-9][0-9]))$")) {

            return true;
        } else
            return false;
    }

    public static boolean validateMonth(String month) {
        if (month.matches("^20((1[1-9])|([2-9][0-9]))\\-(0[1-9]|1[012])")) {
            return true;
        } else
            return false;
    }


    public static boolean checkIfExists(String date) throws SQLException {
        String st = "select count(*) as total from daily_summary where delivery_date = '" + date+"'";
        ResultSet rss = DBconnection.stmt.executeQuery(st);
        int count = 0;
        while (rss.next()) {
            count = rss.getInt("total");
        }
        if (count > 0){
            return true;
        }
        else
            return false;
    }

}
