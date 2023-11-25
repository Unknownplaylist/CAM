package CampAccess.StaffAccessManagement;

import java.util.*;

import CampAccess.StaffAccessManagementInterface.CampManagementServiceInterface;
import CampAccess.StaffAccessManagementInterface.CampReportingServiceInterface;
import CampAccess.StaffAccessManagementInterface.EnquiryResponseServiceInterface;
import CampAccess.StaffAccessManagementInterface.SuggestionReviewServiceInterface;
import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.EnquiryManagement.EnquiryController;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Controllers.SuggestionManagement.SuggestionController;
import Models.*;

public class StaffAccess {
    static Scanner sc = new Scanner(System.in);

    private StaffController staffcont = new StaffController();
    private StudentsController studentscont = new StudentsController();
    EnquiryController enq = new EnquiryController();
    SuggestionController sugg = new SuggestionController();
    CampController campcont = new CampController(studentscont, staffcont);
    Camp camp;
    String staffid;
    Staff staff;
    public CampManagementServiceInterface campManagementService;
    public CampReportingServiceInterface campReportingService;
    public SuggestionReviewServiceInterface suggestionReviewService;
    public EnquiryResponseServiceInterface enquiryResponseService;

    /**
     * Represents a staff access object.
     * This class is responsible for managing staff access and providing access to
     * various services.
     */

    public StaffAccess(String staffid, StaffController staffcont) {
        /**
         * Constructs a new StaffAccess object with the specified staff ID and staff
         * controller.
         * 
         * @param staffid   the ID of the staff
         * @param staffcont the staff controller object
         */
        this.staffid = staffid;
        this.staffcont = staffcont;
        staff = staffcont.staffSearchService.getStaffByEmail(staffcont, staffid + "@NTU.EDU.SG");
        campManagementService = new CampManagementService();
        campReportingService = new CampReportingService();
        suggestionReviewService = new SuggestionReviewService();
        enquiryResponseService = new EnquiryResponseService();

    }
}
