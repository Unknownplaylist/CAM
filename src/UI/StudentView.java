package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StudentView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; 
    private StudentsController studentcont;
    private EnquiryController enq;
    private int logOff=2;
    
    public StudentView(String id,StudentsController studentcont){
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

    public void SubmitEnquiry(){
        enq = new EnquiryController();
        enq.createEnquiry(name);
    }

    public void checkEnquiry(){
        enq=new EnquiryController();
        enq.viewEnquiry(name);
    }

    public void removeEnquiry(){
        enq=new EnquiryController();
        enq.deleteEnquiry(name);
    }

    public void changeEnquiry(){
        enq=new EnquiryController();
        enq.editEnquiry(name);
    }

    public void display(){
        logOff=0;
        do{
            System.out.println(name+"\n"+faculty+"\n");
            if(studentcont.isFirstLogin(email)&&logOff==0){
                System.out.println("Your account is not secure - Change from the Default Password\n");
                logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Register as Camp Committee");
            System.out.println("(4) Withdraw");
            System.out.println("(5) Submit Enquiries");
            System.out.println("(6) Check Enquiries");
            System.out.println("(7) Delete Enquiries");
            System.out.println("(8) Edit Enquiries\n");
            System.out.println("(9) Change your password");
            System.out.println("(10) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1:case 2:case 3:case 4:default:
                    System.out.println("Needs Implementation!");
                break;
                case 5:
                    SubmitEnquiry();
                break;
                case 6:
                    checkEnquiry();
                break;
                case 7:
                    removeEnquiry();
                break;
                case 8:
                    changeEnquiry();
                break;
                case 9:
                    PasswordChange();
                break;
                case 10:
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
