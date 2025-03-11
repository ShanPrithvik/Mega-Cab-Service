package service;

import java.util.List;

import dao.VehicleDAO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Vehicle;
import utils.JSONResponseUtils;
import utils.VehicleValidationUtils;


public class VehicleServiceImpl implements VehicleService{    
    private final VehicleDAO vehicleDAO;

    public VehicleServiceImpl(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;     
    }

	@Override
	public void createVehicle(Vehicle vehicle,  HttpServletResponse response)throws IOException {    
            try {    
                if(!VehicleValidationUtils.isValidVehicle(vehicle)) {                 
                    JSONResponseUtils.sendJsonResponse(response, "Invalid vehicle data format", 400, null);
                    return;                  
                }else if(vehicleDAO.findIfModelExists(vehicle.getModel())) {                
                    JSONResponseUtils.sendJsonResponse(response, "Model already exists", 409, null);
                    return;
                    
                }else if(vehicleDAO.findIfNumberPlateExists(vehicle.getNumberPlate())) {
                    JSONResponseUtils.sendJsonResponse(response, "Number plate already", 409, null);
                    return;
                }else{
                    boolean isVehicleCreated = vehicleDAO.addVehicle(vehicle);
                    if(isVehicleCreated) {
                        JSONResponseUtils.sendJsonResponse(response, "Vehicle created successfully", 200, vehicle);                    
                    } else {
                        JSONResponseUtils.sendJsonResponse(response, "Vehicle creation failed", 400, vehicle);
                    }       
                }
                
            } catch (IOException e) {  
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
            }
	}

	@Override
	public List<Vehicle> getAllVehicles(HttpServletResponse response) throws IOException{  
            try {
                List<Vehicle> vehicles = vehicleDAO.getVehicles();
                if(vehicles == null || vehicles.isEmpty()) {
                    JSONResponseUtils.sendJsonResponse(response, "No vehicles found", 404, null);  
                    return null;
                }
                JSONResponseUtils.sendJsonResponse(response, "Vehicles retrieved successfully", 200, vehicles);

            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
                
            }
            return null;
        
	}

        @Override
        public List<Vehicle> getAvailableVehicles(HttpServletResponse response) throws IOException {
            try {
                    List<Vehicle> vehicles = vehicleDAO.getVehicleInfo();
                    if(vehicles == null || vehicles.isEmpty()) {
                        JSONResponseUtils.sendJsonResponse(response, "No vehicles found", 404, null);  
                    }
                    JSONResponseUtils.sendJsonResponse(response, "Vehicles retrieved successfully", 200, vehicles);

                } catch (IOException e) {              
                    JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);

                }
                return null;
        }


	

}
