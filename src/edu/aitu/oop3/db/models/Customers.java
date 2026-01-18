package edu.aitu.oop3.db.models;

public class Customers {
    private int id;
    private String name;
    private String email;
    public Customers(int id, String name, String email){
        this.id=id;
        this.name=name;
        this.email=email;
    }
    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    @Override
    public String toString(){
        return "Customer (ID: "+id+", Name: "+name+", Email: "+email+")";
    }
}
