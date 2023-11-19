package UI.CampCommitteeViewManagement;
import java.util.*;

import CampAccess.CampCommitteeAccessManagement.CommitteeAccess;
import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.EnquiryManagement.EnquiryController;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Controllers.SuggestionManagement.SuggestionController;
import Models.*;
import UI.CampCommitteeViewManagementInterface.CampCommitteeCampUIInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeEnquiryUIInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeMenuServiceInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeProfileUIInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeRegistrationUIInterface;
import UI.CampCommitteeViewManagementInterface.CampCommitteeSuggestionUIInterface;

public class CampCommitteeView {
    static Scanner sc=new Scanner(System.in);
    private String id;
    String name;
    String faculty;
    String email;

    StudentsController student_controller = new StudentsController();
    StaffController staff_controller = new StaffController();
    private SuggestionController suggestion_controller = new SuggestionController();
    private EnquiryController enquiry_controller = new EnquiryController();
    private CampController camp_controller = new CampController(student_controller, staff_controller);
    
    CommitteeAccess committeeAccess;
    public CampCommitteeMenuServiceInterface campCommitteeMenuService;
    CampCommitteeRegistrationUIInterface campCommitteeRegistrationUI;
    CampCommitteeSuggestionUIInterface campCommitteeSuggestionUI;
    CampCommitteeEnquiryUIInterface campCommitteeEnquiryUI;
    CampCommitteeProfileUIInterface campCommitteeProfileUI;
    CampCommitteeCampUIInterface campCommitteeCampUI;

    int logOff=2;
    public CampCommitteeView(String id,StudentsController studentcont){
        this.id=id;
        this.student_controller=studentcont;
        
        email = studentcont.studentSearchService.getStudentMail(this.id);
        name=studentcont.studentSearchService.getStudentName(studentcont, email);
        faculty=studentcont.studentSearchService.getStudentFaculty(studentcont, email);

        this.committeeAccess = new CommitteeAccess(suggestion_controller, enquiry_controller, studentcont, staff_controller, camp_controller, name);
        this.campCommitteeMenuService = new CampCommitteeMenuService();
        this.campCommitteeRegistrationUI = new CampCommitteeRegistrationUI();
        this.campCommitteeSuggestionUI = new CampCommitteeSuggestionUI();
        this.campCommitteeEnquiryUI = new CampCommitteeEnquiryUI();
        this.campCommitteeProfileUI = new CampCommitteeProfileUI();
        this.campCommitteeCampUI = new CampCommitteeCampUI();
        
        

    }

}
