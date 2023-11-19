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

    public void updateCamp(String campName, Camp updatedCamp) {
        List<Camp> camps = campFileHandler.readCamps(this);
        for(int i=0;i<camps.size();i++){
            if(updatedCamp.getCampName().equalsIgnoreCase(camps.get(i).getCampName())){
                camps.get(i).setStartDate(updatedCamp.getStartDate());
                camps.get(i).setEndDate(updatedCamp.getEndDate());
                camps.get(i).setRegistrationCloseDate(updatedCamp.getRegistrationCloseDate());
                camps.get(i).setLocation(updatedCamp.getLocation());
                camps.get(i).setDescription(updatedCamp.getDescription());
            }
        }

        campFileHandler.writeAllCamps(camps);
    }
    
}
