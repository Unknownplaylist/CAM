package Controllers.CampManagementSystem;

import Models.*;
import java.io.*;
import java.util.*;

import Controllers.CampManagementSystemInterface.CampFileHandlerInterface;
import Controllers.CampManagementSystemInterface.CampRegistrationServiceInterface;
import Controllers.CampManagementSystemInterface.CampReportingServiceInterface;
import Controllers.CampManagementSystemInterface.CampServiceInterface;
import Controllers.CampManagementSystemInterface.CampSortingServiceInterface;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Controllers.StudentManagementInterface.StudentSearchServiceInterface;

public class CampController {
    static final String FILE_PATH = "src/Database/CampInformation.csv";
    static final String CSV_SEPARATOR = ",";
    StudentsController studentController;
    StaffController staffController;
    CampFileHandlerInterface campFileHandler;
    public CampRegistrationServiceInterface campRegistrationService;
    public CampReportingServiceInterface campReportingService;
    public CampServiceInterface campService;
    public CampSortingServiceInterface campSortingService;
    private StudentSearchServiceInterface studentSearchService;

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
