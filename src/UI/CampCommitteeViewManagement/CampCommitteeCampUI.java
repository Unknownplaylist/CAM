package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeCampUIInterface;

public class CampCommitteeCampUI implements CampCommitteeCampUIInterface {

    /**
     * Displays the current point of the camp committee view.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void viewCurrentPoint(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.viewPoint(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    /**
     * Displays the available camps for the camp committee view.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void viewCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewAvailableCamps(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    /**
     * Displays the details of a camp for the camp committee view.
     * This method generates a student list using the student management service.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void viewCampDetails(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.generateStudentList(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    /**
     * Displays the camps associated with the current camp committee view.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void viewYourCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewMyCamps(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
