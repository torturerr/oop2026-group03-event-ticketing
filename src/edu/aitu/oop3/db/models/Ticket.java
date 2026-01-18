package edu.aitu.oop3.db.models;

public class Ticket {
    private int id;
    private static int idGen = 0;

    public Ticket() {
        id = idGen++;
    }
}
