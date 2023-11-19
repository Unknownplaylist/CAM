package Controllers.SuggestionManagementInterface;

import java.util.List;

import Controllers.SuggestionManagement.SuggestionController;

public interface SuggestionViewControllerInterface {

    void formatMessageList(SuggestionController suggestionController, List<String[]> suggestionList);

    void formatMessage(String[] data);

    void viewReviewToSuggestion(SuggestionController suggestionController, String camp);

    void viewSuggestion(SuggestionController suggestionController, String campCommitteeMember);

}