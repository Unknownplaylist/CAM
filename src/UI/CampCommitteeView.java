package UI;
import java.util.*;

import CampAccess.CampCommitteeManagement.CommitteeAccess;
import Controllers.*;
import Controllers.CampEnquiryManagement.EnquiryController;
import Controllers.CampManagementSystem.CampController;
import Controllers.CampStaffManagement.StaffController;
import Controllers.CampStudentManagement.StudentsController;
import Controllers.CampSuggestionManagement.SuggestionController;
import Models.*;

public class CampCommitteeView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email;

    private StudentsController student_controller = new StudentsController();
    private StaffController staff_controller = new StaffController();
    private SuggestionController suggestion_controller = new SuggestionController();
    private EnquiryController enquiry_controller = new EnquiryController();
    private CampController camp_controller = new CampController(student_controller, staff_controller);
    
    private CommitteeAccess committeeAccess;

    private int logOff=2;
    public CampCommitteeView(String id,StudentsController studentcont){
        this.id=id;
        this.student_controller=studentcont;
        
        email = studentcont.studentSearchService.getStudentMail(this.id);
        name=studentcont.studentSearchService.getStudentName(studentcont, email);
        faculty=studentcont.studentSearchService.getStudentFaculty(studentcont, email);

        this.committeeAccess = new CommitteeAccess(suggestion_controller, enquiry_controller, studentcont, staff_controller, camp_controller, name);
    }

    public void PasswordChange(){
        System.out.print("Enter your new password: ");
        String new_pass=sc.next();
        student_controller.studentService.changePassword(student_controller, email, new_pass);
        System.out.println("\nYou will now be logged out.");
        logOff=2;
    }
    public void withdrawFromCamp() {
        System.out.print("Enter the name of the camp you wish to withdraw from: ");
        String campName = sc.next();
        committeeAccess.campCommitteeService.withdrawFromCamp(committeeAccess, email, campName);
    }
    public void registerForCamp() {
        System.out.print("Enter the name of the camp to register: ");
        String campName = sc.next();
    
        CampController campController = new CampController(student_controller, staff_controller); // Initialize campController
    
        Camp camp = campController.campService.getCamp(campController, campName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
    
        // Check if the student is already registered in the camp
        if (campController.campService.isStudentRegisteredInCamp(campController, email, campName)) {
            System.out.println("You are already registered in this camp.");
            return;
        }
    
        // Register the student as a participant
        committeeAccess.campCommitteeService.registerForCamp(committeeAccess, email, campName, false); // false indicates a regular participant
       
    }
    public void display(){
        logOff=0;
        do{
            System.out.println(name+" - Committee"+"\n"+faculty+"\n");
            if(student_controller.studentAuthenticationService.isFirstLogin(student_controller, email)&&logOff==0){
                System.out.println("This is your first login. Kindly set a new password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Camp Details");
            System.out.println("(4) Register for Camp");//implemented
            System.out.println("(5) Withdraw"); //Camp Committee cannot withdraw from camp
            System.out.println("(6) Submit Suggestions");
            System.out.println("(7) View/Edit/Delete Suggestions");
            System.out.println("(8) Check Enquiries");
            System.out.println("(9) Reply Enquiries");
            System.out.println("(10) View Current Point");
            System.out.println("(11) Change your password");
            System.out.println("(12) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1: //ViewCamps
                    committeeAccess.campCommitteeService.viewAvailableCamps(committeeAccess, email);
                    break;
                case 2: //View your Camps
                    committeeAccess.campCommitteeService.viewMyCamps(committeeAccess, name);
                    break;
                case 3: // viewCampDetails
                    committeeAccess.studentManagementService.generateStudentList(committeeAccess, email);
                    break;
                case 4: 
                    registerForCamp();
                    break;
                case 5: //Withdraw
                    withdrawFromCamp();
                    break; 
                case 6: //submitSuggestions
                    committeeAccess.suggestionManagementService.submitSuggestion(committeeAccess, name);
                    break;
                case 7: //View/Edit/Delete Suggestions
                    System.out.println(" Suggestions ");
                    System.out.println("======");
                    System.out.println("(1) View Suggestions");
                    System.out.println("(2) Edit Suggestions");
                    System.out.println("(3) Delete Suggestions");
                    int caseChoice =sc.nextInt();
                
                    switch (caseChoice){
                        case 1: //viewSuggestion
                            committeeAccess.suggestionManagementService.viewSuggestion(committeeAccess, name);
                            break;
                        case 2: //editSuggestion
                            committeeAccess.suggestionManagementService.editSuggestion(committeeAccess, name);
                            break;
                        case 3: //deleteSuggestion
                            committeeAccess.suggestionManagementService.deleteSuggestion(committeeAccess, name);
                            break;
                    }
                    break;
                case 8: //CheckEnquiries
                    committeeAccess.enquiryManagementService.checkEnquiry(committeeAccess, name); //GET THE CAMP FIRST
                    break;
                case 9: //Reply Enquiries
                    committeeAccess.enquiryManagementService.replyEnquiry(committeeAccess, name); //account for multiple number of enquiries for one camp
                    break;
                case 10:
                    committeeAccess.studentManagementService.viewPoint(committeeAccess, name);
                    break;
                case 11: //Change Password
                    PasswordChange();
                    break;
                case 12: //LogOff
                    if(student_controller.studentAuthenticationService.isFirstLogin(student_controller, email)){
                        System.out.println("Kindly Change your password\n");
                        continue;
                    }  
                    else{
                        logOff=2;
                        System.out.println("Logging Off...");
                    }
                break;
            }
            System.out.println("\n");
        }while(logOff!=2);
    }

}
