package eventManagementComponent.services;

import exceptions.EventCancelledException;
import exceptions.SeatAlreadyBookedException;
import exceptions.SeatNotFoundException;
import eventManagementComponent.modles.Event;
import eventManagementComponent.modles.Seat;
import repositories.EventRepository;
import repositories.SeatRepository;

import java.sql.SQLException;
import java.util.List;

public class SeatAllocationService {
    private final SeatRepository seatRepository;
    private final EventRepository eventRepository;

    public SeatAllocationService(SeatRepository seatRepository, EventRepository eventRepository) {
        this.seatRepository = seatRepository;
        this.eventRepository = eventRepository;
    }

    public void initializeSeats (int eventId, int rows, int seatsPerRow) {
        System.out.println("Initializing seats for eventId = " + eventId);
        // check if event is null or canceled
        try {
            Event event = eventRepository.findById(eventId);
            if (event == null) throw new RuntimeException("Event not found");
            if (event.getStatus() == Event.Status.CANCELLED) throw new EventCancelledException();

            System.out.println("Event found, inserting seats...");
            // Save the seats in the database
            for (int i = 1; i <= rows; i++) { // i saves row number
                for (int j = 1; j <= seatsPerRow; j++) { // j saves seat number
                    Seat seat = new Seat(i, j, eventId);
                    int seatId = seatRepository.save(seat); // save id that comes from the DB
                    seat.setId(seatId);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void reserveSeat(int id) {
        //Repositories throw technical exceptions (SQLException)
        //Services convert them into business exceptions (RuntimeException)
        try {
            Seat seat = seatRepository.findById(id); // throws SQL Exception

            if (seat == null) {
                throw new SeatNotFoundException(id);
            }

            if (seat.getBooked()) {
                throw new SeatAlreadyBookedException();
            }
            // change seat to booked
            seatRepository.updateSeatStatus(id, true);

        } catch (Exception e) {
            // here we convert technical error -> business-level error
            throw new RuntimeException("Database error while reserving seat!", e);
        }
    }

    
    public List<Seat> viewSeatingLayout(int eventId) {
        try {
            return seatRepository.getAllSeats(eventId);
        } catch (Exception e) {
            throw new RuntimeException("Database error while viewing seating layout!", e);
        }
    }
}
