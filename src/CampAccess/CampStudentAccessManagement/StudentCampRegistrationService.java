package CampAccess.CampStudentAccessManagement;

import Models.Camp;
import Models.Student;

public class StudentCampRegistrationService {

    public void registerForCamp(StudentAccess studentAccess, String studentEmail, String campName, boolean asCommitteeMember) {
        Student student = studentAccess.studentsController.studentSearchService.getStudentByEmail(studentAccess.studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Withdrawn Camps: " + student.getCampsWithdrawn());
    
        if (student.hasWithdrawnFromCamp(campName)) {
            System.out.println("You cannot register for a camp that you have previously withdrawn from.");
            return;
        }
    
        Camp camp = studentAccess.campController.campService.getCamp(studentAccess.campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        if (studentAccess.campController.campService.isStudentRegisteredInCamp(studentAccess.campController, studentEmail, campName)) {
            System.out.println("Student is already registered in this camp.");
            return;
        }
    
        // Check for date clash
        if (studentAccess.campController.campService.hasDateClash(studentAccess.campController, student, camp)) {
            System.out.println("Cannot register for " + campName + " due to a date clash with another camp.");
            return;
        }
    
        studentAccess.campController.campRegistrationService.registerStudentForCamp(studentAccess.campController, campName, student, asCommitteeMember);
        // System.out.println("Student successfully registered for " +
        // (asCommitteeMember ? "committee member" : "participant") + " in the camp: " +
        // campName);
    }

    public void withdrawFromCamp(StudentAccess studentAccess, String studentEmail, String campName) {
        // Find the student by email
        Student student = studentAccess.studentsController.studentSearchService.getStudentByEmail(studentAccess.studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found with email: " + studentEmail);
            return;
        }
    
        // Retrieve the camp by name
        Camp camp = studentAccess.campController.campService.getCamp(studentAccess.campController, campName);
        if (camp == null) {
            System.out.println("Camp not found with name: " + campName);
            return;
        }
    
        // Call the CampController to handle the withdrawal process
        boolean isWithdrawn = studentAccess.campController.campRegistrationService.withdrawStudentFromAttendees(studentAccess.campController, camp, student);
        if (isWithdrawn) {
            student.addWithdrawnCamp(camp.getCampName());
            studentAccess.studentsController.studentService.updateStudentData(studentAccess.studentsController, student);
            System.out.println("Added to withdrawn list: " + student.getCampsWithdrawn()); // Debugging line
    
        } else {
            System.out.println("Could not withdraw student from the camp.");
        }
    }
    
}
