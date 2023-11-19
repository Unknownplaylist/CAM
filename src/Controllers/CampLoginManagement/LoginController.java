package Controllers.CampLoginManagement;

import Controllers.StudentsController;
import Controllers.CampStaffManagement.StaffController;

public class LoginController {
    StudentsController studentsController;
    StaffController staffController; 
    public LoginService loginService;

    public LoginController(StudentsController studentsController, StaffController staffController) {
        this.studentsController = studentsController;
        this.staffController = staffController;
        this.loginService = new LoginService();
    }

    public boolean loginUser(String id, String password) {
        // Verify user credentials (e.g., id and password)
        boolean validCredentials = loginService.verifyCredentials(this, id, password);

        if (validCredentials) {
            // Check if the user is a student or staff
            if (studentsController.verifyStudent(id + "@e.ntu.edu.sg") || staffController.staffService.verifyStaff(staffController, id + "@NTU.EDU.SG")) {
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
    

    
}
