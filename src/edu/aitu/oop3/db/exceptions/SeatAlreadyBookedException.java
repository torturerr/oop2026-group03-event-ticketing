package edu.aitu.oop3.db.exceptions;

public class SeatAlreadyBookedException extends Exception {
    public SeatAlreadyBookedException(String message){
        super(message);
    }
}
