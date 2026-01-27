package edu.aitu.oop3.db.models;

public class Seat {
    private int id;
    private int row;
    private int number;
    private boolean booked;
    private int eventId;

    public Seat(int row, int number, int eventId) {
        this.row = row;
        this.number = number;
        this.booked = false;
        this.eventId = eventId;
    }

    public Seat(int id, int row, int number, int eventId){
        this.id=id;
        this.row=row;
        this.number=number;
        this.booked=false;
        this.eventId=eventId;
    }

    public Seat(int id, int row, int number, boolean booked, int eventId) {
        this.id = id;
        this.row = row;
        this.number = number;
        this.booked = booked;
        this.eventId = eventId;
    }
    public int getId(){return id;}
    public int getRow(){return row;}
    public int getNumber(){return number;}
    public boolean getBooked(){return booked;}
    public int getEventId(){return eventId;}

    public void setBooked(boolean booked){this.booked=booked;}
    public void setId(int id){this.id=id;};
    @Override
    public String toString(){
        return "Seat: "+id+"Row: "+row+"Number: "+number+"Booked: "+booked;
    }
}
