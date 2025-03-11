package controller;

import dao.VehicleDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Vehicle;
import model.enums.VehicleColor;
import model.enums.VehicleStatus;
import model.enums.VehicleType;
import service.VehicleService;
import service.VehicleServiceImpl;
import utils.JSONResponseUtils;

import java.io.IOException;

@WebServlet("/vehicles/*")
public class VehicleController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final VehicleService vehicleService;

    public VehicleController() {
        this.vehicleService = new VehicleServiceImpl(new VehicleDAOImpl());
    }

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handlePostRequest(request, response);
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);

        switch (path) {
            case "/":
                vehicleService.getAllVehicles(response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);

        switch (path) {
            case "/create":
                handleVehicleCreation(request, response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handleVehicleCreation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("name");
            String model = request.getParameter("model");
            String numberPlate = request.getParameter("numberPlate");
            String type = request.getParameter("vehicleType");
            String color = request.getParameter("vehicleColor");
            String status = request.getParameter("vehicleStatus");
            
            VehicleType vehicleType = VehicleType.valueOf(type.toUpperCase());
            VehicleColor vehicleColor = VehicleColor.valueOf(color.toUpperCase());
            VehicleStatus vehicleStatus = VehicleStatus.valueOf(status.toUpperCase());
           
            Vehicle vehicle = new Vehicle(name, model, numberPlate, vehicleType, vehicleColor, vehicleStatus);
            System.out.println(vehicle.toString());
            vehicleService.createVehicle(vehicle, response);
        } catch (IllegalArgumentException e) {
            JSONResponseUtils.sendJsonResponse(response, "Invalid vehicle data", 400, null);
        }
    }

    private String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        return (path == null) ? "/" : path.toLowerCase();
    }
}
