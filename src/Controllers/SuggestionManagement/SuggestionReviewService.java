package Controllers.SuggestionManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.SuggestionManagementInterface.SuggestionReviewServiceInterface;
import Controllers.SuggestionManagementInterface.SuggestionServiceInterface;

public class SuggestionReviewService implements SuggestionReviewServiceInterface {
    static final String FILE_PATH = "src/Database/Suggestion.csv";
    /**
     * Generates a list of all suggestions for a given camp, including both replied and unreplied suggestions.
     *
     * @param camp the camp for which to find suggestions
     * @return a list of string arrays representing the suggestions
     */
    @Override
    public List<String[]> execFindSuggestion(String camp){ //generate the list of all enquiries for that camp, both replied and unreplied
        List<String[]> suggestionList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(SuggestionController.CSV_SEPARATOR);
                if (data[2].equals(camp)){
                    suggestionList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return suggestionList;
    }

    /**
     * Retrieves a list of unreplied suggestions for a given camp.
     * 
     * @param camp the camp for which to find unreplied suggestions
     * @return a list of unreplied suggestions as an array of strings, or null if there are no suggestions for the camp
     */
    @Override
    public List<String[]> execFindUnrepliedSuggestion(String camp){
        List<String[]> suggestionList = execFindSuggestion(camp);
        List<String[]> unrepliedSuggestionList = new ArrayList<String[]>();
    
        if (suggestionList.isEmpty()){
            System.out.println("No suggestions for " + camp);
            return null;
        }
    
        for (String[] i : suggestionList){
            if ((i[3].equals(" ")) && (i[4].equals(" "))){
                unrepliedSuggestionList.add(i);
            }
        }
        if (unrepliedSuggestionList.size() !=  0){
            System.out.println(unrepliedSuggestionList.size() + " suggestion/suggestions for your camp pending for reply.");
        }
        else {
            System.out.println("No pending suggestion");
        }
        return unrepliedSuggestionList;
    }

    /**
     * Executes the review of a suggestion.
     * 
     * @param suggestionController The suggestion controller.
     * @param camp The camp name.
     */
    @Override
    public void execReviewSuggestion(SuggestionController suggestionController, String camp){
        System.out.print("Type in the Suggestion ID to reply to: ");
        String ID = SuggestionController.sc.nextLine();
    
        String[] suggestionToReply = null;
    
        List<String[]> suggestionList = execFindUnrepliedSuggestion(camp);
        if (suggestionList.isEmpty()) {
            System.out.println("No pending suggestion to reply.");
        }
        for (String[] i : suggestionList){
            if (i[5].equals(ID)){
                if (i[2].equals(camp)){
                    suggestionToReply = i;
                    break;
                }
                else {
                    System.out.println("Suggestion ID " + ID + " does not match " + camp);
                    return;
                }               
            }
        }
    
        if (suggestionToReply != null){
            if ((!suggestionToReply[3].equals(" ")) && (!suggestionToReply[4].equals(" "))){
                System.out.println("The enquiry has already been replied!");
                return;
            }
            else {
                suggestionController.suggestionReviewService.reviewSuggestion(suggestionController, suggestionController.suggestionService, suggestionToReply);
            }
        }
        else {
            System.out.println("No enquiry under ID " + ID + " for " + camp);
        }
    }

    /**
     * Reviews a suggestion and updates its status and decision in the suggestion file.
     * 
     * @param suggestionController The suggestion controller object.
     * @param suggestionService The suggestion service interface.
     * @param suggestionToReview The suggestion to be reviewed.
     */
    @Override
    public void reviewSuggestion(SuggestionController suggestionController, SuggestionServiceInterface suggestionService, String[] suggestionToReview){
        System.out.println("Choose to accept/reject this suggestion: \n1.Accept\n2.Reject"); 
        int choice = SuggestionController.sc.nextInt();
        String decision = new String();
        switch (choice) {
            case 1:
                decision = "Accepted";
                break;
            case 2: 
                decision = "Rejected";
                break;
        }
        suggestionToReview[3] = "Read";
        suggestionToReview[4] = decision;
        System.out.println("Decision has been saved!");
    
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(SuggestionController.CSV_SEPARATOR);
                    if ((data[2].equalsIgnoreCase(suggestionToReview[2])) 
                        && (data[1].equalsIgnoreCase(suggestionToReview[1])) 
                        && (data[0].equalsIgnoreCase(suggestionToReview[0]))) { //cross-validate the student name and camp
                        dataList.add(suggestionToReview);
                    }
                    else {
                        dataList.add(data);
                    }
                }
                suggestionController.suggestionFileHandler.writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your decision request.");
            e.printStackTrace();
        }   
    }
    
}
