package controller;


import dao.BookingDAOImpl;
import dao.CustomerDAOImpl;
import dao.DriverDAOImpl;
import dao.VehicleDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import service.BookingService;
import service.BookingServiceImpl;
import service.DriverService;
import service.DriverServiceImpl;
import service.VehicleService;
import service.VehicleServiceImpl;
import utils.JSONResponseUtils;

@WebServlet("/driver/*")
public class DriverController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final BookingService bookingService;

    public DriverController() {
        this.vehicleService = new VehicleServiceImpl(new VehicleDAOImpl());
        this.driverService = new DriverServiceImpl(new DriverDAOImpl());
        this.bookingService = new BookingServiceImpl(new CustomerDAOImpl(), new BookingDAOImpl(), new DriverDAOImpl(), new VehicleDAOImpl()
    );
    }

    public DriverController(VehicleService vehicleService, DriverService driverService, BookingService bookingService) {
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.bookingService = bookingService;
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleGetRequest(request, response);
    }
    
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handlePutRequest(request, response);
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);

        switch (path) {
            case "/vehicles":
                vehicleService.getAvailableVehicles(response);
                break;
            case "/driverinfo":
                driverService.getAvailableDrivers(response);
                break;
            case "/assignedbookings":
                String driverIdParam = request.getParameter("driverid");
                int driverId = Integer.parseInt(driverIdParam);
                driverService.getAssignedBookings(driverId, response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }
    
    private void handlePutRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String path = getPath(request);

    switch (path) {
        case "/cancelbooking":
            handleCancelBooking(request, response);
            break;
        case "/completebooking":
            handleCompleteBooking(request, response);
            break;
        default:
            JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
    }
}
    
    
    private String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        return (path == null) ? "/" : path.toLowerCase();
    }
    
    private void handleCancelBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingid"));
            int driverId = Integer.parseInt(request.getParameter("driverid"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleid"));

            bookingService.cancelBooking(bookingId, driverId, vehicleId, response);
        } catch (NumberFormatException e) {
            JSONResponseUtils.sendJsonResponse(response, "Invalid parameters", 400, null);
        }
    }
    
    private void handleCompleteBooking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int bookingId = Integer.parseInt(request.getParameter("bookingid"));
            int driverId = Integer.parseInt(request.getParameter("driverid"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleid"));

            bookingService.completeBooking(bookingId, driverId, vehicleId, response);
        } catch (NumberFormatException e) {
            JSONResponseUtils.sendJsonResponse(response, "Invalid parameters", 400, null);
        }
    }
}
