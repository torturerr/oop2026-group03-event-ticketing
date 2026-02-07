package exceptions;

public class InvalidTicketCodeException extends RuntimeException {
    public InvalidTicketCodeException() {
        super("Invalid ticket code");
    }
}
