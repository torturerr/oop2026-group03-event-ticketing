package repositories;

import eventManagementComponent.modles.Seat;

import java.sql.SQLException;
import java.util.List;

public interface SeatRepository extends Repository<Seat> {
    List <Seat> getAllSeats(int eventId) throws SQLException;
    void updateSeatStatus(int id, boolean isBooked) throws SQLException;
}
