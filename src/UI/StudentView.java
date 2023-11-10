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
            System.out.println("(1) View all Camps");
            System.out.println("(2) View your Camps\n");
            System.out.println("(3) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:case 2:default:
                    System.out.println("Needs Implementation!");
                break;
                case 3:
                    logOff=1;
                    System.out.println("Logging Off...");
                break;
            }
            System.out.println("\n");
        }while(logOff!=1);
    }

}
