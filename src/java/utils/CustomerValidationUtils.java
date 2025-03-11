package utils;

import model.Customer;

public class CustomerValidationUtils {

    public static boolean isValidCustomer(Customer customer) {
        return isValidName(customer.getName()) 
            && isValidPhoneNumber(customer.getPhoneNumber()) 
            && isValidNIC(customer.getNIC());
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }
    private static boolean isValidPhoneNumber(long phoneNumber) {
        String phoneStr = String.valueOf(phoneNumber); 
        return phoneStr != null && phoneStr.length() == 10 && phoneStr.matches("^\\d{10}$");
    }
    private static boolean isValidNIC(long nic) {
        String nicStr = String.valueOf(nic);
        return nicStr != null && nicStr.length() == 12;
    }
}
