package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffCampManagementUIInterface;

public class StaffCampManagementUI implements StaffCampManagementUIInterface {

    @Override
    public void CreatCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.createCamp(staffView.access);
    }

    @Override
    public void editCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.editCamp(staffView.access);
    }

    @Override
    public void removeCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.delCamp(staffView.access);
    }

    @Override
    public void visibility(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.changeVisibility(staffView.access);
    }

    @Override
    public void viewMyCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, true);
    }

    @Override
    public void viewCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, false);
    }
    
}
