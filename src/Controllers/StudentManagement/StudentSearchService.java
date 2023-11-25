package Controllers.StudentManagement;

import java.util.List;

import Controllers.StudentManagementInterface.StudentSearchServiceInterface;
import Models.Student;

public class StudentSearchService implements StudentSearchServiceInterface {

    /**
     * Retrieves a student object by their email address.
     *
     * @param studentsController The controller responsible for managing students.
     * @param email The email address of the student to search for.
     * @return The student object with the specified email, or null if not found.
     */
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

    /**
        * Retrieves a student by their name.
        *
        * @param studentsController The controller for managing students.
        * @param name The name of the student to search for.
        * @return The student with the given name, or null if no student is found.
        */
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

    /**
        * Retrieves the name of a student based on their email.
        *
        * @param studentsController the controller for managing students
        * @param email the email of the student to search for
        * @return the name of the student if found, otherwise "Student not found"
        */
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

    /**
     * Retrieves the faculty of a student based on their email.
     *
     * @param studentsController the controller for managing students
     * @param email the email of the student to search for
     * @return the faculty of the student if found, otherwise "Student not found"
     */
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

    /**
        * Returns the email address of a student based on their ID.
        *
        * @param id the ID of the student
        * @return the email address of the student
        */
    @Override
    public String getStudentMail(String id) { // can remove this function later
        return (id + "@e.ntu.edu.sg");
    }
    
}
