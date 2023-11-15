package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class CampCommitteeView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; StudentsController studentcont;
    private int logOff=2;
    public CampCommitteeView(String id,StudentsController studentcont){
        this.id=id;
        this.studentcont=studentcont;
        email = studentcont.getStudentMail(this.id);
        name=studentcont.getStudentName(email);
        faculty=studentcont.getStudentFaculty(email);

    }

    public void PasswordChange(){
        System.out.print("Enter your new password: ");
        String new_pass=sc.next();
        studentcont.changePassword(email, new_pass);
        System.out.println("\nYou will now be logged out.");
        logOff=2;
    }

    public void SubmitSuggestion(){} //implement!

    public void display(){
        logOff=0;
        do{
            System.out.println(name+" - Committee"+"\n"+faculty+"\n");
            if(studentcont.isFirstLogin(email)&&logOff==0){
                System.out.println("This is your first login. Kindly set a new password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Camp Details");
            System.out.println("(4) Withdraw");
            System.out.println("(5) Submit Suggestions");
            System.out.println("(6) Check Enquiries\n");
            System.out.println("(7) Change your password");
            System.out.println("(8) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:case 2:case 3:case 4:case 6:default:
                    System.out.println("Needs Implementation!");
                break;
                case 5:
                    SubmitSuggestion();
                break;
                case 7:
                    PasswordChange();
                break;
                case 8:
                    if(studentcont.isFirstLogin(email)){
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
