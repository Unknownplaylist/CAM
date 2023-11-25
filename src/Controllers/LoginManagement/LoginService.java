package Controllers.LoginManagement;

import Controllers.LoginManagementInterface.LoginServiceInterface;

public class LoginService implements LoginServiceInterface {

    /**
     * Determines the user type based on the provided ID.
     * 
     * @param loginController The LoginController object.
     * @param id The ID of the user.
     * @return The user type as a String ("Student", "Staff", or "Unknown").
     */
    @Override
    public String userType(LoginController loginController, String id){
    
            String studentEmail = id + "@e.ntu.edu.sg";
            String staffEmail = id + "@NTU.EDU.SG";
    
            if (loginController.studentsController.studentAuthenticationService.verifyStudent(loginController.studentsController, studentEmail)) {
                return loginController.studentsController.studentAuthenticationService.getUserRole(loginController.studentsController, studentEmail);
            } else if (loginController.staffController.staffService.verifyStaff(loginController.staffController, staffEmail)) {
                return "Staff";
            } else {
                return "Unknown";
            }
    }

    /**
     * Verifies the credentials of a user.
     * 
     * @param loginController The login controller object.
     * @param id The user ID.
     * @param password The user password.
     * @return true if the credentials are valid, false otherwise.
     */
    @Override
    public boolean verifyCredentials(LoginController loginController, String id, String password) {
        // Verify that the ID is not null
        if (id == null || id.isEmpty()) {
            System.out.println("Invalid ID.");
            return false;
        }
    
        // Normalize the email domain
        String studentEmail = (id + "@e.ntu.edu.sg");
        String staffEmail = (id + "@NTU.EDU.SG");
    
        // Check if the user is a student or staff and verify the password
        if (loginController.studentsController.studentAuthenticationService.verifyStudent(loginController.studentsController, studentEmail)) {
            return loginController.studentsController.studentAuthenticationService.checkPassword(loginController.studentsController, studentEmail, password);
        } else if (loginController.staffController.staffService.verifyStaff(loginController.staffController, staffEmail)) {
            return loginController.staffController.staffService.checkPassword(loginController.staffController, staffEmail, password);
        } else {
            System.out.println("User not found.");
            return false;
        }
    }
    
}
