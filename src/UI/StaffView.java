package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StaffView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; StaffController staffcont;
    private int logOff=0;
    public StaffView(String id,StaffController staffcont){
        this.id=id;
        email = staffcont.getStaffMail(this.id);
        name=staffcont.getStaffName(email);
        faculty=staffcont.getStaffFaculty(email);
        this.staffcont=staffcont;

    }

    public void PasswordChange(){
        System.out.print("Enter your new password: ");
        String new_pass=sc.next();
        staffcont.changePassword(email, new_pass);
        System.out.println("\nYou will now be logged out.");
        logOff=2;
    }

    public void display(){
        logOff=0;
        do{
            System.out.println(name+"\n"+faculty+"\n");
            if(staffcont.isFirstLogin(email)&&logOff==0){
                System.out.println("This is your first login. Kindly set a new password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) Create Camps");
            System.out.println("(2) Change Camp Visibilty");
            System.out.println("(3) View all Camps");
            System.out.println("(4) Your Camps");
            System.out.println("(5) View Enquiries");
            System.out.println("(6) View Suggestions");
            System.out.println("(7) Generate Report\n");
            System.out.println("(8) Change Password");
            System.out.println("(9) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:case 2:case 3:case 4:case 5:case 6:case 7:default:
                    System.out.println("Needs Implementation!");
                break;
                case 8:
                    PasswordChange();
                case 9:
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
