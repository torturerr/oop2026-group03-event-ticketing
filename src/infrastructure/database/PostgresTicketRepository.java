package infrastructure.database;

import repositories.TicketRepository;
import ticketSalesComponent.modle.Ticket;

import java.sql.*; // to use connection, prepared statements, set result etc.

public class PostgresTicketRepository implements TicketRepository {
    private final DatabaseInterface db;

    public PostgresTicketRepository(DatabaseInterface db) {
        this.db = db;
    }

    // save ticket to the database
    @Override
    public int save(Ticket ticket) {
        // To do: edit: return ID and set ID to the object
        String sql = "INSERT INTO tickets (ticket_code, event_id, seat_id, customer_id, type, price, final_price)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try(Connection c = db.getConnection();
            PreparedStatement st = c.prepareStatement(sql)) {

            st.setString(1, ticket.getTicketCode());
            st.setInt(2, ticket.getEventId());
            st.setInt(3, ticket.getSeatId());
            st.setInt(4, ticket.getCustomerId());
            st.setString(5, ticket.getType().name()); // enum → String
            st.setDouble(6, ticket.getPrice());
            st.setDouble(7, ticket.getFinalPrice());

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    ticket.setId(id);
                    return id;
                }
                else {
                    throw new RuntimeException("Failed to retrieve id from database.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not save ticket!", e);
        }
    }
    // Get ticket from the database
    @Override
    public Ticket findByCode(String code) {
        String sql = "SELECT * FROM tickets WHERE ticket_code = ?";

        try (Connection c = db.getConnection();
        PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, code); // put id to the statement

            // the result of the statement is saved in rs
            try(ResultSet rs = st.executeQuery()) {
                if (!rs.next()) return null;

                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getString("ticket_code"),
                        rs.getInt("event_id"),
                        rs.getInt("seat_id"),
                        rs.getInt("customer_id")
                );
                // adding the new fields
                ticket.setType(Ticket.Type.valueOf(rs.getString("type")));
                ticket.setPrice(rs.getDouble("price"));
                ticket.setFinalPrice(rs.getDouble("final_price"));

                return ticket;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not find ticket by the ID!", e);
        }
    }
    //Generic methods
    @Override
    public Ticket findById(int id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    @Override
    public java.util.List<Ticket> findAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
