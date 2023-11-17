package CampAccess;

import java.util.List;
import java.util.stream.Collectors;

import Controllers.*;
import Models.*;

public class StudentAccess {
    private StudentsController studentsController;
    private CampController campController;
    private StaffController staffController;

    public StudentAccess(StudentsController studentsController, CampController campController,
            StaffController staffController) {
        this.studentsController = studentsController;
        this.campController = campController;
        this.staffController = staffController;
    }

    // Method to register a student for a camp
    public void registerForCamp(String studentEmail, String campName, boolean asCommitteeMember) {
        Student student = studentsController.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        campController.registerStudentForCamp(campName, student, asCommitteeMember);
    }

    // Method to view available camps for a student's faculty
    public void viewAvailableCamps(String studentEmail) {
        Student student = studentsController.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = campController.viewAllCamps().stream()
                .filter(camp -> camp.getFaculty().equalsIgnoreCase(studentFaculty) && camp.isVisible())
                .collect(Collectors.toList());

        if (availableCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            availableCamps.forEach(camp -> System.out.println(camp));
        }
    }

    public void withdrawFromCamp(String studentEmail, String campName) {
        // Find the student by email
        Student student = studentsController.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found with email: " + studentEmail);
            return;
        }

        // Retrieve the camp by name
        Camp camp = campController.getCamp(campName);
        if (camp == null) {
            System.out.println("Camp not found with name: " + campName);
            return;
        }

        // Call the CampController to handle the withdrawal process
        campController.withdrawStudentFromAttendees(camp, student);
    }

    // Method to update student profile
    public void updateStudentProfile(String email, String newName, String newFaculty) {
        Student student = studentsController.getStudentByEmail(email);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        Student updatedStudent = new Student(newName, email, newFaculty, student.getRole(), student.getPassword());
        studentsController.updateStudent(email, updatedStudent);
        System.out.println("Profile updated successfully.");
    }

    public void viewMyCamps(String studentEmail) {
        Student student = studentsController.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
    
        String studentName = student.getName(); // Retrieve the name of the logged-in student
    
        List<Camp> myCamps = campController.viewAllCamps().stream()
                .filter(camp -> {
                    List<Student> registeredStudents = camp.getRegisteredStudents();
                    return registeredStudents != null && registeredStudents.stream()
                            .anyMatch(registeredStudent -> registeredStudent != null &&
                                    registeredStudent.getName().equalsIgnoreCase(studentName));
                })
                .collect(Collectors.toList());
    
        if (myCamps.isEmpty()) {
            System.out.println("You are not registered for any camps.");
        } else {
            System.out.println("Registered Camps:");
            myCamps.forEach(camp -> System.out.println(camp.getCampName())); // Print the name of the camp
        }
    }
    

}
