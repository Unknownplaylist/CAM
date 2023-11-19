package Controllers.SuggestionManagementInterface;

import java.util.List;

import Controllers.SuggestionManagement.SuggestionController;

public interface SuggestionReviewServiceInterface {

    List<String[]> execFindSuggestion(String camp);

    List<String[]> execFindUnrepliedSuggestion(String camp);

    void execReviewSuggestion(SuggestionController suggestionController, String camp);

    void reviewSuggestion(SuggestionController suggestionController, SuggestionServiceInterface suggestionService,
            String[] suggestionToReview);

}