package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection { 
    private static final String URL = "jdbc:mysql://localhost:3306/megacitycab"; //Please Change Your Localhost port and the db name which your using in mysql
    private static final String USER = "root"; // Enter the user name of mysql connection
    private static final String PASSWORD = "jbm@plL1x"; // Enter the user password of mysql connection

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

// Befor Running the Program Please add the below Jar's
// 1. jackson-annotation-2.18.2.jar
// 2. jackson-core-2.18.2.jar
// 3.jackson-databoned-2.18.2.jar
// 4. mysql-connection-j-9.2.0.jar

//Also Install the Appache Tomcat Server: I'm Using Version 10.1 with is more fast