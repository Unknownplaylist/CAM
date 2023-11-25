package Controllers.SuggestionManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.SuggestionManagementInterface.SuggestionServiceInterface;

public class SuggestionService implements SuggestionServiceInterface {
    static final String FILE_PATH = "src/Database/Suggestion.csv";
    /**
     * Creates a suggestion and writes it to a file.
     * 
     * @param suggestionController The suggestion controller object.
     * @param campCommitteeMember The name of the camp committee member.
     * @param campName The name of the camp.
     */
    @Override
    public void createSuggestion(SuggestionController suggestionController, String campCommitteeMember, String campName){
        String read = " ";
        String review = " ";
        String ID = " "; //To be assigned later 
        
        System.out.println("Type in your suggestion: ");
        String message = SuggestionController.sc.nextLine();
    
        String data = String.join(SuggestionController.CSV_SEPARATOR,message, campCommitteeMember, campName,read,review, ID);
        String[] suggestion = data.split(SuggestionController.CSV_SEPARATOR);
        suggestionController.suggestionFileHandler.writeSuggestionToFile(suggestion);
    }

    /**
     * Edits a suggestion based on the student's name, assuming a student can only send one enquiry to one camp.
     * If the suggestion is not found or has already been viewed by a staff, the edit request cannot proceed.
     * Otherwise, the user is prompted to enter the edited suggestion, and the suggestion is updated in the CSV file.
     *
     * @param suggestionController The SuggestionController object.
     * @param campCommitteeMember The name of the camp committee member.
     * @param camp The camp name.
     */
    @Override
    public void editSuggestion(SuggestionController suggestionController, String campCommitteeMember, String camp){ //Look for the enquiry based on the student's name, assuming a student can only send one enquiry to one camp
        String[] studentSuggestion = suggestionController.suggestionService.studentSuggestionBasedOnIDandCamp(campCommitteeMember, camp);
    
        if(studentSuggestion == null){
            System.out.println("Cannot proceed with your edit request");
            return;
        }
    
        if ((studentSuggestion[3].equals(" ")) && (studentSuggestion[4].equals(" "))){
            System.out.println("Edit your suggestion here: ");
            String message = SuggestionController.sc.nextLine();
            studentSuggestion[0] = message;
    
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(SuggestionController.CSV_SEPARATOR);
                    if ( (data[1].equalsIgnoreCase(studentSuggestion[1]))
                    && (data[5].equalsIgnoreCase(studentSuggestion[5])) ) {
                        dataList.add(studentSuggestion);
                        continue;
                    }
                    dataList.add(data);
                }
                suggestionController.suggestionFileHandler.writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your edit request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff has viewed your suggestion, cannot edit the suggestion.");
        }
        
    }

    /**
     * Deletes a suggestion based on the provided camp committee member and camp.
     * If the suggestion is not found or has been viewed by a staff, the delete request cannot proceed.
     *
     * @param suggestionController The suggestion controller object.
     * @param campCommitteeMember The camp committee member.
     * @param camp The camp.
     */
    @Override
    public void deleteSuggestion(SuggestionController suggestionController, String campCommitteeMember, String camp){
        String[] studentSuggestion = suggestionController.suggestionService.studentSuggestionBasedOnIDandCamp(campCommitteeMember, camp);
    
        if(studentSuggestion == null){
            System.out.println("Cannot proceed with your delete request");
            return;
        }
    
        if ((studentSuggestion[3].equals(" ")) && (studentSuggestion[4].equals(" "))){
    
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(SuggestionController.CSV_SEPARATOR);
                    if ((data[0].equalsIgnoreCase(studentSuggestion[0])) 
                    && (data[1].equalsIgnoreCase(studentSuggestion[1]))
                    && (data[5].equalsIgnoreCase(studentSuggestion[5])) ) {
                        continue;
                    }
                    dataList.add(data);
                }
                suggestionController.suggestionFileHandler.writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your delete request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff has viewed your suggestion, cannot delete the suggestion.");
        }
    }

    /**
     * Retrieves a list of suggestions made by a specific camp committee member.
     *
     * @param campCommitteeMember The name of the camp committee member.
     * @return A list of string arrays representing the suggestions made by the camp committee member.
     */
    @Override
    public List<String[]> studentFindSuggestion(String campCommitteeMember){
        List<String[]> suggestionList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(SuggestionController.CSV_SEPARATOR);
                if (data[1].equalsIgnoreCase(campCommitteeMember)){
                    suggestionList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return suggestionList;
    }

    /**
     * Retrieves a student suggestion based on the student's name and camp.
     * 
     * @param student the name of the student
     * @param camp the camp name
     * @return an array of strings representing the suggestion data, or null if no suggestion is found
     */
    @Override
    public String[] studentSuggestionBasedOnIDandCamp(String student, String camp){
        List<String[]> studentEnquiryList = studentFindSuggestion(student);
        if(studentEnquiryList.isEmpty()){
            System.out.println("No Suggestions under the student name.");
            return null;
        }
        System.out.print("Type in the Suggestion ID: ");
        String ID  = SuggestionController.sc.nextLine();
    
        for (String[] data: studentEnquiryList){
            if (data[5].equals(ID)){
                if (data[2].equals(camp)){
                    return data;
                } else {
                    System.out.println("Suggestion ID " + ID + " does not match the " + camp);
                    return null;
                }
            }
        }
        return null;
    }
    
}
