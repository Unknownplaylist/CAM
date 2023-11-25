package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffEnquiryUIInterface;

public class StaffEnquiryUI implements StaffEnquiryUIInterface {

    /**
     * This method is used to reply to enquiries made by staff members.
     * It creates a new StaffAccess object and calls the replytoEnquiries method of the EnquiryResponseService class.
     * 
     * @param staffView The StaffView object containing the necessary information for replying to enquiries.
     */
    @Override
    public void replyEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.replytoEnquiries(staffView.access);
    }

    /**
     * Displays the enquiries for the staff view.
     * 
     * @param staffView the staff view object
     */
    @Override
    public void viewEnquiries(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.enquiryResponseService.viewEnquiries(staffView.access);
    }
    
}
