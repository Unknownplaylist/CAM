package Controllers.LoginManagement;

import Controllers.LoginManagementInterface.LoginControllerInterface;
import Controllers.LoginManagementInterface.LoginServiceInterface;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import UI.Main;
import UI.CampCommitteeViewManagement.CampCommitteeView;
import UI.StaffViewManagement.StaffView;
import UI.StudentViewManagement.StudentView;

public class LoginController implements LoginControllerInterface {
    StudentsController studentsController;
    StaffController staffController; 
    public LoginServiceInterface loginService;

    public LoginController(StudentsController studentsController, StaffController staffController) {
        this.studentsController = studentsController;
        this.staffController = staffController;
        this.loginService = new LoginService();
    }

    @Override
    public boolean loginUser(String id, String password) {
        // Verify user credentials (e.g., id and password)
        boolean validCredentials = loginService.verifyCredentials(this, id, password);

        if (validCredentials) {
            // Check if the user is a student or staff
            if (studentsController.studentAuthenticationService.verifyStudent(studentsController, id + "@e.ntu.edu.sg") || staffController.staffService.verifyStaff(staffController, id + "@NTU.EDU.SG")) {
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

    @Override
    public void UIlogIn(Main main, StudentsController sdc, StaffController sfc){
        System.out.print("Username : ");
        String userId = Main.sc.next();
        System.out.print("Password : ");
        String password = Main.sc.next();
        System.out.println();
        boolean loggedIn = loginUser(userId, password);
    
        if(loggedIn){
            String user = loginService.userType(this, userId);
            if(user.equalsIgnoreCase("attendee")){
                main.studentview = new StudentView(userId,sdc,sfc);
                main.studentview.studentMenuService.display(main.studentview);
            }
            else if(user.equalsIgnoreCase("committee")){
                main.commview = new CampCommitteeView(userId, sdc);
                main.commview.campCommitteeMenuService.display(main.commview);
            }
            else{
                main.staffview = new StaffView(userId,sfc);
                main.staffview.staffMenuService.display(main.staffview);
            }
        }
    }
    

    
}
