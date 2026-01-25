package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.DatabaseInterface;
import edu.aitu.oop3.db.models.Customers;
import java.sql.*;


public class PostgresCustomersRepository implements CustomersRepository{
    private final DatabaseInterface db;
    public PostgresCustomersRepository(DatabaseInterface db){
        this.db=db;
    }

    @Override
    public int add(Customers customers) throws SQLException {
        // save the data in table and return id
        String sql="INSERT INTO customers (name, email) VALUES (?, ?) RETURNING id";
        try (Connection conn=db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, customers.getName());
            pstmt.setString(2, customers.getEmail());

            try (ResultSet rs = pstmt.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("id");
                    customers.setId(id);
                    return id;
                }
                else{
                    throw new RuntimeException("Failed to retrieve customer ID");
                }
            }
        }
    }
    @Override
    public Customers getCustomerByID(int id) throws SQLException{
        String sql="SELECT * FROM customers WHERE id = ?";
        try (Connection conn=db.getConnection();
             PreparedStatement pstmt=conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            try(ResultSet rs=pstmt.executeQuery()){
                if(rs.next()){
                    return new Customers(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        }
        return null;
    }
}
