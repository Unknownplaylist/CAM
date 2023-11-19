package Controllers.SuggestionManagement;

import java.io.*;
import java.util.*;

import Controllers.SuggestionManagementInterface.SuggestionFileHandlerInterface;
import Controllers.SuggestionManagementInterface.SuggestionReviewServiceInterface;
import Controllers.SuggestionManagementInterface.SuggestionServiceInterface;
import Controllers.SuggestionManagementInterface.SuggestionViewControllerInterface;

//A camp committee member can submit suggestions to staff
//A camp committee member can view, edit and delete the details of his/her suggestions before being processed
//A camp committee member can get one point for each suggestion given. One extra point for each accepted suggestion

//Enquiry.csv:
//Suggestion,CampCommitteeMember,Camp,Read,Review

public class SuggestionController {
    static Scanner sc = new Scanner(System.in);
    static final String FILE_PATH = "src/Database/Suggestion.csv";
    static final String CSV_SEPARATOR = ",";
    SuggestionFileHandlerInterface suggestionFileHandler;
    public SuggestionServiceInterface suggestionService;
    public SuggestionViewControllerInterface suggestionViewController;
    public SuggestionReviewServiceInterface suggestionReviewService;

    public SuggestionController(){
        suggestionFileHandler = new SuggestionFileHandler();
        suggestionService = new SuggestionService();
        suggestionViewController = new SuggestionViewController();
        suggestionReviewService = new SuggestionReviewService();
    }

    

    // public void readSuggestion(String campCommitteeMember){
    //     String[] studentSuggestion;
    //     if (studentFindSuggestion(campCommitteeMember) != null){
    //         studentSuggestion = studentFindSuggestion(campCommitteeMember);
    //     }
    //     else {
    //         System.out.println("Cannot find the suggestion");
    //         return;
    //     }
    //     if ((!studentSuggestion[3].equals(" ")) && (!studentSuggestion[4].equals(" "))){
    //         formatMessage(studentSuggestion);
    //     }
    //     else {
    //         System.out.println("Your suggestion has not been reviewed. Stay tuned!");
    //     }
    // }

    //IMPLEMENTATIONS FOR STAFF

    // public String[] checkSuggestion(String camp){
    //     String[] data = findSuggestion(camp);
    //     if (data != null){
    //         System.out.println("There is a suggestion for your camp pending for review.");
    //         formatMessage(data);
    //         return data;
    //     }
    //     else {
    //         System.out.println("No pending suggestion");
    //         return null;
    //     }        
    // }

    // public String[] findSuggestion(String camp){
    //     String line;
    //     String[] data;
    //     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    //         while ((line = br.readLine()) != null) {
    //             data = line.split(CSV_SEPARATOR);
    //             if (data[2].equalsIgnoreCase(camp)){
    //                 return data;
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error reading suggestion file");
    //         e.printStackTrace();
    //     }
    //     System.out.println("Cannot find the suggestion for " + camp);
    //     return null;
    // }
    // public String[] studentFindSuggestion(String campCommitteeMember){
    //     String line;
    //     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    //         String[] data;
    //         while ((line = br.readLine()) != null) {
    //             data = line.split(CSV_SEPARATOR);
    //             if (data[1].equalsIgnoreCase(campCommitteeMember)){
    //                 return data;
    //             }
    //         }
    //         System.out.println("Cannot find your suggestion. Have you submitted a suggestion?");
    //         return null;
    //     } catch (IOException e) {
    //         System.out.println("Error reading suggestion file");
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
}
