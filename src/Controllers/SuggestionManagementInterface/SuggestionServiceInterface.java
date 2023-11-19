package Controllers.SuggestionManagementInterface;

import java.util.List;

import Controllers.SuggestionManagement.SuggestionController;

public interface SuggestionServiceInterface {

    void createSuggestion(SuggestionController suggestionController, String campCommitteeMember, String campName);

    void editSuggestion(SuggestionController suggestionController, String campCommitteeMember, String camp);

    void deleteSuggestion(SuggestionController suggestionController, String campCommitteeMember, String camp);

    List<String[]> studentFindSuggestion(String campCommitteeMember);

    String[] studentSuggestionBasedOnIDandCamp(String student, String camp);

}