package CampAccess.CampCommitteeAccessManagementInterface;

import CampAccess.CampCommitteeAccessManagement.CommitteeAccess;

public interface EnquiryManagementServiceInterface {

    void checkEnquiry(CommitteeAccess committeeAccess, String campCommName);

    void viewEnquiry(CommitteeAccess committeeAccess, String campCommName);

    void replyEnquiry(CommitteeAccess committeeAccess, String campCommName);

}