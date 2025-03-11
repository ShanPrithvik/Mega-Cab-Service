package utils;

import model.User;

public class UserValidationUtils {
	
    public static boolean isValidUser(User user) {
        return isValidName(user.getName()) 
               && isValidEmail(user.getEmail()) 
               && isValidPassword(user.getEmail()) 
               && isValidRole(user.getUserRole().name());
    }

    private static boolean isValidName(String name) {
        return name != null && name.matches("^[A-Za-z ]+$");
    }
    private static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    private static boolean isValidPassword(String password) {
        return password != null && password.length() > 8;
    }
    private static boolean isValidRole(String role) {
        return role != null && (role.equals("ADMIN") || role.equals("MANAGER") || role.equals("DRIVER"));
    }
    public static boolean isValidCrendentials(String email, String password) {
        return (!isValidEmail(email) || isValidPassword(password));
    }	
	
}
	
	
	
	 

