package CampAccess.CampStudentAccessManagement;

import java.util.Scanner;

import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Models.*;

public class StudentAccess {
    StudentsController studentsController;
    CampController campController;
    private StaffController staffController;
    public StudentCampRegistrationService studentCampRegistrationService;
    public StudentCampViewService studentCampViewService;
    private StudentProfileService studentProfileService;
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
