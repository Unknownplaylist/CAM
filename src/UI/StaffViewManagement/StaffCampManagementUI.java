package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;

public class StaffCampManagementUI {

    public void CreatCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.createCamp(staffView.access);
    }

    public void editCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.editCamp(staffView.access);
    }

    public void removeCamp(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.delCamp(staffView.access);
    }

    public void visibility(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.changeVisibility(staffView.access);
    }

    public void viewMyCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, true);
    }

    public void viewCamps(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campManagementService.viewCamps(staffView.access, false);
    }
    
}
