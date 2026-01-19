package edu.aitu.oop3.db;
import java.time.LocalDateTime;
public class Event {
    private int id; // id is serial in the database
    private String eventName;
    private Type type;
    private Status status;
    private LocalDateTime date;

    public enum Type {CONCERT, CINEMA, THEATRE, EDUCATION}
    public enum Status{ACTIVE, CANCELLED, COMPLETED}

    // constructors
    public Event(String eventName, Type type, LocalDateTime date) {
        this.type = type;
        setEventName(eventName);
        this.status = Status.ACTIVE; //default
        this.date = date;
    }
    public Event (int id, String eventName, Type type, Status status, LocalDateTime date) {
        this.id = id;
        this.eventName = eventName;
        this.type = type;
        this.status = status;
        this.date = date;
    }

    // getters and setters
    public int getId() { return id; }
    public String getEventName() { return eventName; }
    public Type getType() { return type; }
    public Status getStatus() { return status; }
    public LocalDateTime getDate() { return date; }

    public void setEventName(String eventName) {
        if (eventName == null || eventName.isBlank()) {
            throw new IllegalArgumentException("eventName cannot be null");
        }  else {
            this.eventName = eventName;
        }
    }
    public void setStatus(Status status) { this.status = status; }

    // used by repository after INSERT
    public void setId(int id) {
        this.id = id;
    }
}
