package Controllers.StudentManagement;

import java.util.List;

import Controllers.StudentManagementInterface.StudentAuthenticationServiceInterface;
import Models.Student;

public class StudentAuthenticationService implements StudentAuthenticationServiceInterface {

    @Override
    public boolean isFirstLogin(StudentsController studentsController, String email) {
        return studentsController.studentAuthenticationService.checkPassword(studentsController, email, StudentsController.DEFAULT_PASSWORD);
    }

    @Override
    public boolean checkPassword(StudentsController studentsController, String email, String password) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyStudent(StudentsController studentsController, String email) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUserRole(StudentsController studentsController, String email) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getRole();
            }
        }
        return "Role not found"; // or null, depending on your handling
    }
    
}
