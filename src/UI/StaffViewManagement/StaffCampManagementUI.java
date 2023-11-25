package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffCampManagementUIInterface;

public class StaffCampManagementUI implements StaffCampManagementUIInterface {

    /**
     * Creates a new camp using the provided staff view.
     * 
     * @param staffView the staff view containing the necessary information for creating the camp
     */
    @Override
    public void CreatCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.createCamp(staffView.access);
    }

    /**
     * Edits the camp information for the given staff view.
     * 
     * @param staffView the staff view containing the camp information to be edited
     */
    @Override
    public void editCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.editCamp(staffView.access);
    }

    /**
     * Removes a camp from the staff view.
     * 
     * @param staffView the staff view object
     */
    @Override
    public void removeCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.delCamp(staffView.access);
    }

    /**
     * Changes the visibility of the staff view.
     * 
     * @param staffView the staff view to modify
     */
    @Override
    public void visibility(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.changeVisibility(staffView.access);
    }

    /**
     * Displays the camps associated with the staff member.
     * 
     * @param staffView The staff view object.
     */
    @Override
    public void viewMyCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, true);
    }

    /**
     * Displays the camps for the staff view.
     * 
     * @param staffView the staff view object
     */
    @Override
    public void viewCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, false);
    }
    
}
