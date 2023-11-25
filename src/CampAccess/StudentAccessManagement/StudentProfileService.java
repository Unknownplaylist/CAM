package CampAccess.StudentAccessManagement;

import CampAccess.StudentAccessManagementInterface.StudentProfileServiceInterface;
import Models.Student;

public class StudentProfileService implements StudentProfileServiceInterface {

    /**
     * Updates the profile of a student.
     *
     * @param studentAccess The StudentAccess object used to access the student data.
     * @param email The email of the student whose profile needs to be updated.
     * @param newName The new name for the student.
     * @param newFaculty The new faculty for the student.
     */
    @Override
    public void updateStudentProfile(StudentAccess studentAccess, String email, String newName, String newFaculty) {
        Student student = studentAccess.studentsController.studentSearchService.getStudentByEmail(studentAccess.studentsController, email);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
    
        Student updatedStudent = new Student(newName, email, newFaculty, student.getRole(), student.getPassword(),
                student.getCampsWithdrawn());
        studentAccess.studentsController.studentService.updateStudent(studentAccess.studentsController, email, updatedStudent);
        System.out.println("Profile updated successfully.");
    }
    
}
