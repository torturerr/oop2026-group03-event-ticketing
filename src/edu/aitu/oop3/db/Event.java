package edu.aitu.oop3.db;

public class Event {
    private int id;
    private static int idGen = 0;
    private String eventName;
    private enum Type {CONCERT, CINEMA, EDUCATION}
    private enum Status{ACTIVE, CANCELLED, COMPLETED}

    // constructor
    public void Event(String eventName) {
        id = idGen++;
        setEventName(eventName);
    }
    // getters and setters
    public void setEventName(String eventName) {
        if (eventName == null) {
            throw new IllegalArgumentException("eventName cannot be null");
        }  else {
            this.eventName = eventName;
        }

    }
}
