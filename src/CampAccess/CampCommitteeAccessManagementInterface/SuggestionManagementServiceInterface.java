package CampAccess.CampCommitteeAccessManagementInterface;

import CampAccess.CampCommitteeAccessManagement.CommitteeAccess;

public interface SuggestionManagementServiceInterface {

    void submitSuggestion(CommitteeAccess committeeAccess, String campCommName);

    void viewSuggestion(CommitteeAccess committeeAccess, String campCommName);

    void editSuggestion(CommitteeAccess committeeAccess, String campCommName);

    void deleteSuggestion(CommitteeAccess committeeAccess, String campCommName);

}