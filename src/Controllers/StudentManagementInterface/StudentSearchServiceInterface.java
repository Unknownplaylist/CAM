package Controllers.StudentManagementInterface;

import Controllers.StudentManagement.StudentsController;
import Models.Student;

public interface StudentSearchServiceInterface {

    Student getStudentByEmail(StudentsController studentsController, String email);

    Student getStudentByName(StudentsController studentsController, String name);

    String getStudentName(StudentsController studentsController, String email);

    String getStudentFaculty(StudentsController studentsController, String email);

    String getStudentMail(String id);

}