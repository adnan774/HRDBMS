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
    
    public User getUserByUsername(String username) {
        User user = null;
        openConnection();
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return user;
    }
    
    public void hashPasswords() {
    	// Method to hash existing plain text passwords stored in database
        openConnection();
        try {
            String selectSql = "SELECT user_id, password FROM users";
            Statement selectStmt = conn.createStatement();
            ResultSet rs = selectStmt.executeQuery(selectSql);

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String plainPassword = rs.getString("password");

                String salt = BCrypt.gensalt();
                String hashedPassword = BCrypt.hashpw(plainPassword, salt);

                String updateSql = "UPDATE users SET password = ? WHERE user_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, hashedPassword);
                updateStmt.setInt(2, userId);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
   
    

}
