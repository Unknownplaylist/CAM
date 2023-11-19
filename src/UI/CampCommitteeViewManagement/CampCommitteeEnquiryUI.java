package UI.CampCommitteeViewManagement;

public class CampCommitteeEnquiryUI {

    void checkEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.checkEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    void replyEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.replyEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
