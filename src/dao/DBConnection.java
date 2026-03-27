package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // ⚠️ Change these to match YOUR MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "root";       // your MySQL username
    private static final String PASSWORD = "PASSword@12345";   // your MySQL password

    private static Connection connection = null;

    public static Connection getConnection() {
    if (connection == null) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Connection error: " + e.getMessage());
        }
    }
    return connection;
}

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}