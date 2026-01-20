package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Seat;
import java.sql.SQLException;
import java.util.List;

public interface SeatRepository {
    List <Seat> getAllSeats() throws SQLException;
    void updateSeatStatus(int id, boolean isBooked) throws SQLException;
    Seat getSeatByID(int id) throws SQLException;
}
