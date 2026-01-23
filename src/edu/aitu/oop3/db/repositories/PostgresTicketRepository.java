package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.models.Ticket;

import java.sql.*; // to use connection, prepared statements, set result etc.

public class PostgresTicketRepository implements TicketRepository {
    private final DatabaseInterface db;

    public PostgresTicketRepository(DatabaseInterface db) {
        this.db = db;
    }

    // save ticket to the database
    @Override
    public void save(Ticket ticket) {
        String sql = "INSERT INTO tickets(ticket_code, event_id, seat_id, customer_id) VALUES (?, ?, ?, ?)";

        try(Connection c = db.getConnection();
            PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, ticket.getTicketCode());
            st.setInt(2, ticket.getEventId());
            st.setInt(3, ticket.getSeatId());
            st.setInt(4, ticket.getCustomerId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Could not save ticket!", e);
        }
    }

    // Get ticket from the database
    @Override
    public Ticket findByCode(String code) {
        String sql = "SELECT * FROM tickets WHERE id = ?";

        try (Connection c = db.getConnection();
        PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, code); // put id to the statement

            // the result of the statement is saved in rs
            try(ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                return new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getInt("customer_id")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not find ticket by the ID!", e);
        }
    }
}
