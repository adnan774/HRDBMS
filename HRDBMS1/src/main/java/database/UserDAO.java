package database;

import models.User;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    User user = null;
    Connection conn = null;
    Statement stmt = null;
    String userDB = "root1";
    String password = "password1";
    String url = "jdbc:mysql://localhost:3306/hrdbms";

    private void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Error loading the JDBC driver: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        try {
            conn = DriverManager.getConnection(url, userDB, password);
            stmt = conn.createStatement();
            System.out.println("Database connection successfully established.");
        } catch (SQLException se) {
            System.out.println("Error while connecting to the database: " + se.getMessage());
            se.printStackTrace();
        }
    }

    private void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    

}
