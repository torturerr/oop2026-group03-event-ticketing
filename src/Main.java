import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.PostgresDatabase;

public class Main {
    public static void main(String[] args) {
        DatabaseInterface db = new PostgresDatabase();
        System.out.println("Connecting to Supabase...");

        try (Connection connection = db.getConnection()) {
            System.out.println("Connected successfully!");

            String sql = "SELECT CURRENT_TIMESTAMP";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Database time: " + rs.getTimestamp(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }
    }
}