package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.models.Seat;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresSeatRepository implements SeatRepository{
    private final DatabaseInterface db;
    public PostgresSeatRepository(DatabaseInterface db) {
        this.db = db;
    }
    @Override
    public List<Seat> getAllSeats() throws SQLException {
        List<Seat> list = new ArrayList<>();
        String sql = "SELECT * FROM seats ORDER BY row_num, seat_num";
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Seat seat = new Seat(
                        rs.getInt("id"),
                        rs.getInt("row_num"),
                        rs.getInt("seat_num")
                );
                seat.setBooked(rs.getBoolean("is_booked"));
                list.add(seat);
            }
        }
        return list;
    }
    @Override
    public void updateSeatStatus(int id, boolean isBooked) throws SQLException {
        String sql = "UPDATE seats SET is_booked = ? WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isBooked);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }
    @Override
    public Seat getSeatByID(int id) throws SQLException {
        String sql = "SELECT * FROM seats WHERE id = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Seat seat = new Seat(rs.getInt("id"), rs.getInt("row_num"), rs.getInt("seat_num"));
                    seat.setBooked(rs.getBoolean("is_booked"));
                    return seat;
                }
            }
        }
        return null;
    }
}
