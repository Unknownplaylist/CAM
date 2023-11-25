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
    private int point;

    // Updated constructor
    public Student(String name, String email, String faculty, String role, String password, List<String> camps_withdrawn) {
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.role = role;
        this.password = password;
        this.camps_withdrawn = camps_withdrawn;
        this.point = 0;
    }

    
    /** 
     * @return String
     */
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

    
    public void addWithdrawnCamp(String campName) {
        if (!camps_withdrawn.contains(campName)) {
            camps_withdrawn.add(campName);
        }
    }
    

    public List<String> getCampsWithdrawn() {
        return new ArrayList<>(camps_withdrawn); // Return a copy to avoid external modifications
    }

    public boolean hasWithdrawnFromCamp(String campName) {
        return camps_withdrawn.stream()
                              .anyMatch(withdrawnCamp -> withdrawnCamp.equalsIgnoreCase(campName.trim()));
    }
    
    
    public String getUserId(String email){
        return (email.substring(0,email.length()-11));
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public void setCampsWithdrawn(List<String> campsWithdrawn) {
        
        this.camps_withdrawn = new ArrayList<>(campsWithdrawn);
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

    public void addOnePoint(){
        point ++;
    }

    public int getPoint(){
        return point;   
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
