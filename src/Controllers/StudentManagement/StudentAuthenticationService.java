package Controllers.StudentManagement;

import java.util.List;

import Controllers.StudentManagementInterface.StudentAuthenticationServiceInterface;
import Models.Student;

public class StudentAuthenticationService implements StudentAuthenticationServiceInterface {

    /**
     * Checks if the student with the given email is logging in for the first time.
     *
     * @param studentsController The StudentsController instance.
     * @param email The email of the student.
     * @return true if it is the first login, false otherwise.
     */
    @Override
    public boolean isFirstLogin(StudentsController studentsController, String email) {
        return studentsController.studentAuthenticationService.checkPassword(studentsController, email, StudentsController.DEFAULT_PASSWORD);
    }

    /**
     * Checks if the provided email and password match any student's credentials.
     * 
     * @param studentsController The controller for managing students.
     * @param email The email to be checked.
     * @param password The password to be checked.
     * @return true if the email and password match a student's credentials, false otherwise.
     */
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

    /**
     * Verifies if a student with the given email exists in the system.
     * 
     * @param studentsController The controller for managing students.
     * @param email The email of the student to verify.
     * @return true if a student with the given email exists, false otherwise.
     */
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

    /**
     * Retrieves the role of a user based on their email.
     * 
     * @param studentsController The StudentsController instance.
     * @param email The email of the user.
     * @return The role of the user if found, otherwise "Role not found".
     */
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
