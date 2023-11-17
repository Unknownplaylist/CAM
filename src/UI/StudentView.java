package UI;

import java.util.*;

import CampAccess.*;
import Controllers.*;
import Models.*;

import java.util.Scanner;

public class StudentView {
    static Scanner sc = new Scanner(System.in);
    private String id, name, faculty, email;
    private StudentsController studentCont;
    private EnquiryController enq;
    private StudentAccess studentAccess;
    private StaffController staffCont; // Add StaffController
    
    

    private int logOff = 2;

    // Constructor
    public StudentView(String id, StudentsController studentCont, StaffController staffCont) {
        this.id = id;
        this.studentCont = studentCont;
        this.staffCont = staffCont; // Initialize staffCont
        
        // Initialize StudentAccess with StaffController
        this.studentAccess = new StudentAccess(studentCont, new CampController(studentCont, staffCont), staffCont);

        email = studentCont.getStudentMail(this.id);
        name = studentCont.getStudentName(email);
        faculty = studentCont.getStudentFaculty(email);
    }

    public void PasswordChange() {
        System.out.print("Enter your new password: ");
        String new_pass = sc.next();
        studentCont.changePassword(email, new_pass);
        System.out.println("\nYou will now be logged out.");
        logOff = 2;
    }

    public void SubmitEnquiry() {
        enq = new EnquiryController();
        enq.createEnquiry(name);
    }

    public void checkEnquiry() {
        enq = new EnquiryController();
        enq.viewEnquiry(name);
    }

    public void removeEnquiry() {
        enq = new EnquiryController();
        enq.deleteEnquiry(name);
    }

    public void changeEnquiry() {
        enq = new EnquiryController();
        enq.editEnquiry(name);
    }

    public void registerAsCampCommittee() {
        System.out.print("Enter the name of the camp to register as a committee member: ");
        String campName = sc.next();  

        CampController campController = new CampController(studentCont, staffCont); // Initialize campController

        Camp camp = campController.getCamp(campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }

        // Check if the student is already registered as a participant or committee member
        if (campController.isStudentRegisteredInCamp(email, campName)) {
            System.out.println("You are already registered in this camp.");
            return;
        }
    
        // Register the student as a committee member
        studentAccess.registerForCamp(email, campName, true);
        studentCont.setStudentRole(email, "committee");
        System.out.println("You have been successfully registered as a committee member for the camp: " + campName+ "\n");

        System.out.println("Please Relogin logging off...");
        logOff = 2;

    }
    
    

    public void withdrawFromCamp() {
        System.out.print("Enter the name of the camp you wish to withdraw from: ");
        String campName = sc.next();
        studentAccess.withdrawFromCamp(email, campName);
    }
    public void viewCamps() {
        studentAccess.viewAvailableCamps(email);
    }
    public void viewMyCamps() {
        studentAccess.viewMyCamps(email);
    }
    public void display() {
        logOff = 0;
        do {
            System.out.println(name + "\n" + faculty + "\n");
            if (studentCont.isFirstLogin(email) && logOff == 0) {
                System.out.println("Your account is not secure - Change from the Default Password\n");
                logOff = 1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Register as Camp Committee");
            System.out.println("(4) Withdraw");
            System.out.println("(5) Submit Enquiries");
            System.out.println("(6) Check Enquiries");
            System.out.println("(7) Delete Enquiries");
            System.out.println("(8) Edit Enquiries\n");
            System.out.println("(9) Change your password");
            System.out.println("(10) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewCamps();
                    break;
                case 2:
                    viewMyCamps();
                    break;
                case 3:
                    registerAsCampCommittee();
                    break;
                case 4:
                    withdrawFromCamp();
                    break;
                case 5:
                    SubmitEnquiry();
                    break;
                case 6:
                    checkEnquiry();
                    break;
                case 7:
                    removeEnquiry();
                    break;
                case 8:
                    changeEnquiry();
                    break;
                case 9:
                    PasswordChange();
                    break;
                case 10:
                    if (studentCont.isFirstLogin(email)) {
                        System.out.println("Kindly Change your password\n");
                        continue;
                    } else {
                        logOff = 2;
                        System.out.println("Logging Off...");
                    }
                    break;
            }
            System.out.println("\n");
        } while (logOff != 2);
    }

}
