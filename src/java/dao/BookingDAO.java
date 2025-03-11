package dao;



import java.sql.Connection;
import java.util.List;

import model.Booking;

public interface BookingDAO {
    int addBooking(Booking booking, int customerId, Connection conn);
    List<Booking> getBookings();
    Booking getBookingInfo(int bookingId);
    boolean bookingAccepted(int bookingId);
    boolean bookingCompleted(int bookingId, Connection conn);
    boolean bookingCancelled(int bookingId, Connection conn);
    
}
