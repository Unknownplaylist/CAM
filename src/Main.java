import Controllers.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the StudentsController
        StudentsController studentsController = new StudentsController();

        // Initialize the LoginController with the StudentsController
        LoginController loginController = new LoginController(studentsController);

        // Sample user credentials (email and password)
        String email = "student@example.com";
        String password = "password";

        // Attempt to log in the user
        boolean loggedIn = loginController.loginUser(email, password);

        if (loggedIn) {
            // User is successfully logged in and is a student
            // Access student-specific functionalities here

            // Example: Get the student's name and faculty from the email
            String name = studentsController.getStudentName(email);
            String faculty = studentsController.getStudentFaculty(email);

            // Display the student's name and faculty
            System.out.println("Name: " + name);
            System.out.println("Faculty: " + faculty);
        }
    }
}
