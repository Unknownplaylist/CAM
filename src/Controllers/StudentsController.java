package Controllers;

import java.io.*;
import java.util.*;
import Models.*;

public class StudentsController {
    private static final String FILE_PATH = "src/Database/student.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String DEFAULT_PASSWORD = "password";

    // Read students from CSV
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
                String[] data = line.split(CSV_SEPARATOR, -1); // Use -1 to include empty trailing fields
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
    

    // Write a new student to CSV with default password
    public void writeStudent(Student student) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty(),
                    student.getRole(), "password"); // Default password
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing student file");
            e.printStackTrace();
        }
    }
    public void updateStudentData(Student updatedStudent) {
        List<Student> students = readStudents();
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
                    String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(),
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
    // Update an existing student
    public void updateStudent(String email, Student updatedStudent) {
        List<Student> students = readStudents();
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
                    String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(),
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
    

    public boolean isFirstLogin(String email) {
        return checkPassword(email, DEFAULT_PASSWORD);
    }

    public boolean checkPassword(String email, String password) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email) && student.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void changePassword(String email, String newPassword) {
        List<Student> students = readStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                student.setPassword(newPassword); // Change the password
                found = true;
                break;
            }
        }
        if (found) {
            updateStudentList(students); // Update the entire list in the file
        } else {
            System.out.println("Student with email " + email + " not found");
        }
    }

    private void updateStudentList(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Student student : students) {
                String data = String.join(CSV_SEPARATOR, student.getName(), student.getEmail(), student.getFaculty(),
                        student.getRole(), student.getPassword());
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing student file");
            e.printStackTrace();
        }
    }

    // Get user role based on email
    public String getUserRole(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getRole();
            }
        }
        return "Role not found"; // or null, depending on your handling
    }

    public String getStudentMail(String id) { // can remove this function later
        return (id + "@e.ntu.edu.sg");
    }

    public String getStudentFaculty(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getFaculty();
            }
        }
        return "Student not found";
    }

    public String getStudentName(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student.getName();
            }
        }
        return "Student not found";
    }

    public boolean verifyStudent(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public Student getStudentByEmail(String email) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return student;
            }
        }
        return null; // Student not found
    }

    public boolean setStudentRole(String email, String role) {
        if (!role.equals("attendee") && !role.equals("committee")) {
            System.out.println("Invalid role. Only 'attendee' or 'committee' are allowed.");
            return false;
        }

        List<Student> students = readStudents();
        boolean found = false;
        for (Student student : students) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                student.setRole(role);
                found = true;
                break;
            }
        }

        if (found) {
            updateStudentList(students); // Update the entire list in the file
            return true;
        } else {
            System.out.println("Student with email " + email + " not found.");
            return false;
        }
    }

    public Student getStudentByName(String name) {
        List<Student> students = readStudents();
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null; // Return null if no student with the given name is found
    }
}
