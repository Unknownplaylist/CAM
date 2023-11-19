package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeSuggestionUIInterface;

public class CampCommitteeSuggestionUI implements CampCommitteeSuggestionUIInterface {

    @Override
    public void submitSuggestions(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.submitSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    @Override
    public void viewSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.viewSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    @Override
    public void editSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.editSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    @Override
    public void deleteSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.deleteSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
