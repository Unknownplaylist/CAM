package Controllers;

import java.io.*;
import java.util.*;

//A camp committee member can submit suggestions to staff
//A camp committee member can view, edit and delete the details of his/her suggestions before being processed
//A camp committee member can get one point for each suggestion given. One extra point for each accepted suggestion

//Enquiry.csv:
//Suggestion,CampCommitteeMember,Camp,Read,Review

public class SuggestionController {
    private static final String FILE_PATH = "src/Database/Suggestion.csv";
    private static final String CSV_SEPARATOR = ",";

    private String suggestion;
    private String campCommitteeMember;
    private String camp;
    //private String staff;
    private String read;
    private String review; //Either "Accepted" or "Rejected"

    //IMPLEMENTATIONS FOR CAMP COMMITTEE MEMBER
    public void createSuggestion(String campCommitteeMember){
        read = " ";
        review = " ";

        Scanner sc = new Scanner(System.in);

        System.out.println("Select the camp to send the suggestion to: ");
        camp = sc.nextLine();
        
        System.out.println("Type in your suggestion: ");
        suggestion = sc.nextLine();

        String data = String.join(CSV_SEPARATOR,suggestion, campCommitteeMember, camp,read,review);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            //writer.newLine();
            writer.write(data);
            writer.newLine();
            
        } catch (IOException e) {
            System.out.println("Error uploading the suggestion");
            e.printStackTrace();
        }
    }

    public String[] studentFindSuggestion(String campCommitteeMember){
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[1].equalsIgnoreCase(campCommitteeMember)){
                    return data;
                }
            }
            System.out.println("Cannot find your suggestion. Have you submitted a suggestion?");
            return null;
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return null;
    }

    public void viewSuggestion(String campCommitteeMember){
        String[] data;
        if(studentFindSuggestion(campCommitteeMember)!=null){
            data = studentFindSuggestion(campCommitteeMember);
            formatMessage(data);
        }
        else
            System.out.println("No Suggestions to View");
    }

    public void editSuggestion(String campCommitteeMember){ //Look for the enquiry based on the student's name, assuming a student can only send one enquiry to one camp
        String[] studentSuggestion = studentFindSuggestion(campCommitteeMember);
        if(studentSuggestion == null){
            System.out.println("No Suggestions to Edit");
            return;
        }
        Scanner sc = new Scanner(System.in);

        if ((studentSuggestion[3].equals(" ")) && (studentSuggestion[4].equals(" "))){
            System.out.println("Edit your enquiry here: ");
            suggestion = sc.nextLine();
            studentSuggestion[0] = suggestion;

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(campCommitteeMember)) {
                        dataList.add(data);
                    }
                    else {
                        dataList.add(studentSuggestion);
                    }
                }
                writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your edit request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff has viewed your suggestion, cannot edit the suggestion.");
        }
        
    }

    public void deleteSuggestion(String campCommitteeMember){
        String[] studentSuggestion = studentFindSuggestion(campCommitteeMember);
        if(studentSuggestion == null){
            System.out.println("No Suggestions to Remove");
            return;
        }
        if ((studentSuggestion[3].equals(" ")) && (studentSuggestion[4].equals(" "))){

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(campCommitteeMember)) {
                        dataList.add(data);
                    }
                }
                writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your delete request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff has viewed your suggestion, cannot delete the suggestion.");
        }
    }

    public void readSuggestion(String campCommitteeMember){
        String[] studentSuggestion;
        if (studentFindSuggestion(campCommitteeMember) != null){
            studentSuggestion = studentFindSuggestion(campCommitteeMember);
        }
        else {
            System.out.println("Cannot find the suggestion");
            return;
        }
        if ((!studentSuggestion[3].equals(" ")) && (!studentSuggestion[4].equals(" "))){
            formatMessage(studentSuggestion);
        }
        else {
            System.out.println("Your suggestion has not been reviewed. Stay tuned!");
        }
    }

    //IMPLEMENTATIONS FOR STAFF
    public String[] checkSuggestion(String camp){
        String[] data = findSuggestion(camp);
        if (data != null){
            System.out.println("There is a suggestion for your camp pending for review.");
            formatMessage(data);
            return data;
        }
        else {
            System.out.println("No pending suggestion");
            return null;
        }
        
    }

    public String[] findSuggestion(String camp){
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[2].equalsIgnoreCase(camp)){
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        System.out.println("Cannot find the suggestion for " + camp);
        return null;
    }

    public void reviewSuggestion(String camp){
        String[] suggestionToReview = findSuggestion(camp);

        if ((!suggestionToReview[3].equals(" ")) && (!suggestionToReview[4].equals(" "))){
            System.out.println("The suggestion has already been reviewed!");
            return;
        } 
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose to accept/reject this suggestion: \n1.Accept\n2.Reject"); 
        int choice = sc.nextInt();
        String decision = new String();
        switch (choice) {
            case 1:
                decision = "Accepted";
            case 2: 
                decision = "Rejected";
        }
        suggestionToReview[3] = "Read";
        suggestionToReview[4] = decision;
        System.out.println("Decision has been saved!");
        //sc.close();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if ((!data[2].equalsIgnoreCase(suggestionToReview[2])) && (!data[1].equals(suggestionToReview[1]))) { //cross-validate the student name and camp
                        dataList.add(data);
                    }
                    else {
                        dataList.add(suggestionToReview);
                    }
                }
                writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your decision request.");
            e.printStackTrace();
        }   
    }

    public List<String[]> execFindSuggestion(String camp){
        List<String[]> suggestionList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[2] == camp){
                    suggestionList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return suggestionList;
    }

    public void viewReviewToSuggestion(String camp){
        List<String[]> suggestionList = execFindSuggestion(camp);
        if (!suggestionList.isEmpty()){
            for (String[] i : suggestionList){
                formatMessage(i);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        else System.out.println("No visible suggestions.");       
    }

    //IMPLEMENTATIONS FOR ALL
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("From: "+data[1]);
            System.out.println("To "+data[2] + " Staff");
            System.out.println("--------------------------------");
            if (Objects.equals(data[3]," ")){
                System.out.println("Read Status: Not read || Review Status: Not reviewed");
            }
            else {
                System.out.println("Read Status: Read || Review Status: " + data[4]);
                
            }
            System.out.println("--------------------------------");
            System.out.println("Suggestion: " + data[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough data");
        }
    }

    public void writeCSV(List<String[]> dataList){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    for (String[] i : dataList){
                        String writeLine = String.join(CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }
}
