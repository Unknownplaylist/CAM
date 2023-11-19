package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeCampUIInterface;

public class CampCommitteeCampUI implements CampCommitteeCampUIInterface {

    @Override
    public void viewCurrentPoint(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.viewPoint(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    @Override
    public void viewCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewAvailableCamps(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    @Override
    public void viewCampDetails(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.generateStudentList(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    @Override
    public void viewYourCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewMyCamps(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
