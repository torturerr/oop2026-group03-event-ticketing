package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Event;

public interface EventRepository {
    int save(Event e);
    Event findById(int id);
    Event cancelEvent(int EventId);;
}
