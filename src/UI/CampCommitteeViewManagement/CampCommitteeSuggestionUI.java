package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeSuggestionUIInterface;

public class CampCommitteeSuggestionUI implements CampCommitteeSuggestionUIInterface {

    /**
     * Submits suggestions to the suggestion management service.
     * 
     * @param campCommitteeView The camp committee view.
     */
    @Override
    public void submitSuggestions(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.submitSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    /**
     * Displays the suggestion for the camp committee view.
     * 
     * @param campCommitteeView The camp committee view object.
     */
    @Override
    public void viewSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.viewSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    /**
     * Edits the suggestion in the camp committee view.
     * 
     * @param campCommitteeView The camp committee view containing the suggestion.
     */
    @Override
    public void editSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.editSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }

    /**
     * Deletes a suggestion from the camp committee view.
     * 
     * @param campCommitteeView The camp committee view containing the suggestion.
     */
    @Override
    public void deleteSuggestion(CampCommitteeView campCommitteeView) {
        campCommitteeView.committeeAccess.suggestionManagementService.deleteSuggestion(campCommitteeView.committeeAccess, campCommitteeView.name);
    }
    
}
