package dao;

import java.util.List;

import model.User;

public interface UserDAO {  
    boolean addUser(User user);
    boolean findIfUserEmailExists(String email);
    User login(String username, String password);
    List<User> getUsers();

}
