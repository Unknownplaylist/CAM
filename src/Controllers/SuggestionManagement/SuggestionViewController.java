package Controllers.SuggestionManagement;

import java.util.List;
import java.util.Objects;

import Controllers.SuggestionManagementInterface.SuggestionViewControllerInterface;

public class SuggestionViewController implements SuggestionViewControllerInterface {

    /**
     * Formats and displays the list of suggestions.
     * 
     * @param suggestionController The suggestion controller.
     * @param suggestionList The list of suggestions to be formatted and displayed.
     */
    @Override
    public void formatMessageList(SuggestionController suggestionController, List<String[]> suggestionList){
        if (suggestionList == null){
            System.out.println("No message to display.");
            return;
        }
        for (String[] i : suggestionList){
            System.out.println("--------------------------------");
            suggestionController.suggestionViewController.formatMessage(i);
            System.out.println("--------------------------------");
        }
    }

    /**
     * Formats and prints out the view of an enquiry message.
     * 
     * @param data an array of strings containing the necessary data for formatting the message
     */
    @Override
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("--------------------------------");
            System.out.println("From: "+data[1]);
            System.out.println("To "+data[2] + " Staff");
            if (Objects.equals(data[3]," ")){
                System.out.println("Read Status: Not read || Review Status: Not reviewed");
            }
            else {
                System.out.println("Read Status: Read || Review Status: " + data[4]);
                System.out.println("--------------------------------");               
            }
            System.out.println("Suggestion: " + data[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough data");
        }
    }

    /**
     * Displays the review for a suggestion.
     * 
     * @param suggestionController The suggestion controller.
     * @param camp The camp name.
     */
    @Override
    public void viewReviewToSuggestion(SuggestionController suggestionController, String camp){
        List<String[]> suggestionList = suggestionController.suggestionReviewService.execFindSuggestion(camp);
        if (!suggestionList.isEmpty()){
            for (String[] i : suggestionList){
                formatMessage(i);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        else System.out.println("No visible suggestions.");       
    }

    /**
     * View all suggestions under the camp committee member's name.
     *
     * @param suggestionController The suggestion controller.
     * @param campCommitteeMember  The camp committee member's name.
     */
    @Override
    public void viewSuggestion(SuggestionController suggestionController, String campCommitteeMember){ //view all suggestions under the camp comm name
        List<String[]> suggestionList = suggestionController.suggestionService.studentFindSuggestion(campCommitteeMember);
        if(!suggestionList.isEmpty()){
            for (String[] suggestion: suggestionList){
                formatMessage(suggestion);
            }
        }
        else
            System.out.println("No Suggestions to View");
    }
    
}
