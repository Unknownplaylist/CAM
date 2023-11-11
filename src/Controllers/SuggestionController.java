package Controllers;

import java.io.*;
import java.util.*;

//A camp committee member can submit suggestions to staff
//A camp committee member can view, edit and delete the details of his/her suggestions before being processed
//A camp committee member can get one point for each suggestion given. One extra point for each accepted suggestion

//Enquiry.csv:
//Suggestion,CampCommitteeMember,Camp,Staff,Read,Accept

/*General Rule for Suggestion Controller
-Only campCommitteeMember can write a suggestion to his/her camp.
-Only the staff in charge of the camp can read and accept/decline the suggestion 
-If the enquiry has not been uploaded, the student can choose to delete the suggestion
-campCommitteeMember can view the suggestion, including the message, read and accept status

A student can submit suggestion to any camp he/she can see.
Cannot be deleted once the enquiry has been answered. It will be stored.
That reply can be seen by all the committee members and staff of that camp, besides the student who sent inquiry.


Enquiry flow:
-CampCommitteeMember create suggestion, type in the message (Each camp will only have one active suggestion at a time for simplicity)
-Take note of the name of the CampCommitteeMember, the name of the camp and the staff in charge
-The staff in charge can checkSuggestion to see if there is any pending suggestion
-The staff reads and accepts/declines the enquiry, after which the read flag will be ticked and stored in the database.
 */

public class SuggestionController {
    private static final String FILE_PATH = "src/Database/Suggestion.csv";
    private static final String CSV_SEPARATOR = ",";

    private String suggestion;
    private String campCommitteeMember;
    private String camp;
    private String staff;
    private String read;
    private String accepted; //Either "Accepted" or "Rejected"

    public void createSuggestion(){
        //Generate the enquiry details, including the name of the student and set the read and reply section to ""
        campCommitteeMember = "TBD";
        read = " ";
        accepted = " ";
        staff = "TBD";

        Scanner sc = new Scanner(System.in);

        //get camp of the campCommitteeMember first, put arbitrary camp for now
        camp = "SCSE Camp";
    
        System.out.println("Type in your suggestion: ");
        suggestion = sc.nextLine();

        sc.close();

        String data = String.join(CSV_SEPARATOR,suggestion, campCommitteeMember, camp, staff,read,accepted);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error uploading the enquiry");
            e.printStackTrace();
        }
    }

    public void deleteSuggestion(){
        //to do later
    }

    public void formatSuggestion(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("From: "+data[1]);
            System.out.println("About "+data[2] + ".To "+data[3]);
            if (Objects.equals(data[4]," ")){
                System.out.println("Read Status: Not read || Status: Pending");
            }
            else { //Add accepted and rejected
                System.out.println("Read Status: Read || Status: "+data[5]);
            }
            System.out.println("--------------------------------");
            System.out.println("Message: " + data[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error displaying the suggestion.");
        }
    }

    public int checkSuggestion(String camp){
        //Find the enquiry relevant to the camp that is not read and replied
        //return the line number for enquiry in csv file
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum ++;
                String[] data = line.split(CSV_SEPARATOR);
                if (Objects.equals(data[2], camp) ){                   
                    System.out.println("There is a suggestion for your camp pending for reply.");
                    formatSuggestion(data);
                    return lineNum;                    
                }
            }
            System.out.println("No pending suggestion.");
            return 0;
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return -1;
    }
    
    public String[] findSuggestion(int lineNum){
        String line;
        int current = 0;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                if (current == lineNum){
                    data = line.split(CSV_SEPARATOR);
                    return data;
                }
                current ++;
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        System.out.println("Cannot find the suggestion.");
        return null;
    }

    public void acceptSuggestion(String[] data){
        //need to find the enquiry first
        //take in line num of the enquiry from findEnquiry() if there is a pending enquiry
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in your decision: Accepted/Rejected");
        String Reply = sc.nextLine();
        data[4] = "Read";
        data[5] = Reply;
        System.out.println("Suggestion have been updated!");
        sc.close();

        String upload = String.join(CSV_SEPARATOR,data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(upload);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error uploading the suggestion");
            e.printStackTrace();
        }
    }

    public String viewSuggestionDecision(){
        //the campCommitteeMember can view the decision
        
        return accepted;
    }

    public void generateSuggestionReport(){
        //TODO: create a report for main staff and camp committee member in charge of the camp
    }
}
