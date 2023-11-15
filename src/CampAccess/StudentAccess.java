package CampAccess;
import Controllers.*;
import Models.*;



public class StudentAccess {
    private StudentsController studentsController;
    private CampController campController;

    public StudentAccess(StudentsController studentsController, CampController campController) {
        this.studentsController = studentsController;
        this.campController = campController;
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

        campController.viewAllCamps().stream()
                .filter(camp -> camp.getUserGroup().equals(studentFaculty))
                .forEach(camp -> System.out.println(camp));
    }

    // Method to withdraw from a camp
    public void withdrawFromCamp(String studentEmail, String campName) {
        Student student = studentsController.getStudentByEmail(studentEmail);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        Camp camp = campController.getCamp(campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        campController.withdrawStudentFromCamp(camp, student);
    }

    // Method to update student profile
    public void updateStudentProfile(String email, String newName, String newFaculty) {
        Student updatedStudent = new Student(newName, email, newFaculty, newFaculty, newFaculty); // Add parameters as needed
        studentsController.updateStudent(email, updatedStudent);
    }

    
}

