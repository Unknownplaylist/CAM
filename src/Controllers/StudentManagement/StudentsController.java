package Controllers.StudentManagement;

import java.io.*;
import java.util.*;

import Controllers.StudentManagementInterface.StudentAuthenticationServiceInterface;
import Controllers.StudentManagementInterface.StudentFileHandlerInterface;
import Controllers.StudentManagementInterface.StudentSearchServiceInterface;
import Controllers.StudentManagementInterface.StudentServiceInterface;
import Models.*;

public class StudentsController {
    static final String FILE_PATH = "src/Database/student.csv";
    static final String CSV_SEPARATOR = ",";
    static final String DEFAULT_PASSWORD = "password";
    StudentFileHandlerInterface studentFileHandler;
    public StudentSearchServiceInterface studentSearchService;
    public StudentServiceInterface studentService;
    public StudentAuthenticationServiceInterface studentAuthenticationService;

    /**
     * This class represents a controller for managing students.
     */
    public StudentsController() {

        /**
         * Constructs a new StudentsController object.
         * Initializes the necessary dependencies for managing students.
         */
        this.studentFileHandler = new StudentFileHandler();
        this.studentSearchService = new StudentSearchService();
        this.studentService = new StudentService();
        this.studentAuthenticationService = new StudentAuthenticationService();
    }
}
