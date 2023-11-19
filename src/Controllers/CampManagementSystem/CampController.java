package Controllers.CampManagementSystem;

import Models.*;
import java.io.*;
import java.util.*;

import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentSearchService;
import Controllers.StudentManagement.StudentsController;

public class CampController {
    static final String FILE_PATH = "src/Database/CampInformation.csv";
    static final String CSV_SEPARATOR = ",";
    StudentsController studentController;
    StaffController staffController;
    CampFileHandler campFileHandler;
    public CampRegistrationService campRegistrationService;
    public CampReportingService campReportingService;
    public CampService campService;
    public CampSortingServiceInterface campSortingService;
    private StudentSearchService studentSearchService;

    public CampController(StudentsController studentController, StaffController staffController) {
        this.studentController = studentController;
        this.staffController = staffController;
        this.campFileHandler = new CampFileHandler();
        this.campRegistrationService = new CampRegistrationService();
        this.campReportingService = new CampReportingService();
        this.campService = new CampService();
        this.campSortingService = new CampSortingService();
        this.studentSearchService = studentSearchService;

    }
    
}
