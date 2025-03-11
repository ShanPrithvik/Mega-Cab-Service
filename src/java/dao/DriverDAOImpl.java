package dao;

import config.DatabaseConnection;
import model.Driver;
import model.enums.DriverStatus;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Booking;
import model.Customer;
import model.Vehicle;
import model.enums.BookingStatus;
import model.enums.PaymentStatus;



public class DriverDAOImpl implements DriverDAO {
    
    @Override
    public boolean updateDriverStatus(int bookingId, int driverId, DriverStatus driverStatus, Connection conn) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE drivers SET BookingId = ?, DriverStatus = ? WHERE UserId = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);
            stmt.setString(2, driverStatus.name());
            stmt.setInt(3, driverId);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } 
        return false;
    }

    @Override
    public List<Driver> getDriverInfo() {
        List<Driver> driverList = new ArrayList<>();
        String sql = "SELECT d.Id AS DriverTableId, d.UserId AS DriverID, u.Name AS DriverName, " +
                     "u.Email AS DriverEmail, d.DriverStatus " +
                     "FROM users u " +
                     "JOIN drivers d ON u.Id = d.UserId " +
                     "WHERE d.DriverStatus = 'AVAILABLE'";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("DriverTableId")); 
                driver.setDriverId(rs.getInt("DriverID"));
                driver.setName(rs.getString("DriverName"));
                driver.setEmail(rs.getString("DriverEmail"));
                driver.setDriverStatus(DriverStatus.valueOf(rs.getString("DriverStatus")));

                driverList.add(driver);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return driverList;
    }

    @Override
    public boolean completeBooking(int driverId, int bookingId, Connection conn) {
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE drivers SET DriverStatus = 'AVAILABLE' Where UserId = ? AND BookingId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            

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
    public boolean cancelBooking(int driverId, int bookingId, Connection conn) {
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE drivers SET DriverStatus = 'AVAILABLE' Where UserId = ? AND BookingId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, driverId);
            stmt.setInt(2, bookingId);
            

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
public List<Booking> getAssignedBookings(int driverId) {
   String sql = "SELECT b.Id AS BookingID, b.PickUpLocation, b.DropOfLocation, b.RideFare, " +
             "b.BookingStatus, b.PaymentStatus, " +
             "c.Name AS CustomerName, c.PhoneNumber AS CustomerPhone, " +
             "v.Id AS VehicleId, v.Name AS VehicleName, v.Model AS VehicleModel, v.NumberPlate AS VehicleNumberPlate " +
             "FROM bookings b " +
             "JOIN customers c ON b.CustomerId = c.Id " +
             "JOIN vehicles v ON b.VehicleId = v.Id " +
             "JOIN drivers d ON b.DriverId = d.Id " +  
             "WHERE d.UserId = ?";


    List<Booking> bookings = new ArrayList<>();

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, driverId);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("BookingID"));
                booking.setPickUpLocation(rs.getString("PickUpLocation"));
                booking.setDropOfLocation(rs.getString("DropOfLocation"));
                booking.setRideFare(rs.getDouble("RideFare"));
                booking.setBookingStatus(BookingStatus.valueOf(rs.getString("BookingStatus")));
                booking.setPaymentStatus(PaymentStatus.valueOf(rs.getString("PaymentStatus")));

                Customer customer = new Customer();
                customer.setName(rs.getString("CustomerName"));
                customer.setPhoneNumber((int) rs.getLong("CustomerPhone"));
                booking.setCustomer(customer);

                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("VehicleId"));
                vehicle.setName(rs.getString("VehicleName"));
                vehicle.setModel(rs.getString("VehicleModel"));
                vehicle.setNumberPlate(rs.getString("VehicleNumberPlate"));
                booking.setVehicle(vehicle);

                bookings.add(booking);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return bookings;
}

     
}
