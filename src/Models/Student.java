package Models;

class Student {
    private String name;
    private String email;
    private String faculty;
    
    public Student(String name, String email, String faculty) {
        this.name = name;
        this.email = email;
        this.faculty = faculty;
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
    
    public String getFaculty() {
        return faculty;
    }
    
    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
