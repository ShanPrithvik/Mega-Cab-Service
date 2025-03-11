package service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;
import model.User;

public interface UserService {  
    void registerUser(User user, HttpServletResponse response) throws IOException;
    User userLogin(String email, String password, HttpServletResponse response) throws IOException;
    void getAllUsers(HttpServletResponse response) throws IOException; 
    
}
