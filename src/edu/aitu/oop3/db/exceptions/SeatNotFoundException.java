package edu.aitu.oop3.db.exceptions;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(int id) {
        super("Seat with id: " + id + " not found!");
    }
}
