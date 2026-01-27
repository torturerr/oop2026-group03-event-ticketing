package edu.aitu.oop3.db.models;

public class Customers {
    // Basic data fields
    private int id;
    private String name;
    private String email;
    // Use this to create a new customer (ID is not created yet)
    public Customers(String name, String email) {
        this.name = name;
        this.email = email;
    }
    // Use this when loading a customer from the Database (ID already exists)
    public Customers(int id, String name, String email){
        this.id=id;
        this.name=name;
        this.email=email;
    }
    // Getters to read private data
    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    // Setter to update ID after saving to DB
    public void setId(int id){this.id=id;}
    // Display customer info as text
    @Override
    public String toString(){
        return "Customer (ID: "+id+", Name: "+name+", Email: "+email+")";
    }
}
