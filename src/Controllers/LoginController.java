public class LoginController {
    private StudentsController studentsController;
    private StaffController staffController;

    public LoginController(StudentsController studentsController, StaffController staffController) {
        this.studentsController = studentsController;
        this.staffController = staffController;
    }

    public boolean loginUser(String email, String password) {
        // Verify user credentials
        boolean validCredentials = verifyCredentials(email, password);

        if (validCredentials) {
            // Check if the user is a student
            if (studentsController.verifyStudent(email)) {
                // User is a student
                return true;
            }
            // Check if the user is a staff member
            else if (staffController.verifyStaff(email)) {
                // User is a staff member
                return true;
            } else {
                // User is neither a student nor a staff member
                System.out.println("Access denied. You are not a student or staff member.");
                return false;
            }
        } else {
            // Invalid credentials
            System.out.println("Invalid credentials. Login failed.");
            return false;
        }
    }

    // Other methods...
}

public class Main {
    public static void main(String[] args) {
        // Initialize controllers
        StudentsController studentsController = new StudentsController();
        StaffController staffController = new StaffController();
        LoginController loginController = new LoginController(studentsController, staffController);

        // User credentials
        String email = "user@example.com";
        String password = "password";

        // Attempt to log in the user
        boolean loggedIn = loginController.loginUser(email, password);

        if (loggedIn) {
            // User is successfully logged in
            // Access student- or staff-specific functionalities here
        }
    }
}
