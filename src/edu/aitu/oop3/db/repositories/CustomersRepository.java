package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.models.Customers;
import java.sql.SQLException;

public interface CustomersRepository {
    int add(Customers customers) throws SQLException;
    Customers getCustomerByID(int id) throws SQLException;
}
