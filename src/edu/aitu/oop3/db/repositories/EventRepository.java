package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Event;
import oop4.Repository;

public interface EventRepository extends Repository<Event> {
    int save(Event e);
    Event findById(int id);
    Event cancelEvent(int EventId);;
}
