package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection { 
    private static final String URL = "jdbc:mysql://localhost:3306/megacitycab";
    private static final String USER = "root"; 
    private static final String PASSWORD = "jbm@plL1x"; 

    public static Connection getConnection() {   
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;          
        } catch (ClassNotFoundException | SQLException e) {               
            e.printStackTrace();         
        }      
        return null;
    }
}
