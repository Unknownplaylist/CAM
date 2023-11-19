package UI.CampCommitteeViewManagement;

public class CampCommitteeProfileUI {

    public void PasswordChange(CampCommitteeView campCommitteeView){
        System.out.print("Enter your new password: ");
        String new_pass=CampCommitteeView.sc.next();
        campCommitteeView.student_controller.studentService.changePassword(campCommitteeView.student_controller, campCommitteeView.email, new_pass);
        System.out.println("\nYou will now be logged out.");
        campCommitteeView.logOff=2;
    }
    
}
