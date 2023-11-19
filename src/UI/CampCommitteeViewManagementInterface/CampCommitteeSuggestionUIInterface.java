package UI.CampCommitteeViewManagementInterface;

import UI.CampCommitteeViewManagement.CampCommitteeView;

public interface CampCommitteeSuggestionUIInterface {

    void submitSuggestions(CampCommitteeView campCommitteeView);

    void viewSuggestion(CampCommitteeView campCommitteeView);

    void editSuggestion(CampCommitteeView campCommitteeView);

    void deleteSuggestion(CampCommitteeView campCommitteeView);

}