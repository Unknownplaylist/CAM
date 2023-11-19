package Controllers.StudentManagementInterface;

import java.util.List;

import Models.Student;

public interface StudentFileHandlerInterface {

    List<Student> readStudents();

    void writeStudent(Student student);

    void updateStudentList(List<Student> students);

}