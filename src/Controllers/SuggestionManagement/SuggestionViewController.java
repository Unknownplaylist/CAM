package Controllers.SuggestionManagement;

import java.util.List;
import java.util.Objects;

public class SuggestionViewController {

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
