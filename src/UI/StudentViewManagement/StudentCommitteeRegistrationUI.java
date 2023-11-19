package UI.StudentViewManagement;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;

public class StudentCommitteeRegistrationUI {

    public void registerAsCampCommittee(StudentView studentView) {
        System.out.print("Enter the name of the camp to register as a committee member: ");
        String campName = StudentView.sc.next();  
    
        CampController campController = new CampController(studentView.studentCont, studentView.staffCont); // Initialize campController
    
        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        // Check if the student is already registered as a participant or committee member
        if (campController.campService.isStudentRegisteredInCamp(campController, studentView.email, campName)) {
            System.out.println("You are already registered in this camp.");
            return;
        }
    
        // Register the student as a committee member
        studentView.studentAccess.studentCampRegistrationService.registerForCamp(studentView.studentAccess, studentView.email, campName, true);
        studentView.studentCont.studentService.setStudentRole(studentView.studentCont, studentView.email, "committee");
        System.out.println("You have been successfully registered as a committee member for the camp: " + campName+ "\n");
    
        System.out.println("Please Relogin logging off...");
        studentView.logOff = 2;
    
    }
    
}
