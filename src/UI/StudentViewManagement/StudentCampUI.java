package UI.StudentViewManagement;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;

public class StudentCampUI {

    public void registerForCamp(StudentView studentView) {
        System.out.print("Enter the name of the camp to register: ");
        String campName = StudentView.sc.next();
    
        CampController campController = new CampController(studentView.studentCont, studentView.staffCont); // Initialize campController
    
        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        // Check if the student is already registered in the camp
        if (campController.campService.isStudentRegisteredInCamp(campController, studentView.email, campName)) {
            System.out.println("You are already registered in this camp.");
            return;
        }
    
        // Register the student as a participant
        studentView.studentAccess.studentCampRegistrationService.registerForCamp(studentView.studentAccess, studentView.email, campName, false); // false indicates a regular participant
       
    }

    public void withdrawFromCamp(StudentView studentView) {
        System.out.print("Enter the name of the camp you wish to withdraw from: ");
        String campName = StudentView.sc.next();
        studentView.studentAccess.studentCampRegistrationService.withdrawFromCamp(studentView.studentAccess, studentView.email, campName);
    }

    public void viewCamps(StudentView studentView) {
        studentView.studentAccess.studentCampViewService.viewAvailableCamps(studentView.studentAccess, studentView.email);
    }

    public void viewMyCamps(StudentView studentView) {
        studentView.studentAccess.studentCampViewService.viewMyCamps(studentView.studentAccess, studentView.email);
    }
    
}
