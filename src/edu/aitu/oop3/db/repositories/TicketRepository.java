package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Ticket;

public interface TicketRepository {
    void save(Ticket ticket);
    Ticket findByCode(String code);
}
