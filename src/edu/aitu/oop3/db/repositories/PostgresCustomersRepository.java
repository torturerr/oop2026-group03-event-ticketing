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
    public void add(Customers customers) throws SQLException {
        String sql="INSERT INTO customers (name, email) VALUES (?, ?)";
        try (Connection conn=db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, customers.getName());
            pstmt.setString(2, customers.getEmail());
            pstmt.executeUpdate();
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
