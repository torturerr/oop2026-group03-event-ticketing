package eventManagementComponent.builder;
import eventManagementComponent.modles.Event;

import java.time.LocalDateTime;

public class EventBuilder {
    private String eventName;
    private Event.Type eventType;
    private LocalDateTime date;
    private String venue;
    private int totalSeats;

    public EventBuilder(String eventName, Event.Type eventType) {
        this.eventName = eventName;
        this.eventType = eventType;
    }

    public EventBuilder date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public EventBuilder venue(String venue) {
        this.venue = venue;
        return this;
    }

    public EventBuilder totalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
        return this;
    }

    public Event build() {
        // existing constructor
        return new Event(eventName, eventType, date);
    }
}
