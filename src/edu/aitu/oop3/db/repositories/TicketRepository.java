package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Ticket;
import oop4.Repository;

public interface TicketRepository extends Repository<Ticket> {
    int save(Ticket ticket);
    Ticket findByCode(String code);
}
