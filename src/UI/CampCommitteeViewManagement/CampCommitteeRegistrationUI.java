package UI.CampCommitteeViewManagement;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;
import UI.CampCommitteeViewManagementInterface.CampCommitteeMenuServiceInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeRegistrationUIInterface;

public class CampCommitteeRegistrationUI implements CampCommitteeRegistrationUIInterface {

    @Override
    public void withdrawFromCamp(CampCommitteeView campCommitteeView) {
        System.out.print("Enter the name of the camp you wish to withdraw from: ");
        String campName = CampCommitteeView.sc.next();
        campCommitteeView.committeeAccess.campCommitteeService.withdrawFromCamp(campCommitteeView.committeeAccess, campCommitteeView.email, campName);
    }

    @Override
    public void registerForCamp(CampCommitteeView campCommitteeView, CampCommitteeMenuServiceInterface campCommitteeMenuService) {
        System.out.print("Enter the name of the camp to register: ");
        String campName = CampCommitteeView.sc.next();
    
        CampController campController = new CampController(campCommitteeView.student_controller, campCommitteeView.staff_controller); // Initialize campController
    
        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        // Check if the student is already registered in the camp
        if (campController.campService.isStudentRegisteredInCamp(campController, campCommitteeView.email, campName)) {
            System.out.println("You are already registered in this camp.");
            return;
        }
    
        // Register the student as a participant
        campCommitteeView.committeeAccess.campCommitteeService.registerForCamp(campCommitteeView.committeeAccess, campCommitteeView.email, campName, false); // false indicates a regular participant
       
    }
    
}
