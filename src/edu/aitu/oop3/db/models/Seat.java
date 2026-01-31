package edu.aitu.oop3.db.models;

public class Seat {
    // Seat details: ID, location (row/number), status, and event link
    private int id;
    private int row;
    private int number;
    private boolean booked;
    private int eventId;
    // Use this to create a NEW seat (not yet in Database)
    public Seat(int row, int number, int eventId) {
        this.row = row;
        this.number = number;
        this.booked = false;
        this.eventId = eventId;
    }
    // Use this when loading a seat from DB (without knowing status yet)
    public Seat(int id, int row, int number, int eventId){
        this.id=id;
        this.row=row;
        this.number=number;
        this.booked=false;
        this.eventId=eventId;
    }
    // Use this to load a seat from DB WITH its current booked status
    public Seat(int id, int row, int number, boolean booked, int eventId) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.booked = booked;
        this.eventId = eventId;
    }
    // Getters to read data
    public int getId(){return id;}
    public int getRow(){return row;}
    public int getNumber(){return number;}
    public boolean getBooked(){return booked;}
    public int getEventId(){return eventId;}
    // Setter to change status (e.g., mark as booked after purchase)
    public void setBooked(boolean booked){this.booked=booked;}
    public void setId(int id){this.id=id;}
    // Simple text display of seat info
    @Override
    public String toString(){
        return "Seat: "+id+"Row: "+row+"Number: "+number+"Booked: "+booked;
    }
}
