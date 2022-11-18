package com.newspaper.db;

import java.sql.*;

public class DBconnection {
    public static Connection con = null;
    public static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args)
    {
        init_db();  // open the connection to the database
    }
    public static void init_db()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/databaseGroupProject?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "admin");
            stmt = con.createStatement();
        }
        catch(Exception e)
        {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }

    public static void cleanup_resources() {
        try {
            con.close();
        } catch (SQLException sqle) {
            System.out.println("Error: failed to close the database");
        }
    }

}
