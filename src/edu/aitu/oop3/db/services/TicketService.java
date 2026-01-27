package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.exceptions.InvalidTicketCodeException;
import edu.aitu.oop3.db.models.Ticket;
import edu.aitu.oop3.db.repositories.TicketRepository;
import java.util.UUID;
/**
 * Service class to manage ticket operations.
 * It connects different parts of the system (Tickets and Seats).
 */
public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeatAllocationService seatService;
    // Dependency Injection: taking existing repository and service
    public TicketService(TicketRepository ticketRepository, SeatAllocationService seatService) {
        this.ticketRepository = ticketRepository;
        this.seatService = seatService;
    }

    //Main logic to buy a ticket.
    public String buyTicket(int customerID, int eventID, int seatID){
        // check if seat is available
        seatService.reserveSeat(seatID);
        try{
            //Generate a unique random ticket code
            String ticketCode="TIX-"+UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            //Create a new Ticket object
            Ticket ticket=new Ticket(ticketCode, eventID, seatID, customerID);
            //Save ticket to the Database
            ticketRepository.save(ticket);
            return ticketCode;
        }catch(Exception e){
            // If something fails, wrap the error and throw it
            throw new RuntimeException("Error while purchasing the ticket", e);
        }
    }

    //Logic to check if a ticket exists.
    public Ticket validateTicket(String code) {
        Ticket ticket = ticketRepository.findByCode(code);
        // Custom Exception if ticket is not found
        if(ticket == null) {
            throw new InvalidTicketCodeException();
        }
        return ticket;
    }
}
