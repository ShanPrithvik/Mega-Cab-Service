package dao;

import java.sql.Connection;
import java.util.List;


import model.Vehicle;
import model.enums.VehicleStatus;

public interface VehicleDAO {
    boolean addVehicle(Vehicle vehicle);
    boolean findIfModelExists(String model);
    boolean findIfNumberPlateExists(String numberPlate);
    boolean updateVehicleStatus(int vehicleId, VehicleStatus vehicleStatus, Connection conn);
    List<Vehicle> getVehicleInfo();
    List<Vehicle> getVehicles();
    boolean updateVehicleStatusAvailable(int vehicleId, Connection conn);
    
    
}
