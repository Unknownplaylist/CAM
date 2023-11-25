package Controllers.StudentManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Controllers.StudentManagementInterface.StudentServiceInterface;
import Models.Student;

public class StudentService implements StudentServiceInterface {
    static final String FILE_PATH = "src/Database/student.csv";

    /**
     * Updates the data of a student in the student list and writes the updated list back to the CSV file.
     *
     * @param studentsController The controller for managing students.
     * @param updatedStudent The updated student object.
     */
    @Override
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

    /**
     * Updates a student's information based on their email.
     * 
     * @param studentsController The controller object for managing students.
     * @param email The email of the student to be updated.
     * @param updatedStudent The updated student object containing the new information.
     */
    @Override
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

    /**
     * Changes the password of a student with the specified email.
     * If a student with the given email is found, their password is updated with the new password.
     * If no student is found with the given email, a message is printed indicating that the student was not found.
     *
     * @param studentsController The controller for managing students.
     * @param email The email of the student whose password needs to be changed.
     * @param newPassword The new password to set for the student.
     */
    @Override
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

    /**
     * Sets the role of a student identified by their email address.
     * Only 'attendee' or 'committee' roles are allowed.
     * 
     * @param studentsController The controller for managing students.
     * @param email The email address of the student.
     * @param role The role to be set for the student.
     * @return true if the role was successfully set, false otherwise.
     */
    @Override
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
