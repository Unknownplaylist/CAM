package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeEnquiryUIInterface;

public class CampCommitteeEnquiryUI implements CampCommitteeEnquiryUIInterface {

    /**
     * Checks the enquiries for a camp committee view.
     * 
     * @param campCommitteeView The camp committee view to check enquiries for.
     */
    @Override
    public void checkEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.checkEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    /**
     * Replies to the enquiries made by the camp committee view.
     * This method calls the replyEnquiry method of the enquiry management service,
     * passing the committee access and the name of the camp committee view as parameters.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void replyEnquiries(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.enquiryManagementService.replyEnquiry(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
