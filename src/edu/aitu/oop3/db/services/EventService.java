package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.models.Event;
import edu.aitu.oop3.db.repositories.EventRepository;

public class EventService {
    private final EventRepository eventRepo; //SOLID: service should depend on the abstraction, not the database implementation
    private final SeatAllocationService seatAllocationService;

    public EventService(EventRepository eventRepo,SeatAllocationService seatAllocationService) {
        this.eventRepo = eventRepo;
        this.seatAllocationService = seatAllocationService;
    }

    public int createEvent(Event event, int rows, int seatsPerRow) {
        // Note to myself: Input event felids directly from Main. Then save event in the repository
        int eventId= eventRepo.save(event); // save method returns the id of the event
        seatAllocationService.initializeSeats(eventId, rows, seatsPerRow);
        return eventId;
    }
}
