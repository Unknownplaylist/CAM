import Controllers.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the StudentsController and StaffController
        StudentsController studentsController = new StudentsController();
        StaffController staffController = new StaffController(); // Assuming StaffController is implemented

        // Initialize the LoginController with both StudentsController and StaffController
        LoginController loginController = new LoginController(studentsController, staffController);

        // Sample user ID and password (the ID is the part before "@")
        String userId = "HUKUMAR"; // Assuming "KOH1" is the ID part of the email
        String password = "password";

        // Attempt to log in the user
        boolean loggedIn = loginController.loginUser(userId, password);

        if (loggedIn) {
            // User is successfully logged in and is recognized as a student or staff
            // Access student-specific or staff-specific functionalities here

            // Example: Get the student's or staff's name and faculty using the complete email
            String studentEmail = userId + "@e.ntu.edu.sg";
            String staffEmail = userId + "@NTU.EDU.SG";
            
            String name;
            String faculty;

            // Determine if the user is a student or staff and get the name and faculty
            if (studentsController.verifyStudent(studentEmail)) {
                name = studentsController.getStudentName(studentEmail);
                faculty = studentsController.getStudentFaculty(studentEmail);
            } else {
                name = staffController.getStaffName(staffEmail);
                faculty = staffController.getStaffFaculty(staffEmail);
            }

            // Display the name and faculty
            System.out.println("Name: " + name);
            System.out.println("Faculty: " + faculty);
        }
    }
}

/**
import Controllers.*;
import Models.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the StudentsController
        StudentsController studentsController = new StudentsController();

        // Create a new student object
        String name = "John";
        String email = "john@e.ntu.edu.sg";
        String faculty = "EEE";
        
        Student newStudent = new Student(name, email, faculty);

        // Write the new student to the database
        studentsController.writeStudent(newStudent);

        System.out.println("New student added: " + newStudent.getName());
    }
}
*/