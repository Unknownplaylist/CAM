package Controllers;

import java.io.*;
import java.util.*;

class StudentsController {
    private static final String FILE_PATH = "students.csv";
    private static final String CSV_SEPARATOR = ",";
    
    public List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
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
