package CampAccess;

import java.util.*;
//import java.util.Scanner;
import java.util.stream.Collectors;

import Controllers.*;
import Controllers.CampEnquiryManagement.EnquiryController;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Controllers.CampSuggestionManagement.SuggestionController;
import Models.*;

public class CommitteeAccess {
    static Scanner sc = new Scanner(System.in);

    private SuggestionController suggestion_controller;
    private EnquiryController enquiry_controller;
    private StudentsController student_controller;
    private StaffController staff_controller;
    private CampController camp_controller;

    private Student student;

    public CommitteeAccess(SuggestionController suggestionController, EnquiryController enquiryController,
            StudentsController studentController, StaffController staffController, CampController campController, String campCommName) {
        this.suggestion_controller = suggestionController;
        this.enquiry_controller = enquiryController;
        this.student_controller = studentController;
        this.staff_controller = staffController;
        this.camp_controller = campController;
        this.student = student_controller.studentSearchService.getStudentByName(student_controller, campCommName);
    }

    //CampController
    public void viewAvailableCamps(String studentEmail) {
        Student student = student_controller.studentSearchService.getStudentByEmail(student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = camp_controller.campService.viewAllCamps(camp_controller).stream()
                .filter(camp -> camp.getFaculty().equalsIgnoreCase(studentFaculty) && camp.isVisible())
                .collect(Collectors.toList());

        if (availableCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            System.out.println("\nList of Camps available for your Faculty");
            System.out.println("----------------------------");
            // availableCamps.forEach(camp -> System.out.println(camp));
            for (Camp camp : availableCamps){
                System.out.println(camp);
                System.out.println("----------------------------");
            }
        }
    }

    public void viewMyCamps(String studentName) {
        //Student student = student_controller.getStudentByEmail(studentEmail);
        if (studentName == null) {
            System.out.println("Student not found.");
            return;
        }   
        //String studentName = student.getName(); // Retrieve the name of the logged-in student    
        List<Camp> myCamps = camp_controller.campService.viewAllCamps(camp_controller).stream()
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

    // SuggestionController
    public void submitSuggestion(String campCommName) { //need to retrieve the camp name when creating the suggestion
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        suggestion_controller.suggestionService.createSuggestion(suggestion_controller, campCommName, campName);
    }
    //a student can only be a camp committee member in one camp
    //assume that a camp committee member can only send one suggestion
    public void viewSuggestion(String campCommName) {
        suggestion_controller.suggestionViewController.viewSuggestion(suggestion_controller, campCommName);
    }

    public void editSuggestion(String campCommName) {
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        suggestion_controller.suggestionService.editSuggestion(suggestion_controller, campCommName, campName);
    }

    public void deleteSuggestion(String campCommName) {// to extend the suggestion controller to take in camp
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        suggestion_controller.suggestionService.deleteSuggestion(suggestion_controller, campCommName, campName);
    }

    // EnquiryController
    public void checkEnquiry(String campCommName) {
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        List<String[]> unrepliedEnquiriesList = enquiry_controller.staffEnquiryService.execFindUnrepliedEnquiry(enquiry_controller, camp.getCampName());
        enquiry_controller.enquiryService.formatMessageList(enquiry_controller, unrepliedEnquiriesList);
    }

    public void viewEnquiry(String campCommName) {
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        enquiry_controller.enquiryService.viewReplyToEnquiry(enquiry_controller, camp.getCampName());
    }

    public void replyEnquiry(String campCommName) {
        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        enquiry_controller.staffEnquiryService.execReplyEnquiry(enquiry_controller, camp.getCampName());
        //add one point for every reply to enquiry
        //Student student = student_controller.getStudentByName(campCommName);
        student.addOnePoint();
    }

    public void generateStudentList(String studentEmail) {
        // Assuming 'getStudentByEmail' method exists in StudentsController
        Student committeeMember = student_controller.studentSearchService.getStudentByEmail(student_controller, studentEmail);
        if (committeeMember == null) {
            System.out.println("Student not found.");
            return;
        }

        Camp camp = camp_controller.campService.getCampByCommitteeMember(camp_controller, committeeMember.getName());
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

    public void viewPoint(String campCommName){
        //Student student = student_controller.getStudentByName(campCommName);
        int point = student.getPoint();
        System.out.println("Your current point is: " + point);
    }

    public void registerForCamp(String studentEmail, String campName, boolean asCommitteeMember) {
        Student student = student_controller.studentSearchService.getStudentByEmail(student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Withdrawn Camps: " + student.getCampsWithdrawn());

        if (student.hasWithdrawnFromCamp(campName)) {
            System.out.println("You cannot register for a camp that you have previously withdrawn from.");
            return;
        }

        Camp camp = camp_controller.campService.getCamp(camp_controller, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }

        if (camp_controller.campService.isStudentRegisteredInCamp(camp_controller, studentEmail, campName)) {
            System.out.println("Student is already registered in this camp.");
            return;
        }

        // Check for date clash
        if (camp_controller.campService.hasDateClash(camp_controller, student, camp)) {
            System.out.println("Cannot register for " + campName + " due to a date clash with another camp.");
            return;
        }

        camp_controller.campRegistrationService.registerStudentForCamp(camp_controller, campName, student, asCommitteeMember);
        // System.out.println("Student successfully registered for " +
        // (asCommitteeMember ? "committee member" : "participant") + " in the camp: " +
        // campName);
    }

    public void withdrawFromCamp(String studentEmail, String campName) {
        // Find the student by email
        Student student = student_controller.studentSearchService.getStudentByEmail(student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found with email: " + studentEmail);
            return;
        }

        // Retrieve the camp by name
        Camp camp = camp_controller.campService.getCamp(camp_controller, campName);
        if (camp == null) {
            System.out.println("Camp not found with name: " + campName);
            return;
        }

        // Call the CampController to handle the withdrawal process
        boolean isWithdrawn = camp_controller.campRegistrationService.withdrawStudentFromAttendees(camp_controller, camp, student);
        if (isWithdrawn) {
            student.addWithdrawnCamp(camp.getCampName());
            student_controller.studentService.updateStudentData(student_controller, student);
            System.out.println("Added to withdrawn list: " + student.getCampsWithdrawn()); // Debugging line

        } else {
            System.out.println("Could not withdraw student from the camp.");
        }
    }

   
}
