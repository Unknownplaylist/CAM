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

import java.util.Scanner;

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
    public StudentMenuService studentMenuService;
    StudentCampUI studentCampUI;
    StudentEnquiryUI studentEnquiryUI;
    StudentCommitteeRegistrationUI studentCommitteeRegistrationUI;
    StudentProfileUI studentProfileUI;


    
    

    int logOff = 2;

    // Constructor
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
