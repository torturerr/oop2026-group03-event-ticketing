package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.models.Event;
import java.sql.*;

public class EventRepository {
    private final DatabaseInterface db;

    public EventRepository(DatabaseInterface db) {
        this.db = db;
    }

    // methods
    // save event to the database
    public void save(Event event) {
        String sql = "INSERT INTO events(event_name,type,status, event_date) VALUES(?,?,?, ?)";

        try(Connection c = db.getConnection();
            PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, event.getEventName());
            st.setString(2, event.getType().name()); // name() return the enum constant exactly as it is.
            st.setString(3, event.getStatus().name());
            st.setTimestamp(4, Timestamp.valueOf(event.getDate()));

            st.executeUpdate(); // to ensure the update after Insert
        } catch (SQLException e) {
            throw new RuntimeException("Could not save event!", e);
        }
    }
    // get event from the database
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
