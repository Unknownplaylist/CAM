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

    private boolean verifyCredentials(String email, String password) {
        // Implement the logic to verify user credentials (e.g., compare email and password)
        // Return true if credentials are valid; otherwise, return false
        // You can add your own authentication logic here
        return true; // For demonstration purposes
    }

    // Other methods related to user authentication can be added here
}
