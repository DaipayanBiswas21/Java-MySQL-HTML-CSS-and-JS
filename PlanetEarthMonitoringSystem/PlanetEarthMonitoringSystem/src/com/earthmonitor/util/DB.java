package com.earthmonitor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.earthmonitor.exception.MyException;

public class DB {
    // Hardcoded DB credentials
    // private static final String URL = "jdbc:mysql://" + IP_ADDRESS + ":3306/earth_monitor?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String URL = "jdbc:mysql://localhost:3306/earth_monitor?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useLegacyDatetimeCode=false";
    private static final String USER = "root";
    private static final String PASSWORD = "daipayanb90@";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }

    /**
     * Initializes the DB connection for testing purposes.
     * 
     */
    public static void init() throws SQLException {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connection established successfully.");
            }
        } catch (MyException e) {
            System.err.println("❌ Database connection failed during init: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws MyException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new MyException("Database connection failed: " + e.getMessage(), e);
        }
    }
}

