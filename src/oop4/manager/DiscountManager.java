package oop4.manager;

import edu.aitu.oop3.db.models.Ticket;

public class DiscountManager {
    private static DiscountManager instance;
    // private constructor: so no creation from outside, only from the class
    private DiscountManager() {}

    // global method
    public static DiscountManager getInstance() {
        // first time it's null so we create the object for once
        if (instance == null) {
            instance = new DiscountManager();
        }
        // second time and after it returns the same one
        return instance;
    }

    public double applyDiscount(Ticket ticket) {
        double price = ticket.getPrice();

        switch (ticket.getType()) {
            case STUDENT:
                return price * 0.8;   // 20% off
            case VIP:
                return price * 0.9;   // 10% off
            case STANDARD:
            default:
                return price;
        }
    }
}
