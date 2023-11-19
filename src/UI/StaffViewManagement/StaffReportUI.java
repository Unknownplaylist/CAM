package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;

public class StaffReportUI {

    public void generateReport(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campReportingService.generateReport(staffView.access);
    }
    
}
