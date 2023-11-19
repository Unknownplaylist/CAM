package Controllers.StudentManagementInterface;

import Controllers.StudentManagement.StudentsController;
import Models.Student;

public interface StudentServiceInterface {

    void updateStudentData(StudentsController studentsController, Student updatedStudent);

    void updateStudent(StudentsController studentsController, String email, Student updatedStudent);

    void changePassword(StudentsController studentsController, String email, String newPassword);

    boolean setStudentRole(StudentsController studentsController, String email, String role);

}