
package service;

import dao.DriverDAO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import model.Booking;
import model.Driver;
import utils.JSONResponseUtils;

public class DriverServiceImpl implements DriverService {
    private final DriverDAO driverDAO;
    public DriverServiceImpl(DriverDAO driverDAO) {
        this.driverDAO = driverDAO;
    }
    
        @Override
        public void getAvailableDrivers(HttpServletResponse response) throws IOException {

            try {
                List<Driver> drivers = driverDAO.getDriverInfo();
                if (drivers == null || drivers.isEmpty()) {
                    JSONResponseUtils.sendJsonResponse(response, "No drivers found", 404, null);
                    return;
                }
                JSONResponseUtils.sendJsonResponse(response, "Drivers retrieved successfully", 200, drivers);

            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);

            }

        }

    @Override
    public void getAssignedBookings(int driverId, HttpServletResponse response) throws IOException {
        try {
                List<Booking> bookings = driverDAO.getAssignedBookings(driverId);
                if (bookings == null || bookings.isEmpty()) {
                    JSONResponseUtils.sendJsonResponse(response, "No bookinfs available", 404, null);
                    return;
                }
                JSONResponseUtils.sendJsonResponse(response, "Bookings retrieved successfully", 200, bookings);

            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);

            }
    }
    
}
