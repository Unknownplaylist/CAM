package Controllers;

import java.io.*;
import java.util.*;

//A camp committee member can submit suggestions to staff
//A camp committee member can view, edit and delete the details of his/her suggestions before being processed
//A camp committee member can get one point for each suggestion given. One extra point for each accepted suggestion

//Enquiry.csv:
//Suggestion,CampCommitteeMember,Camp,Read,Review

public class SuggestionController {
    static Scanner sc = new Scanner(System.in);
    private static final String FILE_PATH = "src/Database/Suggestion.csv";
    private static final String CSV_SEPARATOR = ",";

    //IMPLEMENTATIONS FOR CAMP COMMITTEE MEMBER
    public List<String[]> findAllSuggestions(){
        List<String[]> suggestionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {            
            String[] data;
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                suggestionList.add(data);
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return suggestionList;
    }   

    public void writeSuggestionToFile(String[] enquiry){
        List<String[]> existingSuggestion = findAllSuggestions();
        existingSuggestion.add(enquiry);
        writeCSV(existingSuggestion);
    }

    public void createSuggestion(String campCommitteeMember, String campName){
        String read = " ";
        String review = " ";
        String ID = " "; //To be assigned later 
        
        System.out.println("Type in your suggestion: ");
        String message = sc.nextLine();

        String data = String.join(CSV_SEPARATOR,message, campCommitteeMember, campName,read,review, ID);
        String[] suggestion = data.split(CSV_SEPARATOR);
        writeSuggestionToFile(suggestion);
    }

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

    public List<String[]> studentFindSuggestion(String campCommitteeMember){
        List<String[]> suggestionList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
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

    public void viewSuggestion(String campCommitteeMember){ //view all suggestions under the camp comm name
        List<String[]> suggestionList = studentFindSuggestion(campCommitteeMember);
        if(!suggestionList.isEmpty()){
            for (String[] suggestion: suggestionList){
                formatMessage(suggestion);
            }
        }
        else
            System.out.println("No Suggestions to View");
    }

    public String[] studentSuggestionBasedOnIDandCamp(String student, String camp){
        List<String[]> studentEnquiryList = studentFindSuggestion(student);
        if(studentEnquiryList.isEmpty()){
            System.out.println("No Suggestions under the student name.");
            return null;
        }
        System.out.print("Type in the Suggestion ID: ");
        String ID  = sc.nextLine();

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

    public void editSuggestion(String campCommitteeMember, String camp){ //Look for the enquiry based on the student's name, assuming a student can only send one enquiry to one camp
        String[] studentSuggestion = studentSuggestionBasedOnIDandCamp(campCommitteeMember, camp);

        if(studentSuggestion == null){
            System.out.println("Cannot proceed with your edit request");
            return;
        }

        if ((studentSuggestion[3].equals(" ")) && (studentSuggestion[4].equals(" "))){
            System.out.println("Edit your suggestion here: ");
            String message = sc.nextLine();
            studentSuggestion[0] = message;

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if ( (data[1].equalsIgnoreCase(studentSuggestion[1]))
                    && (data[5].equalsIgnoreCase(studentSuggestion[5])) ) {
                        dataList.add(studentSuggestion);
                        continue;
                    }
                    dataList.add(data);
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

    public void deleteSuggestion(String campCommitteeMember, String camp){
        String[] studentSuggestion = studentSuggestionBasedOnIDandCamp(campCommitteeMember, camp);

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
                    data = line.split(CSV_SEPARATOR);
                    if ((data[0].equalsIgnoreCase(studentSuggestion[0])) 
                    && (data[1].equalsIgnoreCase(studentSuggestion[1]))
                    && (data[5].equalsIgnoreCase(studentSuggestion[5])) ) {
                        continue;
                    }
                    dataList.add(data);
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

    public void reviewSuggestion(String[] suggestionToReview){
        System.out.println("Choose to accept/reject this suggestion: \n1.Accept\n2.Reject"); 
        int choice = sc.nextInt();
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
                    data = line.split(CSV_SEPARATOR);
                    if ((data[2].equalsIgnoreCase(suggestionToReview[2])) 
                        && (data[1].equalsIgnoreCase(suggestionToReview[1])) 
                        && (data[0].equalsIgnoreCase(suggestionToReview[0]))) { //cross-validate the student name and camp
                        dataList.add(suggestionToReview);
                    }
                    else {
                        dataList.add(data);
                    }
                }
                writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your decision request.");
            e.printStackTrace();
        }   
    }

    public List<String[]> execFindSuggestion(String camp){ //generate the list of all enquiries for that camp, both replied and unreplied
        List<String[]> suggestionList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
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

    public void execReviewSuggestion(String camp){
        System.out.print("Type in the Suggestion ID to reply to: ");
        String ID = sc.nextLine();

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
                reviewSuggestion(suggestionToReply);
            }
        }
        else {
            System.out.println("No enquiry under ID " + ID + " for " + camp);
        }
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

    public void formatMessageList(List<String[]> suggestionList){
        if (suggestionList == null){
            System.out.println("No message to display.");
            return;
        }
        for (String[] i : suggestionList){
            System.out.println("--------------------------------");
            formatMessage(i);
            System.out.println("--------------------------------");
        }
    }

    public void writeCSV(List<String[]> dataList){
        int ID = 1;
        String writeLine;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    for (String[] i : dataList){
                        if (!i[5].equals("ID")){
                            i[5] = Integer.toString(ID++);
                        }                       
                        writeLine = String.join(CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }
}
