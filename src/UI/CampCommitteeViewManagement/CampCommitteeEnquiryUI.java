package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeEnquiryUIInterface;

public class CampCommitteeEnquiryUI implements CampCommitteeEnquiryUIInterface {

    @Override
    public void checkEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.checkEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    @Override
    public void replyEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.replyEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
