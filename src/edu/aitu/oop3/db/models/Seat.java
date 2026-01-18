package edu.aitu.oop3.db.models;

public class Seat {
    private int id;
    private int row;
    private int number;
    private boolean booked;
    public Seat(int id, int row, int number){
        this.id=id;
        this.row=row;
        this.number=number;
        this.booked=false;
    }
    public int getId(){return id;}
    public int getRow(){return row;}
    public int getNumber(){return number;}
    public boolean getBooked(){return booked;}
    public void setBooked(boolean booked){this.booked=booked;}
    @Override
    public String toString(){
        return "Seat: "+id+"Row: "+row+"Number: "+number+"Booked: "+booked;
    }
}
