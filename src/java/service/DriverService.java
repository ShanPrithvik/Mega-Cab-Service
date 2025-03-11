
package service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DriverService {
    void getAvailableDrivers(HttpServletResponse response) throws IOException;
    void getAssignedBookings(int driverId, HttpServletResponse response) throws IOException;
    
}
