package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class Main {
    StaffView staffview;
    StudentView studentview;
    static Scanner sc = new Scanner(System.in);
    
    public void UIlogIn(LoginController lgc,StudentsController sdc,StaffController sfc){
        System.out.print("Username : ");
        String userId = sc.next();
        System.out.print("Password : ");
        String password = sc.next();
        System.out.println();
        boolean loggedIn = lgc.loginUser(userId, password);

        if(loggedIn){
            String user = lgc.userType(userId);
            if(user.equals("Student")){
                studentview = new StudentView(userId,sdc);
                studentview.display();
            }
            else{
                staffview = new StaffView(userId,sfc);
                staffview.display();
            }
        }
        
    }
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
            LoginController loginController = new LoginController(studentsController, staffController);

            System.out.println("Welcome to CAMs\n===============\nPlease choose the option of your choice\n");
            System.out.println("(1) Log In");
            System.out.println("(2) Exit");
            System.out.print("In : ");
            try{
                loginput=sc.nextInt();
            }
            catch(InputMismatchException  e){
                System.out.println("Invalid Choice\n\n");
                sc.nextLine();
                continue;
            }
            System.out.println();

            switch(loginput){
                case 1 :
                    ob.UIlogIn(loginController,studentsController,staffController);
                break;
                case 2 : 
                    System.out.println("Closing Application...");
                    exit=1;
                break;
                default : 
                    System.out.println("Try Again");
                break;
            }
            System.out.println();
        }while (exit!=1);
    }
}
