package Controllers.StudentManagement;

import java.util.List;

import Controllers.StudentManagementInterface.StudentSearchServiceInterface;
import Models.Student;

public class StudentSearchService implements StudentSearchServiceInterface {

    @Override
    public Student getStudentByEmail(StudentsController studentsController, String email) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null; // Student not found
    }

    @Override
    public Student getStudentByName(StudentsController studentsController, String name) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null; // Return null if no student with the given name is found
    }

    @Override
    public String getStudentName(StudentsController studentsController, String email) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getName();
            }
        }
        return "Student not found";
    }

    @Override
    public String getStudentFaculty(StudentsController studentsController, String email) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getFaculty();
            }
        }
        return "Student not found";
    }

    @Override
    public String getStudentMail(String id) { // can remove this function later
        return (id + "@e.ntu.edu.sg");
    }
    
}
