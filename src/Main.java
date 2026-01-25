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
            Event e = new Event("Disney Movie Night", Event.Type.CINEMA, LocalDateTime.now().plusDays(2));
            int eventId = eventService.createEvent(e, 2, 5);

            // create customer
            Customers customer = new Customers("Danom", "danoom.123@gmail.com");
            int customerId = customersRepo.add(customer); // save the customer in db
            System.out.println("customerId returned = " + customerId);


            // customer buying ticket
            String ticketCode1 = ticketService.buyTicket(customerId, eventId, 4);
            String ticketCode2 = ticketService.buyTicket(customerId, eventId, 3);

            Ticket firstTick = ticketService.validateTicket(ticketCode1);
            System.out.println("first ticket returned = " + firstTick);

            // print the layout
            List<Seat> seats = seatService.viewSeatingLayout(eventId);

            for  (Seat seat : seats) {
                System.out.println(
                        "Row " + seat.getRow() +
                                ", Seat " + seat.getNumber() +
                                " → " + (seat.getBooked() ? "X" : "O")
                );
            }



        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }
    }
}