package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import java.sql.Statement;
import java.sql.SQLException;
import model.Customer;

public class CustomerDAOImpl implements CustomerDAO{

public int addCustomer(Customer customer, Connection conn) {
    int generatedId = -1;
        try {
            String sql = "INSERT INTO customers (Name, PhoneNumber, NIC) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, customer.getName());
            stmt.setLong(2, customer.getPhoneNumber());
            stmt.setLong(3, customer.getNIC());

            int affectedRows = stmt.executeUpdate();
            System.out.println("Affected Rows: " + affectedRows);

            if (affectedRows == 0) {
                return -1;
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            } else {
                return -1;
            }

        } catch (SQLException e) {          
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return generatedId;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }
	
}
