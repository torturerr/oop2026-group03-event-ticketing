package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Event;
import oop4.Repository;

public interface EventRepository extends Repository<Event> {
    Event cancelEvent(int EventId);;
}
