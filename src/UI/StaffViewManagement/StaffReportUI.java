package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffReportUIInterface;

public class StaffReportUI implements StaffReportUIInterface {

    /**
     * Generates a report for the given staff view.
     * 
     * @param staffView the staff view for which the report is generated
     */
    @Override
    public void generateReport(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.campReportingService.generateReport(staffView.access);
    }
    
}
