package edu.aitu.oop3.db.exceptions;

public class InvalidTicketCodeException extends RuntimeException {
    public InvalidTicketCodeException() {
        super("Invalid ticket code");
    }
}
