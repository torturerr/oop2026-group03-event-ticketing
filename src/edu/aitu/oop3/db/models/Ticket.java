package edu.aitu.oop3.db.models;

public class Ticket {
    private int id;
    private String ticketCode;
    private int eventId;
    private int seatId;
    private int customerId;
    private Type type;
    private double price;       // base price
    private double finalPrice;  // after discount

    public enum Type {
        STANDARD,
        STUDENT,
        VIP
    }

    // default constructor for the factory
    public Ticket() {
    }

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
    public Type getType() {return type;}
    public double getPrice() {return price;}
    public double getFinalPrice() {return finalPrice;}

    public void setId(int id) {this.id = id;}
    public void setTicketCode(String ticketCode) {this.ticketCode = ticketCode;}
    public void setEventId(int eventId) {this.eventId = eventId;}
    public void setSeatId(int seatId) {this.seatId = seatId;}
    public void setCustomerId(int customerId) {this.customerId = customerId;}
    public void setType(Type type) {this.type = type;}
    public void setPrice(double price) {this.price = price;}
    public void setFinalPrice(double finalPrice) {this.finalPrice = finalPrice;}
}
