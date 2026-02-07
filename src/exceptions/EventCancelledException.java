package exceptions;

public class EventCancelledException extends RuntimeException {
    public EventCancelledException() {
        super("Event is cancelled");
    }
}
