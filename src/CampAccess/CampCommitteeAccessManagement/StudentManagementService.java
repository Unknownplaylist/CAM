package CampAccess.CampCommitteeAccessManagement;

import CampAccess.CampCommitteeAccessManagementInterface.StudentManagementServiceInterface;
import Models.Camp;
import Models.Student;

public class StudentManagementService implements StudentManagementServiceInterface {

    /**
     * Generates a student list for a given committee access and student email.
     * 
     * @param committeeAccess The committee access object.
     * @param studentEmail The email of the student.
     */
    @Override
    public void generateStudentList(CommitteeAccess committeeAccess, String studentEmail) {
        // Assuming 'getStudentByEmail' method exists in StudentsController
        Student committeeMember = committeeAccess.student_controller.studentSearchService.getStudentByEmail(committeeAccess.student_controller, studentEmail);
        if (committeeMember == null) {
            System.out.println("Student not found.");
            return;
        }
    
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, committeeMember.getName());
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

    /**
     * Displays the current point of a student in the committee access system.
     * 
     * @param committeeAccess The committee access object containing the student information.
     * @param campCommName The name of the student.
     */
    @Override
    public void viewPoint(CommitteeAccess committeeAccess, String campCommName){
        //Student student = student_controller.getStudentByName(campCommName);
        int point = committeeAccess.student.getPoint();
        System.out.println("Your current point is: " + point);
    }
    
}
