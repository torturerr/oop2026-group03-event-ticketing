package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Seat;
import oop4.Repository;

import java.sql.SQLException;
import java.util.List;

public interface SeatRepository extends Repository<Seat> {
    List <Seat> getAllSeats(int eventId) throws SQLException;
    void updateSeatStatus(int id, boolean isBooked) throws SQLException;
}
