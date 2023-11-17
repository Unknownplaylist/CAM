package CampAccess;

import java.util.*;
//import java.util.Scanner;

import Controllers.*;
import Models.*;

public class CommitteeAccess {
    static Scanner sc = new Scanner(System.in);

    private SuggestionController suggestion_controller;
    private EnquiryController enquiry_controller;
    private StudentsController student_controller;

    public CommitteeAccess(SuggestionController suggestion_controller, EnquiryController enquiry_controller,
        StudentsController student_controller){
        this.suggestion_controller = suggestion_controller;
        this.enquiry_controller = enquiry_controller;
        this.student_controller = student_controller;
    }
    
    public void viewCamps(){

    }

    public void viewYourCamps(){ //View the list of camps that he/she is in

    }
        
    public void viewCampDetails(){ //View details of the camp he/she has registered for

    }

    //SuggestionController
    public void submitSuggestion(String campCommName){ 
        suggestion_controller.createSuggestion(campCommName);
    }
    public void viewSuggestion(String campCommName){
        suggestion_controller.viewSuggestion(campCommName);
    }
    public void editSuggestion(String campCommName){
        suggestion_controller.editSuggestion(campCommName);

    }
    public void deleteSuggestion(String campCommName){//to extend the suggestion controller to take in camp
        suggestion_controller.editSuggestion(campCommName);
    }

    //EnquiryController
    public void checkEnquiry(String camp){
        //get his camp
        List<String[]> unrepliedEnquiriesList = enquiry_controller.execFindUnrepliedEnquiry(camp);
        enquiry_controller.formatMessageList(unrepliedEnquiriesList);
    }
    public void viewEnquiry(String camp){
        enquiry_controller.viewReplyToEnquiry(camp);
    }
    public void replyEnquiry(String camp){
        enquiry_controller.execReplyEnquiry(camp);
    }

    //Take from which controller?
    public void generateStudentList(){
        //generate a list of students attending each camp they oversee
        //Include details of the camp as well as the roles of participants
        //Should be filters for how the camp committee member would want to generate the list (attendee, camp committee, etc.)
    }
}
