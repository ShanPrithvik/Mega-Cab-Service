package service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import config.DatabaseConnection;
import dao.BookingDAO;
import dao.CustomerDAO;
import dao.DriverDAO;
import dao.VehicleDAO;
import jakarta.servlet.http.HttpServletResponse;
import model.Booking;
import model.Customer;
import model.enums.DriverStatus;
import model.enums.VehicleStatus;
import utils.BookingValidationUtils;
import utils.JSONResponseUtils;

public class BookingServiceImpl implements BookingService {
    private  BookingDAO bookingDAO;
    private  CustomerDAO customerDAO;
    private  DriverDAO driverDAO;
    private  VehicleDAO vehicleDAO;


    public BookingServiceImpl(CustomerDAO customerDAO, BookingDAO bookingDAO, DriverDAO driverDAO, VehicleDAO vehicleDAO) {
        this.customerDAO = customerDAO;
        this.bookingDAO = bookingDAO;
        this.driverDAO = driverDAO;
        this.vehicleDAO = vehicleDAO;

    }
    public BookingServiceImpl(BookingDAO bookingDAO, DriverDAO driverDAO, VehicleDAO vehicleDAO){
        this.bookingDAO = bookingDAO;  
        this.driverDAO = driverDAO;
        this.vehicleDAO = vehicleDAO;
    }

        @Override
        public void createBooking(Customer customer, Booking booking, HttpServletResponse response) throws IOException {
            Connection conn = null;
            try {
                if (!BookingValidationUtils.isValidBooking(booking)) {
                    JSONResponseUtils.sendJsonResponse(response, "Invalid booking details", 400, null);
                    return;
                }

                conn = DatabaseConnection.getConnection();
                conn.setAutoCommit(false);

                int customerId = customerDAO.addCustomer(customer, conn);
                System.out.println("Generated Customer ID: " + customerId);
                if (customerId == -1) {
                    throw new SQLException("Failed to insert customer.");
                }

                int bookingId = bookingDAO.addBooking(booking, customerId, conn);
                System.out.println("Generated Booking ID: " + bookingId);
                if (bookingId == -1) {
                    throw new SQLException("Failed to insert booking.");
                }

                boolean updateDriverStatus = driverDAO.updateDriverStatus(bookingId, booking.getDriverId(), DriverStatus.BOOKED, conn);
                boolean updateVehicleStatus = vehicleDAO.updateVehicleStatus(booking.getVehicleId(), VehicleStatus.ASSIGNED, conn);

                if (!updateDriverStatus || !updateVehicleStatus) {
                    throw new SQLException("Failed to update driver or vehicle status.");
                }

                conn.commit(); 

                Booking bookingInfo = bookingDAO.getBookingInfo(bookingId);
                JSONResponseUtils.sendJsonResponse(response, "Booking created successfully", 201, bookingInfo);

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException rollbackEx) {
                    }
                }
                JSONResponseUtils.sendJsonResponse(response, "Booking creation failed", 500, null);
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);

                    } catch (SQLException e) {
                    }
                }
            }
        }


	@Override
	public List<Booking> getAllBookings(HttpServletResponse response) throws IOException{
            try {
            List<Booking> bookings = bookingDAO.getBookings();
            
            if(bookings  == null || bookings.isEmpty()) {
                JSONResponseUtils.sendJsonResponse(response, "No bookings found", 404, null);  
                return null;
            }
            JSONResponseUtils.sendJsonResponse(response, "Bookings retrieved successfully", 200, bookings);

            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);

            }       
            return null;
	}

    @Override
    public void bookingAccepted(int bookingId, HttpServletResponse response) throws IOException {
        try {
            boolean accepted = bookingDAO.bookingAccepted(bookingId);
            if(accepted){
                JSONResponseUtils.sendJsonResponse(response, "Booking Accepted", 404, null);  
                return;
            }
            JSONResponseUtils.sendJsonResponse(response, "Bookings retrieved successfully", 200, null);
        } catch (Exception e) {
            JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
        }
    }

    @Override
    public void cancelBooking(int bookingId, int driverId, int vehicleId, HttpServletResponse response) throws IOException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            boolean bookingStatusUpdated = bookingDAO.bookingCancelled(bookingId, conn);
            boolean driverStatusUpdated = driverDAO.cancelBooking(driverId, bookingId, conn);
            boolean vehicleStatusUpdated = vehicleDAO.updateVehicleStatusAvailable(vehicleId, conn);

            if (bookingStatusUpdated && driverStatusUpdated && vehicleStatusUpdated) {
                conn.commit();
                JSONResponseUtils.sendJsonResponse(response, "Booking Cancelled", 200, null); 
            } else {
                conn.rollback();
                JSONResponseUtils.sendJsonResponse(response, "An error occured on cancelling", 404, null); 
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            JSONResponseUtils.sendJsonResponse(response, "An error occured on cancelling", 404, null); 
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }


    @Override
    public void completeBooking(int bookingId, int driverId, int vehicleId, HttpServletResponse response) throws IOException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            boolean bookingStatusUpdated = bookingDAO.bookingCompleted(bookingId, conn);
            boolean driverStatusUpdated = driverDAO.completeBooking(driverId, bookingId, conn);
            boolean vehicleStatusUpdated = vehicleDAO.updateVehicleStatusAvailable(vehicleId, conn);

            if (bookingStatusUpdated && driverStatusUpdated && vehicleStatusUpdated) {
                conn.commit();
                JSONResponseUtils.sendJsonResponse(response, "Booking Completed", 404, null); 
            } else {
                conn.rollback();
                JSONResponseUtils.sendJsonResponse(response, "An error occured on completing", 404, null); 
            }
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
           JSONResponseUtils.sendJsonResponse(response, "An error occured on completing", 404, null); 
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    @Override
    public Booking getBookingInfo(int bookingId, HttpServletResponse response) throws IOException {
        try {
            Booking booking = bookingDAO.getBookingInfo(bookingId);
            
            if(booking  == null) {
                JSONResponseUtils.sendJsonResponse(response, "No booking found", 404, null);  
                return null;
            }
            JSONResponseUtils.sendJsonResponse(response, "Bookings retrieved successfully", 200, booking);

        } catch (IOException e) {              
            JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);

        }       
        return null;
    }
    
    
	
}
