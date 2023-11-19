package UI.StaffViewManagement;
import java.util.*;

import CampAccess.StaffAccessManagement.StaffAccess;
import Controllers.*;
import Controllers.StaffManagement.StaffController;
import Models.*;

public class StaffView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; 
    private StaffController staffcont;
    // private StudentsController studentscont;
    // private CampController campcont;
    private int logOff=0;
    private StaffAccess access;
    // private EnquiryController enq;
    // private SuggestionController sugg;
    public StaffView(String id,StaffController staffcont){
        this.id=id;
        email = staffcont.staffService.getStaffMail(this.id);
        name=staffcont.staffService.getStaffName(staffcont, email);
        faculty=staffcont.staffService.getStaffFaculty(staffcont, email);
        this.staffcont=staffcont;

    }

    public void generateReport(){
        access = new StaffAccess(id,staffcont);
        access.campReportingService.generateReport(access);
    }

    public void reviewSuggestions(){
        access = new StaffAccess(id,staffcont);
        access.suggestionReviewService.reviewSuggestions(access);
    }

    public void viewSuggestions(){
        access = new StaffAccess(id,staffcont);
        access.suggestionReviewService.viewSuggestions(access);
    }

    public void replyEnquiries(){
        access = new StaffAccess(id,staffcont);
        access.enquiryResponseService.replytoEnquiries(access);
    }

    public void viewEnquiries(){
        access = new StaffAccess(id,staffcont);
        access.enquiryResponseService.viewEnquiries(access);
    }

    public void viewCamps(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.viewCamps(access, false);
    }

    public void viewMyCamps(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.viewCamps(access, true);
    }

    public void removeCamp(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.delCamp(access);
    }

    public void editCamp(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.editCamp(access);
    }

    public void visibility(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.changeVisibility(access);
    }

    public void CreatCamp(){
        access = new StaffAccess(id,staffcont);
        access.campManagementService.createCamp(access);
    }

    public void PasswordChange(){
        try{
            System.out.print("Enter your new password: ");
            String new_pass=sc.next();
            staffcont.staffService.changePassword(staffcont, email, new_pass);
            System.out.println("\nYou will now be logged out.");
            logOff=2;
        }
        catch(Exception e){
            System.out.println("Invalid Input");
            return;
        }
    }

    public void display(){
        logOff=0;
        int choice;
        do{
            System.out.println(name+"\n"+faculty+"\n");
            if(staffcont.staffService.isFirstLogin(staffcont, email)&&logOff==0){
                System.out.println("Your account is not secure - Change from the Default Password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) Create a Camp");
            System.out.println("(2) Edit Camp");
            System.out.println("(3) Delete Camp");
            System.out.println("(4) Change Camp Visibilty");
            System.out.println("(5) View all Camps");
            System.out.println("(6) Your Camps");
            System.out.println("(7) View Enquiries");
            System.out.println("(8) Reply to Enquiries");
            System.out.println("(9) View Suggestions");
            System.out.println("(10) Review Suggestions");
            System.out.println("(11) Generate Report\n");
            System.out.println("(12) Change Password");
            System.out.println("(13) LogOff\n");
            System.out.print("In: ");
            try{
               choice=sc.nextInt();
            }
            catch(InputMismatchException  e){
                System.out.println("\nInvalid\n\n");
                sc.nextLine();
                sc.nextLine();
                continue;
            }
            System.out.println();
            switch(choice){
                default:
                    System.out.println("Needs Implementation!");
                break;
                case 1:
                    CreatCamp();
                break;
                case 2:
                    editCamp();
                break;
                case 3:
                    removeCamp();
                break;
                case 4:
                    visibility();
                break;
                case 5:
                    viewCamps();
                break;
                case 6:
                    viewMyCamps();
                break;
                case 7:
                    viewEnquiries();
                break;
                case 8:
                    replyEnquiries();
                break;
                case 9:
                    viewSuggestions();
                break;
                case 10:
                    reviewSuggestions();
                break;
                case 11:
                    generateReport();
                break;
                case 12:
                    PasswordChange();
                case 13:
                    if(staffcont.staffService.isFirstLogin(staffcont, email)){
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