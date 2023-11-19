package Controllers.StudentManagementInterface;

import Controllers.StudentManagement.StudentsController;

public interface StudentAuthenticationServiceInterface {

    boolean isFirstLogin(StudentsController studentsController, String email);

    boolean checkPassword(StudentsController studentsController, String email, String password);

    boolean verifyStudent(StudentsController studentsController, String email);

    String getUserRole(StudentsController studentsController, String email);

}