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
    /**
     * The CampController class is responsible for managing the camp system.
     * It handles the registration, reporting, sorting, and searching of students in the camp.
     */

    public CampController(StudentsController studentController, StaffController staffController) {
        /**
         * Constructs a new CampController object with the specified student controller
         * and staff controller.
         * 
         * @param studentController the student controller object
         * @param staffController   the staff controller object
         */
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
