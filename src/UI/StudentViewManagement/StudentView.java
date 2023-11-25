package UI.StudentViewManagement;

import java.util.*;

import CampAccess.*;
import CampAccess.StudentAccessManagement.StudentAccess;
import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.EnquiryManagement.EnquiryController;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Models.*;
import UI.StudentViewManagementInterface.StudentCampUIInterface;
import UI.StudentViewManagementInterface.StudentCommitteeRegistrationUIInterface;
import UI.StudentViewManagementInterface.StudentEnquiryUIInterface;
import UI.StudentViewManagementInterface.StudentMenuServiceInterface;
import UI.StudentViewManagementInterface.StudentProfileUIInterface;

import java.util.Scanner;

/**
 * The StudentView class represents the view for a student in the CAM system.
 * It provides access to various functionalities and information related to the student.
 */
public class StudentView {
    static Scanner sc = new Scanner(System.in);
    private String id;
    String name;
    String faculty;
    String email;
    StudentsController studentCont;
    EnquiryController enq;
    StudentAccess studentAccess;
    StaffController staffCont; 
    public StudentMenuServiceInterface studentMenuService;
    StudentCampUIInterface studentCampUI;
    StudentEnquiryUIInterface studentEnquiryUI;
    StudentCommitteeRegistrationUIInterface studentCommitteeRegistrationUI;
    StudentProfileUIInterface studentProfileUI;


    
    

    int logOff = 2;

    // Constructor
    /**
     * Represents a view for a student in the user interface.
     * This class is responsible for managing the display and interaction of a student's information and actions.
     */
    public StudentView(String id, StudentsController studentCont, StaffController staffCont) {
        this.id = id;
        this.studentCont = studentCont;
        this.staffCont = staffCont; 
        this.studentMenuService = new StudentMenuService();
        this.studentCampUI = new StudentCampUI();
        this.studentEnquiryUI = new StudentEnquiryUI();
        this.studentCommitteeRegistrationUI = new StudentCommitteeRegistrationUI();
        this.studentProfileUI = new StudentProfileUI();
        
        // Initialize StudentAccess with StaffController
        this.studentAccess = new StudentAccess(studentCont, new CampController(studentCont, staffCont), staffCont);

        email = studentCont.studentSearchService.getStudentMail(this.id);
        name = studentCont.studentSearchService.getStudentName(studentCont, email);
        faculty = studentCont.studentSearchService.getStudentFaculty(studentCont, email);
    }

}
