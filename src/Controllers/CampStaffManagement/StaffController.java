package Controllers.CampStaffManagement;

import java.io.*;
import java.util.*;
import Models.*;

public class StaffController {
    static final String FILE_PATH = "src/Database/staff.csv";
    static final String CSV_SEPARATOR = ",";
    static final String DEFAULT_PASSWORD = "password";
    StaffFileHandler staffFileHandler;
    public StaffSearchService staffSearchService;
    public StaffService staffService;

    public StaffController() {
        this.staffFileHandler = new StaffFileHandler();
        this.staffSearchService = new StaffSearchService();
        this.staffService = new StaffService();
    }

}
