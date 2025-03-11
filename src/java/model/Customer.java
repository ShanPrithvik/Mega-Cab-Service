package model;

public class Customer {
    private int id;
    private String name;
    private long phoneNumber;
    private long NIC;
    
    public Customer (String name, long phoneNumber, long NIC) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.NIC = NIC;
    }
    
    public Customer() {}
    
    public int getId() {
       return id;
    }
    public String getName() {
       return name;
    }
    public void setName(String name) {
       this.name = name;
    }
    public long getPhoneNumber() {
       return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
       this.phoneNumber = phoneNumber;
    }
    public long getNIC() {
       return NIC;
    }
    public void setNIC(long nIC) {
       NIC = nIC;
    }
	
}
