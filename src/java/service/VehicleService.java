package service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import model.Vehicle;


public interface VehicleService {
    void createVehicle(Vehicle vehicle, HttpServletResponse response) throws IOException;
    List<Vehicle> getAllVehicles(HttpServletResponse response)throws IOException;
    List<Vehicle> getAvailableVehicles(HttpServletResponse response)throws IOException;

}
