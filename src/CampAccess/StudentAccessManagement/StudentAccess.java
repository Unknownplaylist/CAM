package CampAccess.StudentAccessManagement;

import java.util.Scanner;

import CampAccess.StudentAccessManagementInterface.StudentCampRegistrationServiceInterface;
import CampAccess.StudentAccessManagementInterface.StudentCampViewServiceInterface;
import CampAccess.StudentAccessManagementInterface.StudentProfileServiceInterface;
import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Models.*;

public class StudentAccess {
    StudentsController studentsController;
    CampController campController;
    private StaffController staffController;
    public StudentCampRegistrationServiceInterface studentCampRegistrationService;
    public StudentCampViewServiceInterface studentCampViewService;
    private StudentProfileServiceInterface studentProfileService;
    static Scanner sc = new Scanner(System.in);

    public StudentAccess(StudentsController studentsController, CampController campController,
            StaffController staffController) {
        this.studentsController = studentsController;
        this.campController = campController;
        this.staffController = staffController;
        this.studentCampRegistrationService = new StudentCampRegistrationService();
        this.studentCampViewService = new StudentCampViewService();
        this.studentProfileService = new StudentProfileService();
    }

}
