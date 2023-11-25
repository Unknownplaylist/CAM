package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeProfileUIInterface;

public class CampCommitteeProfileUI implements CampCommitteeProfileUIInterface {

    /**
     * Changes the password for the camp committee user.
     * 
     * @param campCommitteeView The CampCommitteeView object.
     */
    @Override
    public void PasswordChange(CampCommitteeView campCommitteeView){
        System.out.print("Enter your new password: ");
        String new_pass=CampCommitteeView.sc.next();
        campCommitteeView.student_controller.studentService.changePassword(campCommitteeView.student_controller, campCommitteeView.email, new_pass);
        System.out.println("\nYou will now be logged out.");
        campCommitteeView.logOff=2;
    }
    
}
