package Models;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String email;
    private String faculty;
    private String role;
    private String password;
    private List<String> camps_withdrawn = new ArrayList<>();

    // Updated constructor
    public Student(String name, String email, String faculty, String role, String password) {
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.role = role;
        this.password = password;
    }

    // Getters and setters for all fields
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
    
    public String getFaculty() {
        return faculty;
    }

    public void addWithdrawnCamp(String camp_name){
        camps_withdrawn.add(camp_name);
    }

    public boolean hasWithdrawnFromCamp(String camp_name){
        for(String name : camps_withdrawn){
            if(name.equalsIgnoreCase(camp_name));
                return true;
        }
        return false;
    }
    
    public String getUserId(String email){
        return (email.substring(0,email.length()-11));
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method for printing
    @Override
    public String toString() {
        return "Student{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", faculty='" + faculty + '\'' +
               ", role='" + role + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
