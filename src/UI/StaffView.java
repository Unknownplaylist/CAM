package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StaffView {
    static Scanner sc=new Scanner(System.in);
    String userid, name, faculty;
    public StaffView(String id,StaffController staffcont){
        userid=id;
        name=staffcont.getStaffName(staffcont.getStaffMail(id));
        faculty=staffcont.getStaffFaculty(staffcont.getStaffMail(id));

    }
    public void display(){
        int logOff=0;
        do{
            System.out.println(name+"\n"+faculty+"\n");
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) Create Camps");
            System.out.println("(2) Change Camp Visibilty");
            System.out.println("(3) View all Camps");
            System.out.println("(4) Your Camps");
            System.out.println("(5) View Enquiries");
            System.out.println("(6) View Suggestions");
            System.out.println("(7) Generate Report\n");
            System.out.println("(8) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:case 2:case 3:case 4:case 5:case 6:case 7:default:
                    System.out.println("Needs Implementation!");
                break;
                case 8:
                    logOff=1;
                    System.out.println("Logging Off...");
                break;
            }
            System.out.println("\n");
        }while(logOff!=1);
    }

}
