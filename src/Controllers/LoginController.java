package Controllers;

public class LoginController {
    private StudentsController studentsController;
    private StaffController staffController; 

    public LoginController(StudentsController studentsController, StaffController staffController) {
        this.studentsController = studentsController;
        this.staffController = staffController;
    }

    public boolean loginUser(String id, String password) {
        // Verify user credentials (e.g., id and password)
        boolean validCredentials = verifyCredentials(id, password);

        if (validCredentials) {
            // Check if the user is a student or staff
            if (studentsController.verifyStudent(id + "@e.ntu.edu.sg") || staffController.verifyStaff(id + "@NTU.EDU.SG")) {
                // User is a student or staff
                return true;
            } else {
                // User is not a student or staff
                System.out.println("Access denied. You are not a recognized student or staff.");
                return false;
            }
        } else {
            // Invalid credentials
            System.out.println("Invalid credentials. Login failed.");
            return false;
        }
    }

    public String userType(String id){
        if(studentsController.verifyStudent(id+"@e.ntu.edu.sg"))
            return "Student";
        return "Staff";
    }

    private static final String DEFAULT_PASSWORD = "password";

    private boolean verifyCredentials(String id, String password) {
        // Verify that the ID is not null and password matches the default password
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid ID.");
            return false;
        }

        // Check if the provided password matches the default password
        return password.equals(DEFAULT_PASSWORD);
    }
}
