package eventManagementComponent.services;

import eventManagementComponent.modles.Event;
import repositories.EventRepository;
import reportingComponent.SearchResult;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class EventService {
    private final EventRepository eventRepo; //SOLID: service should depend on the abstraction, not the database implementation
    private final SeatAllocationService seatAllocationService;

    public EventService(EventRepository eventRepo,SeatAllocationService seatAllocationService) {
        this.eventRepo = eventRepo;
        this.seatAllocationService = seatAllocationService;
    }

    public int createEvent(Event event, int rows, int seatsPerRow) {
        // Note to myself: Input event felids directly from Main. Then save event in the repository
        try {
            int eventId= eventRepo.save(event); // save method returns the id of the event
            seatAllocationService.initializeSeats(eventId, rows, seatsPerRow);
            return eventId;
        } catch (SQLException e) {
            System.out.println("Something went wrong while creating the event");
            return -1;
        }
    }


    public Event cancelEvent (int eventId) {
        Event event = eventRepo.cancelEvent(eventId);
        event.setStatus(Event.Status.CANCELLED);
        return event;
    }

    public SearchResult<Event> getSortedByDate() {
        List<Event> events = eventRepo.findAll();

        // sort events by date using a lambda expression
        events.sort(Comparator.comparing(e -> e.getDate()));

        return new SearchResult<>(events);
    }
}
