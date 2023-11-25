package Models;

public class Staff {
    private String name;
    private String email;
    private String faculty;
    private String password;

    // Constructor
    public Staff(String name, String email, String faculty, String password) {
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.password = password;
    }

    
    /** 
     * @return String
     */
    // Getters and setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public String getUserId(String email){
        return (email.substring(0,email.length()-11));
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFaculty() {
        return faculty;
    }
    
    public void setFaculty(String faculty) {
        this.faculty = faculty;
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
        return "Staff{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", faculty='" + faculty + '\'' +
               ", password='" + password + '\'' +
               '}';
    }
}
