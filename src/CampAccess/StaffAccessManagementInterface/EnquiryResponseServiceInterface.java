package CampAccess.StaffAccessManagementInterface;

import CampAccess.StaffAccessManagement.StaffAccess;

public interface EnquiryResponseServiceInterface {

    void replytoEnquiries(StaffAccess staffAccess);

    void viewEnquiries(StaffAccess staffAccess);

}