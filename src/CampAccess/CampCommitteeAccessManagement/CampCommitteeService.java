package CampAccess.CampCommitteeAccessManagement;

import java.util.List;
import java.util.stream.Collectors;

import CampAccess.CampCommitteeAccessManagementInterface.CampCommitteeServiceInterface;
import Models.Camp;
import Models.Student;

/**
 * The CampCommitteeService class implements the CampCommitteeServiceInterface and provides methods for managing camp committees and registrations.
 */
public class CampCommitteeService implements CampCommitteeServiceInterface {

    @Override
    public void viewAvailableCamps(CommitteeAccess committeeAccess, String studentEmail) {
        Student student = committeeAccess.student_controller.studentSearchService.getStudentByEmail(committeeAccess.student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = committeeAccess.camp_controller.campService.viewAllCamps(committeeAccess.camp_controller).stream()
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

    /**
     * Retrieves and displays the camps that a student is registered for or is a committee member of.
     * 
     * @param committeeAccess The committee access object.
     * @param studentName The name of the student.
     */
    @Override
    public void viewMyCamps(CommitteeAccess committeeAccess, String studentName) {
        //Student student = student_controller.getStudentByEmail(studentEmail);
        if (studentName == null) {
            System.out.println("Student not found.");
            return;
        }   
        //String studentName = student.getName(); // Retrieve the name of the logged-in student    
        List<Camp> myCamps = committeeAccess.camp_controller.campService.viewAllCamps(committeeAccess.camp_controller).stream()
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

    /**
     * Registers a student for a camp.
     *
     * @param committeeAccess The CommitteeAccess object.
     * @param studentEmail The email of the student.
     * @param campName The name of the camp.
     * @param asCommitteeMember Indicates whether the student is registering as a committee member.
     */
    @Override
    public void registerForCamp(CommitteeAccess committeeAccess, String studentEmail, String campName, boolean asCommitteeMember) {
        Student student = committeeAccess.student_controller.studentSearchService.getStudentByEmail(committeeAccess.student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Withdrawn Camps: " + student.getCampsWithdrawn());
    
        if (student.hasWithdrawnFromCamp(campName)) {
            System.out.println("You cannot register for a camp that you have previously withdrawn from.");
            return;
        }
    
        Camp camp = committeeAccess.camp_controller.campService.getCamp(committeeAccess.camp_controller, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        if (committeeAccess.camp_controller.campService.isStudentRegisteredInCamp(committeeAccess.camp_controller, studentEmail, campName)) {
            System.out.println("Student is already registered in this camp.");
            return;
        }
    
        // Check for date clash
        if (committeeAccess.camp_controller.campService.hasDateClash(committeeAccess.camp_controller, student, camp)) {
            System.out.println("Cannot register for " + campName + " due to a date clash with another camp.");
            return;
        }
    
        committeeAccess.camp_controller.campRegistrationService.registerStudentForCamp(committeeAccess.camp_controller, campName, student, asCommitteeMember);
        // System.out.println("Student successfully registered for " +
        // (asCommitteeMember ? "committee member" : "participant") + " in the camp: " +
        // campName);
    }

    /**
     * Withdraws a student from a camp.
     * 
     * @param committeeAccess The CommitteeAccess object.
     * @param studentEmail The email of the student to be withdrawn.
     * @param campName The name of the camp.
     */
    @Override
    public void withdrawFromCamp(CommitteeAccess committeeAccess, String studentEmail, String campName) {
        // Find the student by email
        Student student = committeeAccess.student_controller.studentSearchService.getStudentByEmail(committeeAccess.student_controller, studentEmail);
        if (student == null) {
            System.out.println("Student not found with email: " + studentEmail);
            return;
        }
    
        // Retrieve the camp by name
        Camp camp = committeeAccess.camp_controller.campService.getCamp(committeeAccess.camp_controller, campName);
        if (camp == null) {
            System.out.println("Camp not found with name: " + campName);
            return;
        }
    
        // Call the CampController to handle the withdrawal process
        boolean isWithdrawn = committeeAccess.camp_controller.campRegistrationService.withdrawStudentFromAttendees(committeeAccess.camp_controller, camp, student);
        if (isWithdrawn) {
            student.addWithdrawnCamp(camp.getCampName());
            committeeAccess.student_controller.studentService.updateStudentData(committeeAccess.student_controller, student);
            System.out.println("Added to withdrawn list: " + student.getCampsWithdrawn()); // Debugging line
    
        } else {
            System.out.println("Could not withdraw student from the camp.");
        }
    }
    
}
