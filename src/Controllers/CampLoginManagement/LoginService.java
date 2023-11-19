package Controllers.CampLoginManagement;

public class LoginService {

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

    boolean verifyCredentials(LoginController loginController, String id, String password) {
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
