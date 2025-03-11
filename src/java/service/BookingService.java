package service;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.Customer;

public interface BookingService {
    void createBooking(Customer customer, Booking booking,  HttpServletResponse response) throws IOException;
    List<Booking> getAllBookings(HttpServletResponse response) throws IOException;
    void bookingAccepted(int BookingId, HttpServletResponse response) throws IOException;
    void cancelBooking(int bookingId, int driverId, int vehicleId, HttpServletResponse response) throws IOException;
    void completeBooking(int bookingId, int driverId, int vehicleId, HttpServletResponse response) throws IOException;
    Booking getBookingInfo(int bookingId, HttpServletResponse response) throws IOException;
}
