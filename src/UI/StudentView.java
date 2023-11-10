package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StudentView {
    static Scanner sc=new Scanner(System.in);
    String userid, name, faculty;
    public StudentView(String id,StudentsController studentcont){
        userid=id;
        name=studentcont.getStudentName(studentcont.getStudentMail(id));
        faculty=studentcont.getStudentFaculty(studentcont.getStudentMail(id));

    }
    public void display(){
        int logOff=0;
        do{
            System.out.println(name+"\n"+faculty+"\n");
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Register as Camp Committee");
            System.out.println("(4) Withdraw");
            System.out.println("(5) Submit Enquiries");
            System.out.println("(6) Check Enquiries\n");
            System.out.println("(7) Change your password");
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
