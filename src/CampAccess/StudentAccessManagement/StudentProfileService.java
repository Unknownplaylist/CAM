package CampAccess.StudentAccessManagement;

import CampAccess.StudentAccessManagementInterface.StudentProfileServiceInterface;
import Models.Student;

public class StudentProfileService implements StudentProfileServiceInterface {

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
