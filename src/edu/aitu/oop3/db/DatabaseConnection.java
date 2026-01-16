package edu.aitu.oop3.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String URL =
            "jdbc:postgresql://aws-1-ap-south-1.pooler.supabase.com:5432/postgres?user=postgres.qrrtpdmvgzqotlfxdmjy&password=sisimasisi229";
    private static final String USER = "postgres";
    private static final String PASSWORD = "sisimasisi229"; // ← DATABASE PASSWORD
    private DatabaseConnection() {
        // no instances
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
