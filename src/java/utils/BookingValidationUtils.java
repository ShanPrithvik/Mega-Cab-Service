package utils;

import model.Booking;

public class BookingValidationUtils {

    public static boolean isValidBooking(Booking booking) {
        return isValidLocation(booking.getPickUpLocation()) 
            && isValidLocation(booking.getDropOfLocation()) 
            && areDifferentLocations(booking.getPickUpLocation(), booking.getDropOfLocation()) 
            && isValidFare(booking.getRideFare());
    }

    private static boolean isValidLocation(String location) {
        return location != null && location.matches("^[A-Za-z ]{2,50}$");
    }
    private static boolean areDifferentLocations(String pickup, String dropoff) {
        return pickup != null && dropoff != null && !pickup.equalsIgnoreCase(dropoff);
    }
    private static boolean isValidFare(double fare) {
        return fare > 0;
    }
  
}
