package repositories;

import ticketSalesComponent.modle.Ticket;

public interface TicketRepository extends Repository<Ticket> {
    Ticket findByCode(String code);
}
