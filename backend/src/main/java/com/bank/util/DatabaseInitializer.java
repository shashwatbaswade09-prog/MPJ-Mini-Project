package com.bank.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            // Create Customers table
            stmt.execute("CREATE TABLE IF NOT EXISTS customers (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100), " +
                    "email VARCHAR(100))");

            // Create Accounts table (with role)
            stmt.execute("CREATE TABLE IF NOT EXISTS accounts (" +
                    "account_id INT PRIMARY KEY, " +
                    "customer_id INT, " +
                    "balance DOUBLE PRECISION, " +
                    "password VARCHAR(100), " +
                    "role VARCHAR(20) DEFAULT 'USER')");

            // Create Transactions table
            stmt.execute("CREATE TABLE IF NOT EXISTS transactions (" +
                    "id SERIAL PRIMARY KEY, " +
                    "account_id INT, " +
                    "type VARCHAR(50), " +
                    "amount DOUBLE PRECISION, " +
                    "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Create Loans table
            stmt.execute("CREATE TABLE IF NOT EXISTS loans (" +
                    "id SERIAL PRIMARY KEY, " +
                    "account_id INT, " +
                    "amount DOUBLE PRECISION, " +
                    "status VARCHAR(20) DEFAULT 'PENDING', " +
                    "request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            // Seed initial data if empty
            stmt.execute("INSERT INTO customers (id, name, email) VALUES (1, 'Admin User', 'admin@bank.com') ON CONFLICT (id) DO NOTHING");
            stmt.execute("INSERT INTO customers (id, name, email) VALUES (2, 'John Doe', 'john@gmail.com') ON CONFLICT (id) DO NOTHING");
            stmt.execute("INSERT INTO customers (id, name, email) VALUES (3, 'Jane Smith', 'jane@gmail.com') ON CONFLICT (id) DO NOTHING");

            stmt.execute("INSERT INTO accounts (account_id, customer_id, balance, password, role) VALUES (100, 1, 10000.0, 'admin123', 'ADMIN') ON CONFLICT (account_id) DO NOTHING");
            stmt.execute("INSERT INTO accounts (account_id, customer_id, balance, password, role) VALUES (101, 2, 500.0, 'user123', 'USER') ON CONFLICT (account_id) DO NOTHING");
            stmt.execute("INSERT INTO accounts (account_id, customer_id, balance, password, role) VALUES (102, 3, 1000.0, 'user456', 'USER') ON CONFLICT (account_id) DO NOTHING");

            System.out.println("Database Initialized Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if needed
    }
}
