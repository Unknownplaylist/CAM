package UI;
import java.util.*;

import CampAccess.CommitteeAccess;
import Controllers.*;
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
        
        email = studentcont.getStudentMail(this.id);
        name=studentcont.getStudentName(email);
        faculty=studentcont.getStudentFaculty(email);

        this.committeeAccess = new CommitteeAccess(suggestion_controller, enquiry_controller, studentcont, staff_controller, camp_controller, name);
    }

    public void PasswordChange(){
        System.out.print("Enter your new password: ");
        String new_pass=sc.next();
        student_controller.changePassword(email, new_pass);
        System.out.println("\nYou will now be logged out.");
        logOff=2;
    }

    public void display(){
        logOff=0;
        do{
            System.out.println(name+" - Committee"+"\n"+faculty+"\n");
            if(student_controller.isFirstLogin(email)&&logOff==0){
                System.out.println("This is your first login. Kindly set a new password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Camp Details");
            System.out.println("(4) Withdraw"); //Camp Committee cannot withdraw from camp
            System.out.println("(5) Submit Suggestions");
            System.out.println("(6) View/Edit/Delete Suggestions");
            System.out.println("(7) Check Enquiries");
            System.out.println("(8) Reply Enquiries");
            System.out.println("(9) View Current Point");
            System.out.println("(10) Change your password");
            System.out.println("(11) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1: //ViewCamps
                    committeeAccess.viewAvailableCamps(email);
                    break;
                case 2: //View your Camps
                    committeeAccess.viewMyCamps(name);
                    break;
                case 3: // viewCampDetails
                    committeeAccess.generateStudentList(email);
                    break;
                case 4: //Withdraw
                    System.out.println("Cannot withdraw from camp where you are in the Camp Committee!");
                    break; 
                case 5: //submitSuggestions
                    committeeAccess.submitSuggestion(name);
                    break;
                case 6: //View/Edit/Delete Suggestions
                    System.out.println(" Suggestions ");
                    System.out.println("======");
                    System.out.println("(1) View Suggestions");
                    System.out.println("(2) Edit Suggestions");
                    System.out.println("(3) Delete Suggestions");
                    int caseChoice =sc.nextInt();
                
                    switch (caseChoice){
                        case 1: //viewSuggestion
                            committeeAccess.viewSuggestion(name);
                            break;
                        case 2: //editSuggestion
                            committeeAccess.editSuggestion(name);
                            break;
                        case 3: //deleteSuggestion
                            committeeAccess.deleteSuggestion(name);
                            break;
                    }
                    break;
                case 7: //CheckEnquiries
                    committeeAccess.checkEnquiry(name); //GET THE CAMP FIRST
                    break;
                case 8: //Reply Enquiries
                    committeeAccess.replyEnquiry(name); //account for multiple number of enquiries for one camp
                    break;
                case 9:
                    committeeAccess.viewPoint(name);
                    break;
                case 10: //Change Password
                    PasswordChange();
                    break;
                case 11: //LogOff
                    if(student_controller.isFirstLogin(email)){
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
