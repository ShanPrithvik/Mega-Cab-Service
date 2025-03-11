package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import model.User;
import model.enums.UserRoles;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOImpl implements UserDAO{

    @Override
    public boolean addUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement driverStmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 

            String sql = "INSERT INTO users (Name, Email, Password, UserRole) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getUserRole().name());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);

                    if (user.getUserRole() == UserRoles.DRIVER) {
                        String driverSql = "INSERT INTO drivers (BookingId, UserId) VALUES (NULL, ?)";
                        driverStmt = conn.prepareStatement(driverSql);
                        driverStmt.setInt(1, userId);
                        driverStmt.executeUpdate();
                    }
                }
            }

            conn.commit(); 
            return true;

        } catch (Exception e) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    Logger.getLogger(UserDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            e.printStackTrace();
            System.out.println(e.getMessage());

        } 
        return false;
    }

    

    @Override
    public User login(String email, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT Id, Name, Email, UserRole FROM users WHERE Email = ? AND Password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        User user = new User(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("Email"),
                            UserRoles.valueOf(rs.getString("UserRole"))
                        );                      
                        return user;
                    }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();     
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT Id, Name, Email, UserRole FROM users";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    User user = new User(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                       UserRoles.valueOf(rs.getString("UserRole"))
                    );
                    users.add(user);
                }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
            
        return users;
    }

    @Override
    public boolean findIfUserEmailExists(String email) { 
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT Email FROM users WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

               stmt.setString(1, email);
               ResultSet rs = stmt.executeQuery();
               return rs.next(); 
    
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }    
        return false; 
    }

}
