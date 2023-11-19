package UI;
import java.util.*;
import Controllers.*;
import Controllers.LoginManagement.LoginController;
import Controllers.LoginManagementInterface.LoginControllerInterface;
import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import Models.*;
import UI.CampCommitteeViewManagement.CampCommitteeView;
import UI.StaffViewManagement.StaffView;
import UI.StudentViewManagement.StudentView;

public class Main {
    public StaffView staffview;
    public StudentView studentview;
    public CampCommitteeView commview;
    public static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        int exit=0;
        int loginput=0;
        do
        {
            Main ob = new Main();
            // Initialize the StudentsController and StaffController
            StudentsController studentsController = new StudentsController();
            StaffController staffController = new StaffController();
            // Initialize the LoginController
            LoginControllerInterface loginController = new LoginController(studentsController, staffController);

            System.out.println("Welcome to CAMs\n===============\nPlease choose the option of your choice\n");
            System.out.println("(1) Log In");
            System.out.println("(2) Exit");
            System.out.print("In : ");
            try{
                loginput=sc.nextInt();
            }
            catch(InputMismatchException  e){
                System.out.println("\nInvalid\n\n");
                sc.nextLine();
                continue;
            }
            System.out.println();

            switch(loginput){
                case 1 :
                    loginController.UIlogIn(ob,studentsController,staffController);
                break;
                case 2 : 
                    System.out.println("Closing Application...");
                    exit=1;
                break;
                default : 
                    System.out.println("Try Again\n");
                break;
            }
            System.out.println();
        }while (exit!=1);
    }
}
