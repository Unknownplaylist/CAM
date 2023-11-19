package UI.CampCommitteeViewManagement;

public class CampCommitteeCampUI {

    void viewCurrentPoint(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.viewPoint(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    void viewCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewAvailableCamps(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    void viewCampDetails(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.studentManagementService.generateStudentList(campCommitteeView.committeeAccess, campCommitteeView.email);
    }

    void viewYourCamps(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.campCommitteeService.viewMyCamps(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
