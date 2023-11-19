package UI.StaffViewManagement;
import java.util.*;

import CampAccess.StaffAccessManagement.StaffAccess;
import Controllers.*;
import Controllers.StaffManagement.StaffController;
import Models.*;
import UI.StaffViewManagementInterface.StaffCampManagementUIInterface;
import UI.StaffViewManagementInterface.StaffEnquiryUIInterface;
import UI.StaffViewManagementInterface.StaffMenuServiceInterface;
import UI.StaffViewManagementInterface.StaffProfileUIInterface;
import UI.StaffViewManagementInterface.StaffReportUIInterface;
import UI.StaffViewManagementInterface.StaffSuggestionUIInterface;

public class StaffView {
    static Scanner sc=new Scanner(System.in);
    String id;
    String name;
    String faculty;
    String email; 
    StaffController staffcont;
    // private StudentsController studentscont;
    // private CampController campcont;
    int logOff=0;
    StaffAccess access;
    // private EnquiryController enq;
    // private SuggestionController sugg;
    public StaffMenuServiceInterface staffMenuService;
    StaffCampManagementUIInterface staffCampManagementUI;
    StaffEnquiryUIInterface staffEnquiryUI;
    StaffSuggestionUIInterface staffSuggestionUI;
    StaffReportUIInterface staffReportUI;
    StaffProfileUIInterface staffProfileUI;
    public StaffView(String id,StaffController staffcont){
        this.id=id;
        email = staffcont.staffService.getStaffMail(this.id);
        name=staffcont.staffService.getStaffName(staffcont, email);
        faculty=staffcont.staffService.getStaffFaculty(staffcont, email);
        this.staffcont=staffcont;
        this.staffMenuService = new StaffMenuService();
        this.staffCampManagementUI = new StaffCampManagementUI();
        this.staffEnquiryUI = new StaffEnquiryUI();
        this.staffSuggestionUI = new StaffSuggestionUI();
        this.staffReportUI = new StaffReportUI();
        this.staffProfileUI = new StaffProfileUI();

    }

}