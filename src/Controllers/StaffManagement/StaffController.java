package Controllers.StaffManagement;

import java.io.*;
import java.util.*;

import Controllers.StaffManagementInterface.StaffFileHandlerInterface;
import Controllers.StaffManagementInterface.StaffSearchServiceInterface;
import Controllers.StaffManagementInterface.StaffServiceInterface;
import Models.*;

public class StaffController {
    static final String FILE_PATH = "src/Database/staff.csv";
    static final String CSV_SEPARATOR = ",";
    static final String DEFAULT_PASSWORD = "password";
    StaffFileHandlerInterface staffFileHandler;
    public StaffSearchServiceInterface staffSearchService;
    public StaffServiceInterface staffService;

    public StaffController() {
        this.staffFileHandler = new StaffFileHandler();
        this.staffSearchService = new StaffSearchService();
        this.staffService = new StaffService();
    }

}
