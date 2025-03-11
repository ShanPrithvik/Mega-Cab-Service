package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.Vehicle;
import model.enums.VehicleColor;
import model.enums.VehicleStatus;
import model.enums.VehicleType;

public class VehicleDAOImpl implements VehicleDAO{

    @Override
    public boolean addVehicle(Vehicle vehicle) { 
        try {      
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO vehicles (Name, Model, NumberPlate, VehicleType, VehicleColor, VehicleStatus) VALUES (?, ?, ?, ?, ? ,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
                
            stmt.setString(1,vehicle.getName());
            stmt.setString(2, vehicle.getModel());
            stmt.setString(3, vehicle.getNumberPlate());
            stmt.setString(4, vehicle.getVehicleType().name());
            stmt.setString(5, vehicle.getVehicleColor().name()); 
            stmt.setString(6, vehicle.getVehicleStatus().name());
           
            stmt.executeUpdate();
            return true;
                
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }     
        return false;

    }

    @Override
    public List<Vehicle> getVehicles() {
        Connection conn = DatabaseConnection.getConnection();
        List<Vehicle> vehicles = new ArrayList<>();

        try {  
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicles");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Model"),
                    rs.getString("NumberPlate"),
                    VehicleType.valueOf(rs.getString("VehicleType")),
                    VehicleColor.valueOf(rs.getString("VehicleColor")),
                    VehicleStatus.valueOf(rs.getString("VehicleStatus"))

                );             
                vehicles.add(vehicle);
            }

        } catch (SQLException e) {         
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        }	
        return vehicles;
    }

    @Override
    public boolean findIfModelExists(String model) {     
        try {         
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT model FROM vehicles WHERE Model = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, model);
            ResultSet rs = stmt.executeQuery();
            return rs.next();          

        } catch (SQLException e) {          
            e.printStackTrace();
            System.out.println(e.getMessage());
            
        }     
        return false; 
    }

    @Override
    public boolean findIfNumberPlateExists(String numberPlate) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT NumberPlate FROM vehicles WHERE NumberPlate = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); 
            
            stmt.setString(1, numberPlate);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }   
        return false; 
    }

    @Override
    public boolean updateVehicleStatus(int vehicleId, VehicleStatus vehicleStatus, Connection conn) {
        try {
            String sql = "UPDATE vehicles SET VehicleStatus = ? WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, vehicleStatus.name());
            stmt.setInt(2, vehicleId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }     
        return false;
    }

    @Override
    public List<Vehicle> getVehicleInfo() {
        List<Vehicle> vehicleList = new ArrayList<>();
        String sql = "SELECT Id AS VehicleID, Name AS VehicleName, Model AS VehicleModel, NumberPlate, VehicleType, VehicleColor, VehicleStatus FROM vehicles WHERE VehicleStatus = 'AVAILABLE'";

        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setId(rs.getInt("VehicleID"));
                vehicle.setName(rs.getString("VehicleName"));
                vehicle.setModel(rs.getString("VehicleModel"));
                vehicle.setNumberPlate(rs.getString("NumberPlate"));
                vehicle.setVehicleType(VehicleType.valueOf(rs.getString("VehicleType")));
                vehicle.setVehicleColor(VehicleColor.valueOf(rs.getString("VehicleColor")));
                vehicle.setVehicleStatus(VehicleStatus.valueOf(rs.getString("VehicleStatus")));
                vehicleList.add(vehicle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return vehicleList;
    }

    @Override
    public boolean updateVehicleStatusAvailable(int vehicleId, Connection conn) {
        try {
            String sql = "UPDATE vehicles SET VehicleStatus = 'AVAILABLE' WHERE Id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, vehicleId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }     
        return false;
    }


	

}
