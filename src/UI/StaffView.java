package UI;
import java.util.*;

import CampAccess.StaffAccess;
import Controllers.*;
import Models.*;

public class StaffView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; 
    private StaffController staffcont;
    private StudentsController studentscont;
    private CampController campcont;
    private int logOff=0;
    private StaffAccess access;
    public StaffView(String id,StaffController staffcont){
        this.id=id;
        email = staffcont.getStaffMail(this.id);
        name=staffcont.getStaffName(email);
        faculty=staffcont.getStaffFaculty(email);
        this.staffcont=staffcont;

    }

    public void viewcamps(){
        access = new StaffAccess(id,staffcont);
        access.viewCamps(false);
    }

    public void viewmycamps(){
        access = new StaffAccess(id,staffcont);
        access.viewCamps(true);
    }

    public void removeCamp(){
        access = new StaffAccess(id,staffcont);
        access.delCamp();
    }

    public void visibility(){
        access = new StaffAccess(id,staffcont);
        access.changeVisibility();
    }

    public void CreatCamp(){
        access = new StaffAccess(id,staffcont);
        access.createCamp();
    }

    public void PasswordChange(){
        try{
            System.out.print("Enter your new password: ");
            String new_pass=sc.next();
            staffcont.changePassword(email, new_pass);
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
            if(staffcont.isFirstLogin(email)&&logOff==0){
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
            System.out.println("(8) View Suggestions");
            System.out.println("(9) Generate Report\n");
            System.out.println("(10) Change Password");
            System.out.println("(11) LogOff\n");
            System.out.print("In: ");
            try{
               choice=sc.nextInt();
            }
            catch(InputMismatchException  e){
                System.out.println("\nInvalid\n\n");
                sc.nextLine();
                continue;
            }
            System.out.println();
            switch(choice){
                case 2:case 7:case 8: case 9 :default:
                    System.out.println("Needs Implementation!");
                break;
                case 1:
                    CreatCamp();
                break;
                case 3:
                    removeCamp();
                break;
                case 4:
                    visibility();
                break;
                case 5:
                    viewcamps();
                break;
                case 6:
                    viewmycamps();
                break;
                case 10:
                    PasswordChange();
                case 11:
                    if(staffcont.isFirstLogin(email)){
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
