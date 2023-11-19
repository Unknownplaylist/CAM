package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;

public class StaffEnquiryUI {

    public void replyEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.replytoEnquiries(staffView.access);
    }

    public void viewEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.viewEnquiries(staffView.access);
    }
    
}
