package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.exceptions.SeatAlreadyBookedException;
import edu.aitu.oop3.db.models.Seat;
import edu.aitu.oop3.db.repositories.SeatRepository;
import java.sql.SQLException;
import java.util.List;

public class SeatAllocationService {
    private final SeatRepository seatRepo;
    public SeatAllocationService(SeatRepository seatRepo){
        this.seatRepo=seatRepo;
    }
    public void viewSittingLayout(int eventId) throws SQLException{
        List<Seat> seats = seatRepo.getAllSeats(eventId);
        System.out.println("\n===== HALL SCHEME =====");
        for(Seat seat:seats){
            String icon=seat.getBooked() ? "[X]":"[ ]";
            System.out.print(icon+"Row"+seat.getRow()+"Place"+seat.getNumber()+"|");
            if (seat.getNumber()%5==0) System.out.println();
        }
        System.out.println("======================\\n");
    }
    public void ReserveSeat(int seatId) throws SQLException, SeatAlreadyBookedException{
        Seat seat=seatRepo.getSeatByID(seatId);
        if (seat != null&&seat.getBooked()){
            throw new SeatAlreadyBookedException("ERROR: Seat №" + seatId + " is already taken!");
        }
        seatRepo.updateSeatStatus(seatId, true);
        System.out.println("Seat №" + seatId + " is booked.");
    }
}
