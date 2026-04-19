package com.bank.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TECHNICAL DEMONSTRATION ONLY:
 * This class shows how a traditional JDBC connection is established.
 * In our Spring Boot app, this is handled automatically by the HikariCP connection pool,
 * but this code shows the underlying logic.
 */
public class DBConnection {

    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/mpj_vault";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // Update if you have a password

    /**
     * This method demonstrates the 4 steps of JDBC:
     * 1. Register the Driver
     * 2. Initialize the Connection
     * 3. Send SQL (Handled in the Service layer)
     * 4. Close the connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Step 1: Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Step 2: Establish the connection
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            System.out.println("JDBC Connection Successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return connection;
    }
}
