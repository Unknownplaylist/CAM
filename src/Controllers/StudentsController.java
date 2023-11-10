package Controllers;

import java.io.*;
import java.util.*;
import Models.*;


public class StudentsController {
    private static final String FILE_PATH = "src/Database/student.csv";
    private static final String CSV_SEPARATOR = ",";
    
    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File does not exist at: " + FILE_PATH);
            return students;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                if (data.length < 3) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                Student student = new Student(data[0], data[1], data[2]);
                students.add(student);
            }
        } catch (IOException e) {
            System.out.println("Error reading student file");
            e.printStackTrace();
        }
        return students;
    }
    public void writeStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty());
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing student file");
            e.printStackTrace();
        }
    }

    public String getStudentMail(String id){ //can remove this function later
        return (id+"@e.ntu.edu.sg");
    }
    
    public void updateStudent(String email, Student updatedStudent) {
        List<Student> students = readStudents();
        boolean studentExists = false;
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                student.setName(updatedStudent.getName());
                student.setFaculty(updatedStudent.getFaculty());
                studentExists = true;
                break;
            }
        }
        if (studentExists) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Student student : students) {
                    String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty());
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
    
    public String getStudentFaculty(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student.getFaculty();
            }
        }
        return "Student not found";
    }
    
    public String getStudentName(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student.getName();
            }
        }
        return "Student not found";
    }
    
    public boolean verifyStudent(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
