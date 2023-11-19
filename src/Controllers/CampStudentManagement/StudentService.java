package Controllers.CampStudentManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Models.Student;

public class StudentService {
    static final String FILE_PATH = "src/Database/student.csv";

    public void updateStudentData(StudentsController studentsController, Student updatedStudent) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        boolean studentExists = false;
    
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getEmail().equalsIgnoreCase(updatedStudent.getEmail())) {
                // Replace the old student data with the updated data
                students.set(i, updatedStudent);
                studentExists = true;
                break;
            }
        }
    
        if (studentExists) {
            // Write the updated list back to the CSV file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Student student : students) {
                    String campsWithdrawnString = String.join(";", student.getCampsWithdrawn());
                    String data = String.join(StudentsController.CSV_SEPARATOR, student.getName(), student.getEmail(),
                                              student.getFaculty(), student.getRole(), student.getPassword(),
                                              campsWithdrawnString);
                    bw.write(data);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing student file");
                e.printStackTrace();
            }
        } else {
            System.out.println("Student with email " + updatedStudent.getEmail() + " not found");
        }
    }

    public void updateStudent(StudentsController studentsController, String email, Student updatedStudent) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        boolean studentExists = false;
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            if (student.getEmail().equalsIgnoreCase(email)) {
                student.setName(updatedStudent.getName());
                student.setFaculty(updatedStudent.getFaculty());
                student.setRole(updatedStudent.getRole());
                student.setPassword(updatedStudent.getPassword());
                // Assuming you have a setter for camps_withdrawn in the Student class
                student.setCampsWithdrawn(updatedStudent.getCampsWithdrawn());
                students.set(i, student); // Update the list with the modified student
                studentExists = true;
                break;
            }
        }
    
        if (studentExists) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Student student : students) {
                    String campsWithdrawnString = String.join(";", student.getCampsWithdrawn());
                    String data = String.join(StudentsController.CSV_SEPARATOR, student.getName(), student.getEmail(),
                            student.getFaculty(), student.getRole(), student.getPassword(), campsWithdrawnString);
                    bw.write(data);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing student file");
                e.printStackTrace();
            }
        } else {
            System.out.println("Student with email " + email + " not found");
        }
    }

    public void changePassword(StudentsController studentsController, String email, String newPassword) {
        List<Student> students = studentsController.studentFileHandler.readStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                student.setPassword(newPassword); // Change the password
                found = true;
                break;
            }
        }
        if (found) {
            studentsController.studentFileHandler.updateStudentList(students); // Update the entire list in the file
        } else {
            System.out.println("Student with email " + email + " not found");
        }
    }

    public boolean setStudentRole(StudentsController studentsController, String email, String role) {
        if (!role.equals("attendee") && !role.equals("committee")) {
            System.out.println("Invalid role. Only 'attendee' or 'committee' are allowed.");
            return false;
        }
    
        List<Student> students = studentsController.studentFileHandler.readStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                student.setRole(role);
                found = true;
                break;
            }
        }
    
        if (found) {
            studentsController.studentFileHandler.updateStudentList(students); // Update the entire list in the file
            return true;
        } else {
            System.out.println("Student with email " + email + " not found.");
            return false;
        }
    }
    
}
