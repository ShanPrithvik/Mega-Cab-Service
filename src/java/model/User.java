package model;

import model.enums.UserRoles;

public class User {
    
    private int id;
    private String name;
    private String email;
    private String password;
    private UserRoles userRole;
    
    public User () {}
	
    public User(String name, String email, String password, UserRoles userRole) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.userRole = userRole;

    }

    public User(int id, String name, String email, UserRoles userRole) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.userRole = userRole;

    }
    
    public int getId() {
            return id;
    }
    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
    public String getEmail() {
            return email;
    }   
    public void setEmail(String email) {
            this.email = email;
    }  
    public String getPassword() {
            return password;
    } 
    public void setPassword(String password) {
            this.password = password;
    }	
    public UserRoles getUserRole() {
            return userRole;
    }
    public void setUserRole(UserRoles userRole) {
            this.userRole = userRole;
    }
   	
}
