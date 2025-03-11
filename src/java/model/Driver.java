
package model;

import model.enums.DriverStatus;

public class Driver extends User{
    private int id;
    private int bookingId;
    private int driverId;
    private DriverStatus driverStatus;

    public Driver(int bookingId, int driverId, DriverStatus driverStatus) {
       this.bookingId = bookingId;
       this.driverId = driverId;
       this.driverStatus = driverStatus;
   }
    
    public Driver() {
        super();      
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public int getBookingId() {
        return bookingId;
    }
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    public int getDriverId() {
        return driverId;
    }
    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }
    public DriverStatus getDriverStatus() {
        return driverStatus;
    }
    public void setDriverStatus(DriverStatus driverStatus) {
        this.driverStatus = driverStatus;
    }

   
    
}
