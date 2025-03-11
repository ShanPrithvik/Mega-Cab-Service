package model;

import model.enums.VehicleColor;
import model.enums.VehicleStatus;
import model.enums.VehicleType;

public class Vehicle {
    
    private int id;
    private String name;
    private String model;
    private String numberPlate;
    private VehicleType vehicleType;
    private VehicleColor vehicleColor;
    private VehicleStatus vehicleStatus;
    
    public Vehicle () {}

    public Vehicle(int id, String name, String model, String numberPlate, VehicleType vehicleType, VehicleColor vehicleColor, VehicleStatus vehicleStatus) {   
        this.id = id;
        this.name = name;
        this.model = model;
        this.numberPlate = numberPlate;
        this.vehicleColor = vehicleColor;
        this.vehicleStatus = vehicleStatus;
        this.vehicleType = vehicleType;
            
    }
    
    public Vehicle(String name, String model, String numberPlate, VehicleType vehicleType, VehicleColor vehicleColor, VehicleStatus vehicleStatus) {
        this.name = name;
        this.model = model;
        this.numberPlate = numberPlate;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.vehicleStatus = vehicleStatus;
            
    }
    
    public void setId(int id) {
        this.id = id;
    }  
    public int getId() {
            return id;
    }    
    public String getName() {
            return name;
    }   
    public void setName(String name) {
            this.name = name;
    }   
    public String getModel() {
            return model;
    }    
    public void setModel(String model) {
            this.model = model;
    }   
    public String getNumberPlate() {
            return numberPlate;
    }   
    public void setNumberPlate(String numberPlate) {
            this.numberPlate = numberPlate;
    }    
    public VehicleType getVehicleType() {
            return vehicleType;
    }   
    public void setVehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
    }   
    public VehicleColor getVehicleColor() {
            return vehicleColor;
    }   
    public void setVehicleColor(VehicleColor vehicleColor) {
            this.vehicleColor = vehicleColor;
    }   
    public VehicleStatus getVehicleStatus() {
            return vehicleStatus;
    } 
    public void setVehicleStatus(VehicleStatus vehicleStatus) {
            this.vehicleStatus = vehicleStatus;
    }
}
