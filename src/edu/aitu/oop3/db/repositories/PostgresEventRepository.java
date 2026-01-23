package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.models.Event;
import java.sql.*;

public class PostgresEventRepository implements EventRepository {
    private final DatabaseInterface db;

    public PostgresEventRepository(DatabaseInterface db) {
        this.db = db;
    }

    // methods
    // save event to the database
    @Override
    public int save(Event event) {
        String sql = "INSERT INTO events(event_name,type,status, event_date) VALUES(?,?,?, ?) RETURNING id";

        try(Connection c = db.getConnection();
            PreparedStatement pst = c.prepareStatement(sql)) {

            pst.setString(1, event.getEventName());
            pst.setString(2, event.getType().name()); // name() return the enum constant exactly as it is.
            pst.setString(3, event.getStatus().name());
            pst.setTimestamp(4, Timestamp.valueOf(event.getDate()));

            //pst.executeUpdate(); no need for this as we're using RETURNING
            // Here: INSERT + get id in ONE step, using executQuery
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    throw new RuntimeException("Failed to retrieve event ID");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not save event!", e);
        }
    }
    // get event from the database
    @Override
    public Event findById(int id) {
        String sql = "SELECT * FROM events WHERE id = ?";

        try(Connection c = db.getConnection();
            PreparedStatement st = c.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null; // not found

                String typeStr = rs.getString("type");
                String statusStr = rs.getString("status");
                return new Event(
                        rs.getInt("id"),
                        rs.getString("event_name"),
                        Event.Type.valueOf(typeStr),
                        Event.Status.valueOf(statusStr), // valueOf() converts the string back to enum
                        rs.getTimestamp("event_date").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find the event by Id!", e);
        }
    }
}
