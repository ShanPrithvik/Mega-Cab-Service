package service;

import java.io.IOException;
import java.util.List;

import dao.UserDAO;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import utils.JSONResponseUtils;
import utils.UserValidationUtils;

public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
        
	@Override
	public void registerUser(User user, HttpServletResponse response) throws IOException{            
            try {
                if (!UserValidationUtils.isValidUser(user)) {     
                    JSONResponseUtils.sendJsonResponse(response, "Invalid user data format", 400, null);
                    return;                        
                } else if (userDAO.findIfUserEmailExists(user.getEmail())) {                      
                    JSONResponseUtils.sendJsonResponse(response, "Email already exists", 409, null);
                    return;                       
                } else {                      
                    boolean userCreated = userDAO.addUser(user);
                    if(userCreated){   
                        JSONResponseUtils.sendJsonResponse(response, "User created successfully", 201, user);
                        return;                           
                    } else {
                        JSONResponseUtils.sendJsonResponse(response, "User creation failed", 400, null);
                        return;                            
                    }  
                }
                                
            } catch (IOException e) {               
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
            }
        
        }

	@Override
	public User userLogin(String email, String password, HttpServletResponse response) throws IOException {   
            try {
                if (!UserValidationUtils.isValidCrendentials(email, password)) {
                    JSONResponseUtils.sendJsonResponse(response, "Invalid email or password", 400, null);
                    return null;
                }
                User user = userDAO.login(email, password);
                if (user != null) {                  
                    JSONResponseUtils.sendJsonResponse(response, "Login successful", 200, user);
                    return user;                
                } else {                  
                    JSONResponseUtils.sendJsonResponse(response, "Invalid credentials", 401, null);
                    return null;
                }
                
            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
                return null;
                
            }
            
        }

	@Override
	public void getAllUsers(HttpServletResponse response) throws IOException {
            try {
                List<User> users = userDAO.getUsers();
                if (users == null || users.isEmpty()) {
                    JSONResponseUtils.sendJsonResponse(response, "No users found", 404, null);
                    return;
                }
                JSONResponseUtils.sendJsonResponse(response, "Users retrieved successfully", 200, users);

            } catch (IOException e) {              
                JSONResponseUtils.sendJsonResponse(response, "An error occurred while processing your request", 500, null);
                
            }
        
        }

}
