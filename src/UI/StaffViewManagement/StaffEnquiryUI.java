package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffEnquiryUIInterface;

public class StaffEnquiryUI implements StaffEnquiryUIInterface {

    @Override
    public void replyEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.replytoEnquiries(staffView.access);
    }

    @Override
    public void viewEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.viewEnquiries(staffView.access);
    }
    
}
