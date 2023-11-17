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

    public void viewYourCamps(){

    }
        
    public void viewCampDetails(){

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
    public void deleteSuggestion(String studentEmail){//to extend the suggestion controller to take in camp
        String studentName = student_controller.getStudentName(studentEmail);
        suggestion_controller.editSuggestion(studentName);
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



    private CampController campController;
    private StudentsController studentController;

    public CommitteeAccess(SuggestionController suggestionController, EnquiryController enquiryController,
                           StudentsController studentController, CampController campController){
        this.suggestion_controller = suggestionController;
        this.enquiry_controller = enquiryController;
        this.student_controller = studentController;
        this.campController = campController;
    }
    public void generateStudentList(String studentEmail) {
        // Assuming 'getStudentByEmail' method exists in StudentsController
        Student committeeMember = studentController.getStudentByEmail(studentEmail);
        if (committeeMember == null) {
            System.out.println("Student not found.");
            return;
        }

        
        Camp camp = campController.getCampByCommitteeMember(committeeMember.getName());
        if (camp == null) {
            System.out.println(committeeMember.getName() + " is not a committee member of any camp.");
            return;
        }

        System.out.println("Camp Name: " + camp.getCampName());
        System.out.println("Camp Location: " + camp.getLocation());
        System.out.println("Camp Dates: " + camp.getStartDate() + " to " + camp.getEndDate());
        System.out.println("Registered Students:");
        for (Student student : camp.getRegisteredStudents()) {
            System.out.println(" - " + student.getName() + " (Role: Participant)");
        }
        System.out.println("Committee Members:");
        for (Student committeeMemberInCamp : camp.getCommitteeMembers()) {
            System.out.println(" - " + committeeMemberInCamp.getName() + " (Role: Committee Member)");
        }
        System.out.println();
    }
}
