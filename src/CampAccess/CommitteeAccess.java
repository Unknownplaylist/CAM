package CampAccess;

import java.util.*;
//import java.util.Scanner;
import java.util.stream.Collectors;

import Controllers.*;
import Models.*;

public class CommitteeAccess {
    static Scanner sc = new Scanner(System.in);

    private SuggestionController suggestion_controller;
    private EnquiryController enquiry_controller;
    private StudentsController student_controller;
    private StaffController staff_controller;
    private CampController camp_controller;

    public CommitteeAccess(SuggestionController suggestionController, EnquiryController enquiryController,
            StudentsController studentController, StaffController staffController, CampController campController) {
        this.suggestion_controller = suggestionController;
        this.enquiry_controller = enquiryController;
        this.student_controller = studentController;
        this.staff_controller = staffController;
        this.camp_controller = campController;
    }

    public void viewCamps() {

    }

    public void viewYourCamps() {

    }

    public void viewCampDetails() {

    }

    // SuggestionController
    public void submitSuggestion(String campCommName) {
        Camp camp = camp_controller.getCampByCommitteeMember(campCommName);
        String campName = camp.getCampName();
        suggestion_controller.createSuggestion(campCommName);
    }

    public void viewSuggestion(String campCommName) {
        suggestion_controller.viewSuggestion(campCommName);
    }

    public void editSuggestion(String campCommName) {
        suggestion_controller.editSuggestion(campCommName);
    }

    public void deleteSuggestion(String campCommName) {// to extend the suggestion controller to take in camp
        suggestion_controller.deleteSuggestion(campCommName);
    }

    // EnquiryController
    public void checkEnquiry(String camp) {
        // get his camp
        List<String[]> unrepliedEnquiriesList = enquiry_controller.execFindUnrepliedEnquiry(camp);
        enquiry_controller.formatMessageList(unrepliedEnquiriesList);
    }

    public void viewEnquiry(String camp) {
        enquiry_controller.viewReplyToEnquiry(camp);
    }

    public void replyEnquiry(String camp) {
        enquiry_controller.execReplyEnquiry(camp);
    }

    public void generateStudentList(String studentEmail) {
        // Assuming 'getStudentByEmail' method exists in StudentsController
        Student committeeMember = student_controller.getStudentByEmail(studentEmail);
        if (committeeMember == null) {
            System.out.println("Student not found.");
            return;
        }

        Camp camp = camp_controller.getCampByCommitteeMember(committeeMember.getName());
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

    
    public void viewAvailableCamps(String studentEmail) {
        Student student = student_controller.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = camp_controller.viewAllCamps().stream()
                .filter(camp -> camp.getFaculty().equalsIgnoreCase(studentFaculty) && camp.isVisible())
                .collect(Collectors.toList());

        if (availableCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            availableCamps.forEach(camp -> System.out.println(camp));
        }


    }

    public void viewMyCamps(String studentEmail) {
        Student student = student_controller.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
    
        String studentName = student.getName(); // Retrieve the name of the logged-in student
    
        List<Camp> myCamps = camp_controller.viewAllCamps().stream()
                .filter(camp -> {
                    List<Student> registeredStudents = camp.getRegisteredStudents();
                    List<Student> committeeMembers = camp.getCommitteeMembers();
                    boolean isRegistered = registeredStudents != null && registeredStudents.stream()
                                        .anyMatch(registeredStudent -> registeredStudent != null &&
                                                registeredStudent.getName().equalsIgnoreCase(studentName));
                    boolean isCommitteeMember = committeeMembers != null && committeeMembers.stream()
                                        .anyMatch(committeeMember -> committeeMember != null &&
                                                committeeMember.getName().equalsIgnoreCase(studentName));
                    return isRegistered || isCommitteeMember;
                })
                .collect(Collectors.toList());
    
        if (myCamps.isEmpty()) {
            System.out.println("You are not registered for any camps.");
        } else {
            System.out.println("Registered Camps:");
            myCamps.forEach(camp -> {
                System.out.println(camp.getCampName() + " - " + (camp.getCommitteeMembers().stream()
                    .anyMatch(committeeMember -> committeeMember != null && 
                        committeeMember.getName().equalsIgnoreCase(studentName)) ? "Committee Member" : "Attendee"));
            });
        }
    }
    

}
