package CampAccess.CampCommitteeAccessManagement;

import java.util.*;

import CampAccess.CampCommitteeAccessManagementInterface.CampCommitteeServiceInterface;
import CampAccess.CampCommitteeAccessManagementInterface.EnquiryManagementServiceInterface;
import CampAccess.CampCommitteeAccessManagementInterface.StudentManagementServiceInterface;
import CampAccess.CampCommitteeAccessManagementInterface.SuggestionManagementServiceInterface;
import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampManagementSystem.CampRegistrationService;
import Controllers.EnquiryManagement.EnquiryController;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Controllers.SuggestionManagement.SuggestionController;
import Models.*;

public class CommitteeAccess {
    static Scanner sc = new Scanner(System.in);

    SuggestionController suggestion_controller;
    EnquiryController enquiry_controller;
    StudentsController student_controller;
    private StaffController staff_controller;
    CampController camp_controller;
    public CampCommitteeServiceInterface campCommitteeService;
    public EnquiryManagementServiceInterface enquiryManagementService;
    public SuggestionManagementServiceInterface suggestionManagementService;
    public StudentManagementServiceInterface studentManagementService;


    Student student;

    /**
     * Represents a CommitteeAccess object that manages access to various controllers and services.
     * 
     * @param suggestionController The SuggestionController object.
     * @param enquiryController The EnquiryController object.
     * @param studentController The StudentsController object.
     * @param staffController The StaffController object.
     * @param campController The CampController object.
     * @param campCommName The name of the camp committee.
     */
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
