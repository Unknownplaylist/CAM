package CampAccess;

import java.util.Scanner;

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
    

    public void viewCampDetails(){
    }

    //SuggestionController
    public void submitSuggestion(String studentEmail){ 
        String studentName = student_controller.getStudentName(studentEmail);
        suggestion_controller.createSuggestion(studentName);
    }
    public void viewSuggestion(String studentEmail){
        String studentName = student_controller.getStudentName(studentEmail);
        suggestion_controller.viewSuggestion(studentName);
    }
    public void editSuggestion(String studentEmail){
        String studentName = student_controller.getStudentName(studentEmail);
        suggestion_controller.editSuggestion(studentName);

    }
    public void deleteSuggestion(String studentEmail){//to extend the suggestion controller to take in camp
        String studentName = student_controller.getStudentName(studentEmail);
        suggestion_controller.editSuggestion(studentName);
    }

    //EnquiryController
    public void checkEnquiry(){

    }
    public void viewEnquiry(String studentEmail){
        String studentName = student_controller.getStudentName(studentEmail);
        enquiry_controller.viewEnquiry(studentName);

    }
    public void replyEnquiry(String studentEmail){
        String studentName = student_controller.getStudentName(studentEmail);
        enquiry_controller.replyEnquiry(studentName);
    }

    //Take from which controller?
    public void generateStudentList(){
        //generate a list of students attending each camp they oversee
        //Include details of the camp as well as the roles of participants
        //Should be filters for how the camp committee member would want to generate the list (attendee, camp committee, etc.)
    }
}
