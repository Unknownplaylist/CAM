package Controllers.CampStudentManagement;

import java.io.*;
import java.util.*;
import Models.*;

public class StudentsController {
    static final String FILE_PATH = "src/Database/student.csv";
    static final String CSV_SEPARATOR = ",";
    static final String DEFAULT_PASSWORD = "password";
    StudentFileHandler studentFileHandler;
    public StudentSearchService studentSearchService;
    public StudentService studentService;
    public StudentAuthenticationService studentAuthenticationService;

    public StudentsController() {
        this.studentFileHandler = new StudentFileHandler();
        this.studentSearchService = new StudentSearchService();
        this.studentService = new StudentService();
        this.studentAuthenticationService = new StudentAuthenticationService();
    }
}
