package edu.aitu.oop3.db.models;

public class Ticket {
    private int id;
    private String ticketCode;
    private int eventId;
    private int seatId;
    private int customerId;

    public Ticket(String ticketCode, int eventId, int seatId, int customerId) {
        this.ticketCode = ticketCode;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerId = customerId;
    }

    // for loading from DB (Ticket has id)
    public Ticket(int id, String ticketCode, int eventId, int seatId, int customerId) {
        this.id = id;
        this.ticketCode = ticketCode;
        this.eventId = eventId;
        this.seatId = seatId;
        this.customerId = customerId;
    }

    // Getters and Setters
    public int getId() {return id;}
    public String getTicketCode() {return ticketCode;}
    public int getEventId() {return eventId;}
    public int getSeatId() {return seatId;}
    public int getCustomerId() {return customerId;}

}
