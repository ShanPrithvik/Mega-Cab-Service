package controller;

import dao.DriverDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.enums.UserRoles;
import service.UserService;
import service.UserServiceImpl;
import utils.JSONResponseUtils;
import dao.UserDAOImpl;
import java.io.IOException;

@WebServlet("/users/*")
public class UserController extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private final UserService userService;
    
    public UserController() {
        this.userService = new UserServiceImpl(new UserDAOImpl()); 
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleGetRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handlePostRequest(request, response);
    }

    private void handleGetRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        
        switch (path) {
            case "/":
                userService.getAllUsers(response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handlePostRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = getPath(request);
        
        switch (path) {
            case "/register":
                handleUserRegistration(request, response);
                break;
            case "/login":
                handleUserLogin(request, response);
                break;
            default:
                JSONResponseUtils.sendJsonResponse(response, "Invalid request", 400, null);
        }
    }

    private void handleUserRegistration(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("userRole");
            UserRoles userRole = UserRoles.valueOf(role.toUpperCase());

            User user = new User(name, email, password, userRole);
            userService.registerUser(user, response);
        } catch (IllegalArgumentException e) {
            JSONResponseUtils.sendJsonResponse(response, "Invalid user data", 400, null);
        }
    }

    private void handleUserLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        userService.userLogin(email, password, response);
    }
    
    private String getPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        return (path == null) ? "/" : path.toLowerCase();
    }
}
