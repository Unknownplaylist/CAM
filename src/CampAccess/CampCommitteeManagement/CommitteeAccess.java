package CampAccess.CampCommitteeManagement;

import java.util.*;

import Controllers.*;
import Controllers.CampEnquiryManagement.EnquiryController;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampManagementSystem.CampRegistrationService;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Controllers.CampSuggestionManagement.SuggestionController;
import Models.*;

public class CommitteeAccess {
    static Scanner sc = new Scanner(System.in);

    SuggestionController suggestion_controller;
    EnquiryController enquiry_controller;
    StudentsController student_controller;
    private StaffController staff_controller;
    CampController camp_controller;
    public CampCommitteeService campCommitteeService;
    public EnquiryManagementService enquiryManagementService;
    public SuggestionManagementService suggestionManagementService;
    public StudentManagementService studentManagementService;


    Student student;

    public CommitteeAccess(SuggestionController suggestionController, EnquiryController enquiryController,
            StudentsController studentController, StaffController staffController, CampController campController, String campCommName) {
        this.suggestion_controller = suggestionController;
        this.enquiry_controller = enquiryController;
        this.student_controller = studentController;
        this.staff_controller = staffController;
        this.camp_controller = campController;
        this.student = student_controller.studentSearchService.getStudentByName(student_controller, campCommName);
        this.campCommitteeService = new CampCommitteeService();
        this.enquiryManagementService = new EnquiryManagementService();
        this.suggestionManagementService = new SuggestionManagementService();
        this.studentManagementService = new StudentManagementService();
        
    }

   
}
