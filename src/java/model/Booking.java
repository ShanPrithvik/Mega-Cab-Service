package model;

import model.enums.BookingStatus;
import model.enums.PaymentStatus;

public class Booking {
    
    private int id;
    private int customerId;
    private int managerId;
    private int vehicleId;
    private int driverId;
    private String pickUpLocation;
    private String dropOfLocation;
    private double rideFare;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;
    private Customer customer;
    private User driver;
    private Vehicle vehicle;
    
    public Booking () {}
    
    public Booking(int customerId, int managerId, int vehicleId, int driverId, String pickUpLocation,
	String dropOfLocation, double rideFare) {
            this.customerId = customerId;
            this.managerId = managerId;
            this.vehicleId = vehicleId;
            this.driverId = driverId;
            this.pickUpLocation = pickUpLocation;
            this.dropOfLocation = dropOfLocation;
            this.rideFare = rideFare;
    }
    
    public void setId(int id) {
        this.id = id;
    }
      	
    public int getId() {
        return id;
    }  
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getManagerId() {
        return managerId;
    }
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    public int getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    public int getDriverId() {
        return driverId;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public String getPickUpLocation() {
	return pickUpLocation;
    }
    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }
    public String getDropOfLocation() {
        return dropOfLocation;
    }
    public void setDropOfLocation(String dropOfLocation) {
        this.dropOfLocation = dropOfLocation;
    }
    public double getRideFare() {
        return rideFare;
    }
    public void setRideFare(double rideFare) {
        this.rideFare = rideFare;
    }
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public User getDriver() {
        return driver;
    }
    public void setDriver(User driver) {
        this.driver = driver;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
}