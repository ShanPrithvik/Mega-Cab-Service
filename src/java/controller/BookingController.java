package controller;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import dao.DriverDAO;
import dao.DriverDAOImpl;
import dao.VehicleDAO;
import dao.VehicleDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.Customer;
import service.BookingService;
import service.BookingServiceImpl;
import utils.JSONResponseUtils;

import java.io.IOException;

@WebServlet("/bookings/*")
public class BookingController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final BookingService bookingService;

    public BookingController() {
        CustomerDAO customerDAO = new CustomerDAOImpl();
        BookingDAO bookingDAO = new BookingDAOImpl();
        DriverDAO driverDAO = new DriverDAOImpl();
        VehicleDAO vehicleDAO = new VehicleDAOImpl();

        this.bookingService = new BookingServiceImpl(customerDAO, bookingDAO, driverDAO, vehicleDAO);
    }

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handlePostRequest(request, response);
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       int bookingId = getBookingIdFromParams(request);
       String path = getPath(request);

        switch (path) {
            case "/accept":
                bookingService.bookingAccepted(bookingId,response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        int bookingId = getBookingIdFromParams(request);
        switch (path) {
            case "/":
                bookingService.getAllBookings(response);
                break;
            case "/bookinginfo":
                bookingService.getBookingInfo(bookingId, response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);

        switch (path) {
            case "/create":
                handleBookingCreation(request, response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handleBookingCreation(HttpServletRequest request, HttpServletResponse response) throws IOException {

            String customerName = request.getParameter("customerName");
            long  customerPhone = Long.parseLong(request.getParameter("customerPhone"));
            long  customerNic = Long.parseLong(request.getParameter("customerNIC"));
            Customer customer = new Customer(customerName, customerPhone, customerNic);     

            int managerId = Integer.parseInt(request.getParameter("managerId"));
            int vehicleId = Integer.parseInt(request.getParameter("vehicleId"));
            int driverId = Integer.parseInt(request.getParameter("driverId"));
            String pickUpLocation = request.getParameter("pickUpLocation");
            String dropOffLocation = request.getParameter("dropOfLocation");
            double rideFare = Double.parseDouble(request.getParameter("rideFare"));

            Booking booking = new Booking(0, managerId, vehicleId, driverId, pickUpLocation, dropOffLocation, rideFare);

            bookingService.createBooking(customer, booking, response);

    }
    
    private int getBookingIdFromParams(HttpServletRequest request) {
        String bookingIdParam = request.getParameter("bookingid");
        if (bookingIdParam == null || bookingIdParam.isEmpty()) {
            return -1;
        }
    return Integer.parseInt(bookingIdParam);
}

    private String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        return (path == null) ? "/" : path.toLowerCase();
    }
}
