package edu.aitu.oop3.db.services;

import edu.aitu.oop3.db.exceptions.InvalidTicketCodeException;
import edu.aitu.oop3.db.models.Ticket;
import edu.aitu.oop3.db.repositories.TicketRepository;
import java.util.UUID;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final SeatAllocationService seatService;

    public TicketService(TicketRepository ticketRepository, SeatAllocationService seatService) {
        this.ticketRepository = ticketRepository;
        this.seatService = seatService;
    }
    public String buyTicket(int customerID, int eventID, int seatID){
        // check if seat is available
        seatService.reserveSeat(seatID);
        try{
            String ticketCode="TIX-"+UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            Ticket ticket=new Ticket(ticketCode, eventID, seatID, customerID);
            ticketRepository.save(ticket);
            return ticketCode;
        }catch(Exception e){
            throw new RuntimeException("Error while purchasing the ticket", e);
        }
    }
    public Ticket validateTicket(String code) {
        Ticket ticket = ticketRepository.findByCode(code);
        if(ticket == null) {
            throw new InvalidTicketCodeException();
        }
        return ticket;
    }
}
