import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.util.List;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.PostgresDatabase;
import edu.aitu.oop3.db.models.*;
import edu.aitu.oop3.db.services.*;
import edu.aitu.oop3.db.repositories.*;
import edu.aitu.oop3.db.exceptions.*;

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
            Event event1 = new Event("Singing Night", Event.Type.CONCERT, LocalDateTime.now().plusDays(1));
            Event event2 = new Event ("Marvel Movie Night", Event.Type.CINEMA, LocalDateTime.now().plusDays(2));

            int eventId1 = eventService.createEvent(event1, 2, 2);
            int eventId2 = eventService.createEvent(event2, 3, 3);

            // create customer 1
            Customers customer1 = new Customers("Dan", "dan.123@gmail.com");
            int customerId1 = customersRepo.add(customer1); // save the customer in db
            System.out.println("customerId returned = " + customerId1);
            // create customer 2
            Customers customer2 = new Customers("Mary", "mary@gmail.com");
            int customerId2 = customersRepo.add(customer2);
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
                // first customer buying one ticket
                String ticketCode1 = ticketService.buyTicket(customerId1, eventId1, 2);
                System.out.println("Ticket bought: " + ticketCode1);
                // second customer buying 2 tickets
                String ticketCode2 = ticketService.buyTicket(customerId2, eventId2, 5);
                System.out.println("Ticket bought: " + ticketCode2);
                String ticketCode3 = ticketService.buyTicket(customerId2, eventId2, 6);
                System.out.println("Ticket bought: " + ticketCode3);

            } catch (SeatAlreadyBookedException seatEx) {
            System.out.println("Seat already booked. Please choose another seat.");
            } catch (Exception generalEx) {
                System.out.println("Something went wrong: " + generalEx.getMessage());
            }

            // cancel event 1
           try {
               Event eventCancelledName = eventService.cancelEvent(eventId1);
               System.out.println("Event: '" + eventCancelledName + "' has been " + eventCancelledName.getStatus());
           } catch (EventCancelledException canceledEx) {
               System.out.println("Couldn't cancel event!");
           }

        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }
    }
}