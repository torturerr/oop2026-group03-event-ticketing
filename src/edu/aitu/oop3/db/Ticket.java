package edu.aitu.oop3.db;

public class Ticket {
    private int id;
    private static int idGen = 0;

    public Ticket() {
        id = idGen++;
    }
}
