package Controllers;

public class LoginController {
    private StudentsController studentsController;

    public LoginController(StudentsController studentsController) {
        this.studentsController = studentsController;
    }

    public boolean loginUser(String email, String password) {
        // Verify user credentials (e.g., email and password)
        boolean validCredentials = verifyCredentials(email, password);

        if (validCredentials) {
            // Check if the user is a student
            if (studentsController.verifyStudent(email)) {
                // User is a student
                return true;
            } else {
                // User is not a student
                System.out.println("Access denied. You are not a student.");
                return false;
            }
        } else {
            // Invalid credentials
            System.out.println("Invalid credentials. Login failed.");
            return false;
        }
    }

    private static final String DEFAULT_PASSWORD = "password";

    private boolean verifyCredentials(String email, String password) {
        // Verify that the email is in the correct format and not null
        if (email == null || !email.contains("@") || !email.endsWith("e.ntu.edu.sg")) {
            System.out.println("Invalid email format.");
            return false;
        }

        // Check if the provided password matches the default password
        return password.equals(DEFAULT_PASSWORD);
    }

}
