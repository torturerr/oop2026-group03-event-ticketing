package repositories;

import eventManagementComponent.modles.Event;

public interface EventRepository extends Repository<Event> {
    Event cancelEvent(int EventId);;
}
