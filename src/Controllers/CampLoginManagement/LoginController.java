package Controllers.CampLoginManagement;

import Controllers.StaffController;
import Controllers.StudentsController;

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
    
            String studentEmail = id + "@e.ntu.edu.sg";
            String staffEmail = id + "@NTU.EDU.SG";
    
            if (studentsController.verifyStudent(studentEmail)) {
                return studentsController.getUserRole(studentEmail);
            } else if (staffController.verifyStaff(staffEmail)) {
                return "Staff";
            } else {
                return "Unknown";
            }
    }

    private boolean verifyCredentials(String id, String password) {
        // Verify that the ID is not null
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid ID.");
            return false;
        }
    
        // Normalize the email domain
        String studentEmail = (id + "@e.ntu.edu.sg");
        String staffEmail = (id + "@NTU.EDU.SG");
    
        // Check if the user is a student or staff and verify the password
        if (studentsController.verifyStudent(studentEmail)) {
            return studentsController.checkPassword(studentEmail, password);
        } else if (staffController.verifyStaff(staffEmail)) {
            return staffController.checkPassword(staffEmail, password);
        } else {
            System.out.println("User not found.");
            return false;
        }
    }
    

    
}
