package CampAccess.CampStaffAccessManagement;
import java.util.*;

import Controllers.*;
import Controllers.CampEnquiryManagement.EnquiryController;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Controllers.CampSuggestionManagement.SuggestionController;
import Models.*;

    public class StaffAccess {
    static Scanner sc=new Scanner(System.in);
    
    private StaffController staffcont=new StaffController();
    private StudentsController studentscont=new StudentsController();
    EnquiryController enq = new EnquiryController();
    SuggestionController sugg = new SuggestionController();
    CampController campcont = new CampController(studentscont, staffcont);
    Camp camp;
    String staffid;
    Staff staff;
    public CampManagementService campManagementService;
    public CampReportingService campReportingService;
    public SuggestionReviewService suggestionReviewService;
    public EnquiryResponseService enquiryResponseService;

    public StaffAccess(String staffid,StaffController staffcont){
        this.staffid=staffid;
        this.staffcont=staffcont;
        staff=staffcont.staffSearchService.getStaffByEmail(staffcont, staffid+"@NTU.EDU.SG");
        campManagementService = new CampManagementService();
        campReportingService = new CampReportingService();
        suggestionReviewService = new SuggestionReviewService();
        enquiryResponseService = new EnquiryResponseService();
        
    }
}
