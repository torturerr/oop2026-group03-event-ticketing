package ticketSalesComponent.factory;

import eventManagementComponent.modles.Event;
import ticketSalesComponent.modle.Ticket;

public class TicketFactory {

    public static Ticket createTicket(int eventId, int seatId, int customerId, Event.Type eventType, Ticket.Type ticketType) {
        Ticket ticket = new Ticket(); // this the default constructor
        ticket.setEventId(eventId);
        ticket.setSeatId(seatId);
        ticket.setCustomerId(customerId);
        ticket.setType(ticketType);

        double basePrice = basePriceForEventType(eventType);
        double multiplier = multiplierForTicketType(ticketType);
        ticket.setPrice(basePrice * multiplier);
        ticket.setFinalPrice(basePrice * multiplier); // discount later

        return ticket;
    }

    private static double basePriceForEventType(Event.Type eventType) {
        switch (eventType) {
            case CONCERT: return 10.0;
            case  CINEMA: return 5.0;
            case THEATER: return 8.0;
            case EDUCATION: return 5.5;
            default: return 6.5;
        }
    }

    private static double  multiplierForTicketType(Ticket.Type ticketType) {
        if (ticketType == null) return 1.0;
        switch (ticketType) {
            case VIP: return 1.6; // Vip cost more than the others
            case STUDENT: return 1.0;
            case STANDARD: return 1.0;
            default: return 1.0;
        }
    }
}
