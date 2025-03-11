package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import java.sql.Statement;
import model.Booking;
import model.Customer;
import model.Driver;
import model.Vehicle;
import model.enums.BookingStatus;
import model.enums.PaymentStatus;
import model.enums.VehicleStatus;

public class BookingDAOImpl implements BookingDAO {
    
    @Override
    public int addBooking(Booking booking, int customerId, Connection conn) {
        int generatedId = -1;
        try {
            String sql = "INSERT INTO bookings (CustomerId, ManagerId, VehicleId, DriverId, PickUpLocation, DropOfLocation, RideFare, BookingStatus, PaymentStatus)"
                       + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, customerId);
            stmt.setInt(2, booking.getManagerId());
            stmt.setInt(3, booking.getVehicleId());
            stmt.setInt(4, booking.getDriverId());
            stmt.setString(5, booking.getPickUpLocation());
            stmt.setString(6, booking.getDropOfLocation());
            stmt.setDouble(7, booking.getRideFare());
            stmt.setString(8, booking.getBookingStatus().PENDING.name()); 
            stmt.setString(9, booking.getPaymentStatus().PENDING.name()); 

            int affectedRows = stmt.executeUpdate();
             System.out.println("Executing SQL: " + stmt.toString());

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
    public List<Booking> getBookings() {
        String sql = "SELECT " +
             "b.Id AS BookingID, " +
             "b.DriverId AS DriverId, " +
             "c.Name AS CustomerName, " +
             "c.PhoneNumber AS CustomerPhone, " +
             "c.NIC AS CustomerNIC, " +
             "b.PickUpLocation, " +
             "b.DropOfLocation, " +
             "b.RideFare, " +
             "b.PaymentStatus, " +
             "b.BookingStatus, " +
             "u.Name AS DriverName, " +
             "u.Email AS DriverEmail, " +
             "v.Id AS VehicleId, " +
             "v.Name AS VehicleName, " +
             "v.Model AS VehicleModel, " +
             "v.NumberPlate AS VehicleNumberPlate, " +
             "v.VehicleStatus " +
             "FROM bookings b " +
             "JOIN customers c ON b.CustomerId = c.Id " +
             "JOIN drivers d ON b.DriverId = d.Id " +
             "JOIN users u ON d.UserId = u.Id " +
             "JOIN vehicles v ON b.VehicleId = v.Id;";


        List<Booking> bookings = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    Booking booking = new Booking();
                    booking.setId(rs.getInt("BookingID"));
                    booking.setDriverId(rs.getInt("DriverId"));
                    booking.setPickUpLocation(rs.getString("PickUpLocation"));
                    booking.setDropOfLocation(rs.getString("DropOfLocation"));
                    booking.setRideFare(rs.getDouble("RideFare"));
                    booking.setPaymentStatus(PaymentStatus.valueOf(rs.getString("PaymentStatus")));
                    booking.setBookingStatus(BookingStatus.valueOf(rs.getString("BookingStatus")));

                    Customer customer = new Customer(rs.getString("CustomerName"), rs.getLong("CustomerPhone"), rs.getLong("CustomerNIC"));
                    booking.setCustomer(customer);

                    Driver driver = new Driver();
                    driver.setName(rs.getString("DriverName"));
                    driver.setEmail(rs.getString("DriverEmail"));
                    booking.setDriver(driver);  

                    Vehicle vehicle = new Vehicle();
                    vehicle.setId(rs.getInt("VehicleId"));
                    vehicle.setName(rs.getString("VehicleName"));
                    vehicle.setModel(rs.getString("VehicleModel"));
                    vehicle.setNumberPlate(rs.getString("VehicleNumberPlate"));
                    vehicle.setVehicleStatus(VehicleStatus.valueOf(rs.getString("VehicleStatus")));
                    booking.setVehicle(vehicle);

                    bookings.add(booking);

                }
            
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            return bookings;
        }

    @Override
    public Booking getBookingInfo(int bookingId) {
       String sql = "SELECT b.Id AS BookingID, b.PickUpLocation, b.DropOfLocation, b.RideFare, " +
             "b.PaymentStatus, b.BookingStatus, u.Name AS DriverName, u.Email AS DriverEmail, " +
             "v.Name AS VehicleName, v.Model AS VehicleModel, v.NumberPlate AS VehicleNumberPlate " +
             "FROM bookings b " +
             "JOIN drivers d ON b.DriverId = d.Id " +  
             "JOIN users u ON d.UserId = u.Id " +
             "JOIN vehicles v ON b.VehicleId = v.Id " +
             "WHERE b.Id = ?";

        Booking booking = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
             
                    booking = new Booking();
                    booking.setId(rs.getInt("BookingID"));                
                    booking.setPickUpLocation(rs.getString("PickUpLocation"));
                    booking.setDropOfLocation(rs.getString("DropOfLocation"));
                    booking.setRideFare(rs.getDouble("RideFare"));
                    booking.setPaymentStatus(PaymentStatus.valueOf(rs.getString("PaymentStatus")));
                    booking.setBookingStatus(BookingStatus.valueOf(rs.getString("BookingStatus")));

                    Driver driver = new Driver();
                    driver.setName(rs.getString("DriverName"));
                    driver.setEmail(rs.getString("DriverEmail"));
                    booking.setDriver(driver); 

                    Vehicle vehicle = new Vehicle();
                    vehicle.setName(rs.getString("VehicleName"));
                    vehicle.setModel(rs.getString("VehicleModel"));
                    vehicle.setNumberPlate(rs.getString("VehicleNumberPlate"));
                    booking.setVehicle(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            System.out.println(e.getMessage());
        }
        return booking;
    }

    @Override
    public boolean bookingAccepted(int bookingId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "UPDATE bookings SET BookingStatus = 'STARTED' WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);

            int rowsUpdated = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return rowsUpdated > 0; 
        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean bookingCompleted(int bookingId, Connection conn) {
       try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE bookings SET BookingStatus = 'COMPLETED', PaymentStatus = 'PAID' WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);

            int rowsUpdated = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return rowsUpdated > 0; 
        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean bookingCancelled(int bookingId, Connection conn) {
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE bookings SET BookingStatus = 'CANCELLED', PaymentStatus = 'CANCELLED' WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);

            int rowsUpdated = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return rowsUpdated > 0; 
        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println(e.getMessage());
            return false;
        }
    }

}
