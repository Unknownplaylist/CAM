package CampAccess;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Models.*;

public class StudentAccess {
    private StudentsController studentsController;
    private CampController campController;
    private StaffController staffController;
    static Scanner sc = new Scanner(System.in);

    public StudentAccess(StudentsController studentsController, CampController campController,
            StaffController staffController) {
        this.studentsController = studentsController;
        this.campController = campController;
        this.staffController = staffController;
    }

    // Method to register a student for a camp
    public void registerForCamp(String studentEmail, String campName, boolean asCommitteeMember) {
        Student student = studentsController.studentSearchService.getStudentByEmail(studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Withdrawn Camps: " + student.getCampsWithdrawn());

        if (student.hasWithdrawnFromCamp(campName)) {
            System.out.println("You cannot register for a camp that you have previously withdrawn from.");
            return;
        }

        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }

        if (campController.campService.isStudentRegisteredInCamp(campController, studentEmail, campName)) {
            System.out.println("Student is already registered in this camp.");
            return;
        }

        // Check for date clash
        if (campController.campService.hasDateClash(campController, student, camp)) {
            System.out.println("Cannot register for " + campName + " due to a date clash with another camp.");
            return;
        }

        campController.campRegistrationService.registerStudentForCamp(campController, campName, student, asCommitteeMember);
        // System.out.println("Student successfully registered for " +
        // (asCommitteeMember ? "committee member" : "participant") + " in the camp: " +
        // campName);
    }

    // Method to view available camps for a student's faculty
    public void viewAvailableCamps(String studentEmail) {
        Student student = studentsController.studentSearchService.getStudentByEmail(studentsController, studentEmail);

        int choice;
        System.out.println("How do you want to view the Camps?");
        System.out.println("(1) By Name\n(2) By Starting Date\n(3) By Ending Date\n(4) By Location");
        System.out.print("Enter your preferred view (by number): ");
        try {
            choice = sc.nextInt();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Invalid input - Redirecting you to Menu");
            return;
        }
        switch (choice) {
            case 1:
                campController.campSortingService.sortCampsAlphabeticallyAndWrite(campController);
                break;
            case 2:
                campController.campSortingService.sortCampsByStartDateAndWrite(campController);
                break;
            case 3:
                campController.campSortingService.sortCampsByEndDateAndWrite(campController);
                break;
            case 4:
                campController.campSortingService.sortCampsLocationAndWrite(campController);
                break;
            default:
                System.out.println("Invalid input - Redirecting you to Menu");
                return;
        }

        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = campController.campService.viewAllCamps(campController).stream()
                .filter(camp -> camp.getFaculty().equalsIgnoreCase(studentFaculty) && camp.isVisible())
                .collect(Collectors.toList());

        if (availableCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            availableCamps.forEach(camp -> System.out.println(camp+"\n"));
        }
    }

    public void withdrawFromCamp(String studentEmail, String campName) {
        // Find the student by email
        Student student = studentsController.studentSearchService.getStudentByEmail(studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found with email: " + studentEmail);
            return;
        }

        // Retrieve the camp by name
        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found with name: " + campName);
            return;
        }

        // Call the CampController to handle the withdrawal process
        boolean isWithdrawn = campController.campRegistrationService.withdrawStudentFromAttendees(campController, camp, student);
        if (isWithdrawn) {
            student.addWithdrawnCamp(camp.getCampName());
            studentsController.studentService.updateStudentData(studentsController, student);
            System.out.println("Added to withdrawn list: " + student.getCampsWithdrawn()); // Debugging line

        } else {
            System.out.println("Could not withdraw student from the camp.");
        }
    }

    // Method to update student profile
    public void updateStudentProfile(String email, String newName, String newFaculty) {
        Student student = studentsController.studentSearchService.getStudentByEmail(studentsController, email);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        Student updatedStudent = new Student(newName, email, newFaculty, student.getRole(), student.getPassword(),
                student.getCampsWithdrawn());
        studentsController.studentService.updateStudent(studentsController, email, updatedStudent);
        System.out.println("Profile updated successfully.");
    }

    public void viewMyCamps(String studentEmail) {
        Student student = studentsController.studentSearchService.getStudentByEmail(studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String studentName = student.getName(); // Retrieve the name of the logged-in student

        List<Camp> myCamps = campController.campService.viewAllCamps(campController).stream()
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
                                committeeMember.getName().equalsIgnoreCase(studentName)) ? "Committee Member"
                                        : "Attendee"));
            });
        }
    }

}
