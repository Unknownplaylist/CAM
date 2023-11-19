package UI.CampCommitteeViewManagement;

public class CampCommitteeSuggestionUI {

    void submitSuggestions(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.submitSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    void viewSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.viewSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    void editSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.editSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    void deleteSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.deleteSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
