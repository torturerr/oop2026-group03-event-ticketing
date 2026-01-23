package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.exceptions.InvalidTicketCodeException;
import edu.aitu.oop3.db.models.Ticket;
import edu.aitu.oop3.db.repositories.TicketRepository;
import java.util.UUID;

public class TicketService {
    private final TicketRepository ticketRepository;
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    public void buyTicket(int customerID, int eventID, int seatID){
        try{
            String ticketCode="TIX-"+UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Ticket ticket=new Ticket(ticketCode, customerID, eventID, seatID);
            ticketRepository.save(ticket);
            System.out.println("Ticket has been purchased, your code: "+ticketCode);
        }catch(Exception e){
            throw new RuntimeException("Error while purchasing the ticket", e);
        }
    }
    public void validateTicket(String code) {
        Ticket ticket = ticketRepository.findByCode(code);
        if(ticket == null) {
            throw new InvalidTicketCodeException();
        }
        System.out.println("The ticket is valid for an event № " + ticket.getEventId());
    }
}
