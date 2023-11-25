package CampAccess.StudentAccessManagement;

import java.util.List;
import java.util.stream.Collectors;

import CampAccess.StudentAccessManagementInterface.StudentCampViewServiceInterface;
import Models.Camp;
import Models.Student;

public class StudentCampViewService implements StudentCampViewServiceInterface {

    /**
     * Displays the available camps for a student based on their preferred view.
     * 
     * @param studentAccess The StudentAccess object.
     * @param studentEmail The email of the student.
     */
    @Override
    public void viewAvailableCamps(StudentAccess studentAccess, String studentEmail) {
        Student student = studentAccess.studentsController.studentSearchService.getStudentByEmail(studentAccess.studentsController, studentEmail);
    
        int choice;
        System.out.println("How do you want to view the Camps?");
        System.out.println("(1) By Name\n(2) By Starting Date\n(3) By Ending Date\n(4) By Location");
        System.out.print("Enter your preferred view (by number): ");
        try {
            choice = StudentAccess.sc.nextInt();
            System.out.println();
        } catch (Exception e) {
            System.out.println("Invalid input - Redirecting you to Menu");
            return;
        }
        switch (choice) {
            case 1:
                studentAccess.campController.campSortingService.sortCampsAlphabeticallyAndWrite(studentAccess.campController);
                break;
            case 2:
                studentAccess.campController.campSortingService.sortCampsByStartDateAndWrite(studentAccess.campController);
                break;
            case 3:
                studentAccess.campController.campSortingService.sortCampsByEndDateAndWrite(studentAccess.campController);
                break;
            case 4:
                studentAccess.campController.campSortingService.sortCampsLocationAndWrite(studentAccess.campController);
                break;
            default:
                System.out.println("Invalid input - Redirecting you to Menu");
                return;
        }
    
        String studentFaculty = student.getFaculty();
        List<Camp> availableCamps = studentAccess.campController.campService.viewAllCamps(studentAccess.campController).stream()
                .filter(camp -> camp.getFaculty().equalsIgnoreCase(studentFaculty) && camp.isVisible())
                .collect(Collectors.toList());
    
        if (availableCamps.isEmpty()) {
            System.out.println("No camps available for your faculty.");
        } else {
            availableCamps.forEach(camp -> System.out.println(camp+"\n"));
        }
    }

    /**
     * Retrieves and displays the camps that a student is registered for or is a committee member of.
     * 
     * @param studentAccess The StudentAccess object used to access the necessary controllers and services.
     * @param studentEmail The email of the student whose camps are to be viewed.
     */
    @Override
    public void viewMyCamps(StudentAccess studentAccess, String studentEmail) {
        Student student = studentAccess.studentsController.studentSearchService.getStudentByEmail(studentAccess.studentsController, studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
    
        String studentName = student.getName(); // Retrieve the name of the logged-in student
    
        List<Camp> myCamps = studentAccess.campController.campService.viewAllCamps(studentAccess.campController).stream()
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
