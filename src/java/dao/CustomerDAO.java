package dao;

import java.sql.Connection;
import java.util.List;

import model.Customer;

public interface CustomerDAO {
    int addCustomer(Customer customer, Connection conn);
    List<Customer> getAllCustomers();
}
