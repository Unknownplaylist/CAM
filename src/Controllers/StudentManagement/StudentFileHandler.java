package Controllers.StudentManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Controllers.StudentManagementInterface.StudentFileHandlerInterface;
import Models.Student;

public class StudentFileHandler implements StudentFileHandlerInterface {
    static final String FILE_PATH = "src/Database/student.csv";
    @Override
    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(StudentsController.FILE_PATH);
        if (!file.exists()) {
            System.out.println("File does not exist at: " + StudentsController.FILE_PATH);
            return students;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(StudentsController.CSV_SEPARATOR, -1); // Use -1 to include empty trailing fields
                if (data.length < 6) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                List<String> campsWithdrawn = data[5].isEmpty() ? new ArrayList<>() : Arrays.asList(data[5].split(";"));
                Student student = new Student(data[0], data[1], data[2], data[3], data[4], campsWithdrawn);
                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("Error reading student file");
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void writeStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String data = String.join(StudentsController.CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty(),
                    student.getRole(), "password"); // Default password
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing student file");
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudentList(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                String campsWithdrawnString = String.join(";", student.getCampsWithdrawn());
                String data = String.join(StudentsController.CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty(),
                        student.getRole(), student.getPassword(),campsWithdrawnString);
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing student file");
            e.printStackTrace();
        }
    }
    
}
