import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.List;

import eventManagementComponent.modles.Event;
import eventManagementComponent.modles.Seat;
import eventManagementComponent.services.EventService;
import eventManagementComponent.services.SeatAllocationService;
import exceptions.SeatAlreadyBookedException;
import infrastructure.database.DatabaseInterface;
import infrastructure.database.PostgresDatabase;
import infrastructure.database.PostgresCustomersRepository;
import infrastructure.database.PostgresEventRepository;
import infrastructure.database.PostgresSeatRepository;
import infrastructure.database.PostgresTicketRepository;
import models.Customers;
import repositories.CustomersRepository;
import repositories.EventRepository;
import repositories.SeatRepository;
import repositories.TicketRepository;
import ticketSalesComponent.modle.Ticket;
import eventManagementComponent.builder.EventBuilder;
import ticketSalesComponent.services.TicketService;

public class Main {
    public static void main(String[] args) {
        DatabaseInterface db = new PostgresDatabase();
        System.out.println("Connecting to Supabase...");

        try (Connection connection = db.getConnection()) {
            System.out.println("Connected successfully!");

            String sql = "SELECT CURRENT_TIMESTAMP";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Database time: " + rs.getTimestamp(1));
                }
            }
            // repositories
            EventRepository eventRepo = new PostgresEventRepository(db);
            SeatRepository seatRepo = new PostgresSeatRepository(db);
            TicketRepository ticketRepo = new PostgresTicketRepository(db);
            CustomersRepository customersRepo = new PostgresCustomersRepository(db);

            // services
            SeatAllocationService seatService = new SeatAllocationService(seatRepo, eventRepo);
            EventService eventService = new EventService(eventRepo, seatService);
            TicketService ticketService = new TicketService(ticketRepo, seatService);

            // create event: event and seats now in DB
            Event event1 = new EventBuilder("AI Conference", Event.Type.EDUCATION) // create Event using builder
                    .date(LocalDateTime.of(2026, 2, 10, 10, 0))
                    .venue("Main Hall")
                    .totalSeats(100)
                    .build();
            Event event2 = new Event ("Marvel Movie Night", Event.Type.CINEMA, LocalDateTime.now().plusDays(2));

            int eventId1 = eventService.createEvent(event1, 2, 2);
            int eventId2 = eventService.createEvent(event2, 3, 3);

            // create customer 1
            Customers customer1 = new Customers("Dan", "dan.123@gmail.com");
            int customerId1 = customersRepo.save(customer1); // save the customer in db
            System.out.println("customerId returned = " + customerId1);
            // create customer 2
            Customers customer2 = new Customers("Mary", "mary@gmail.com");
            int customerId2 = customersRepo.save(customer2);
            System.out.println("customerId returned = " + customerId2);

            // print the layout for event 1
            List<Seat> seats = seatService.viewSeatingLayout(eventId1);
            for  (Seat seat : seats) {
                System.out.println(
                        "ID: " + seat.getId() +
                        "| Row " + seat.getRow() +
                        "| Seat " + seat.getNumber() +
                        " → " + (seat.getBooked() ? "X" : "O")
                );
            }

            // customer buying ticket
            try {
                // first customer buying one VIP ticket
                String ticketCode1 = ticketService.buyTicket(customerId1, eventId1, 2, event1.getType(), Ticket.Type.VIP);
                System.out.println("Ticket bought: " + ticketCode1);
                // second customer buying 2 tickets, one standard and one student
                String ticketCode2 = ticketService.buyTicket(customerId2, eventId2, 5, event2.getType(), Ticket.Type.STANDARD);
                System.out.println("Ticket bought: " + ticketCode2);
                String ticketCode3 = ticketService.buyTicket(customerId2, eventId2, 6,  event2.getType(), Ticket.Type.STUDENT);
                System.out.println("Ticket bought: " + ticketCode3);

            } catch (SeatAlreadyBookedException seatEx) {
            System.out.println("Seat already booked. Please choose another seat.");
            } catch (Exception generalEx) {
                System.out.println("Something went wrong: " + generalEx.getMessage());
            }

            // cancel event 1
           try {
               Event eventCancelled = eventService.cancelEvent(eventId1);
               System.out.println("Event: '" + eventCancelled.getEventName() + "' has been " + eventCancelled.getStatus());
           } catch (Exception e) {
               System.out.println("Something went wrong! Couldn't cancel event!");
           }

            // print the layout for event 2
            List<Seat> seats2 = seatService.viewSeatingLayout(eventId2);
            for  (Seat seat : seats2) {
                System.out.println(
                        "ID: " + seat.getId() +
                                "| Row " + seat.getRow() +
                                "| Seat " + seat.getNumber() +
                                " → " + (seat.getBooked() ? "X" : "O")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }
    }
}