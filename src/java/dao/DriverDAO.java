
package dao;

import java.sql.Connection;
import java.util.List;
import model.Booking;
import model.Driver;
import model.enums.DriverStatus;

public interface DriverDAO {
    boolean updateDriverStatus(int bookingId, int driverId, DriverStatus driverStatus, Connection conn);
    List<Driver> getDriverInfo();
    boolean completeBooking(int driverId, int bookingId, Connection conn);
    boolean cancelBooking(int driverId, int bookingId, Connection conn);
    List<Booking> getAssignedBookings(int driverId);
}
