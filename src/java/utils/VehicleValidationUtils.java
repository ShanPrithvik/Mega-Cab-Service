package utils;

import model.Vehicle;

public class VehicleValidationUtils {
    
    public static boolean isValidVehicle(Vehicle vehicle) {
        return isValidName(vehicle.getName()) 
            && isValidModel(vehicle.getModel()) 
            && isValidNumberPlate(vehicle.getNumberPlate()) 
            && isValidVehicleType(vehicle.getVehicleType().name()) 
            && isValidColor(vehicle.getVehicleColor().name()) 
            && isValidStatus(vehicle.getVehicleStatus().name());
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }
    private static boolean isValidModel(String model) {
        return model != null &&  model.length() == 5 && model.matches("^[a-zA-Z0-9-]+$");
    }
    private static boolean isValidNumberPlate(String numberPlate) {
        return numberPlate != null &&  numberPlate.length() == 7 && numberPlate.matches("^[a-zA-Z0-9-]+$");
    }
    private static boolean isValidVehicleType(String vehicleType) {
        return vehicleType != null && (vehicleType.equals("CAR") || vehicleType.equals("BIKE") || vehicleType.equals("VAN") || vehicleType.equals("TUK"));
    }
    private static boolean isValidColor(String vehicleColor) {
        return vehicleColor != null && (vehicleColor.equals("BLACK") || vehicleColor.equals("WHITE") || vehicleColor.equals("RED") || vehicleColor.equals("BLUE"));
    }
    private static boolean isValidStatus(String vehicleStatus) {
        return vehicleStatus != null && (vehicleStatus.equals("AVAILABLE") || vehicleStatus.equals("ASSIGNED") || vehicleStatus.equals("MAINTAINENCE"));
    }

}
