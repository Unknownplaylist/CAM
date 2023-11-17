package UI;
import java.util.*;

import CampAccess.CommitteeAccess;
import Controllers.*;
import Models.*;

public class CampCommitteeView {
    static Scanner sc=new Scanner(System.in);
    private String id, name, faculty, email; StudentsController studentcont;
    private CommitteeAccess committeeAccess;
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
            System.out.println("(4) Withdraw"); //Camp Committee cannot withdraw from camp
            System.out.println("(5) Submit Suggestions");
            System.out.println("(6) View/Edit/Delete Suggestions");
            System.out.println("(7) Check Enquiries\n");
            System.out.println("(8) Reply Enquiries");
            System.out.println("(9) Change your password");
            System.out.println("(10) LogOff\n");
            System.out.print("In: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1: //ViewCamps
                    break;
                case 2: //View your Camps
                    break;
                case 3: // viewCampDetails
                    break;
                case 4: //Withdraw
                    System.out.println("Cannot withdraw from camp where you are in the Camp Committee!");
                    break; 
                case 5: //submitSuggestions
                    committeeAccess.submitSuggestion(name);
                    break;
                case 6: //View/Edit/Delete Suggestions
                    //Add another switch/case
                    System.out.println(" Suggestions ");
                    System.out.println("======");
                    System.out.println("(1) View Suggestions");
                    System.out.println("(2) Edit Suggestions");
                    System.out.println("(3) Delete Suggestions");
                    int caseChoice =sc.nextInt();

                    switch (caseChoice){
                        case 1: //viewSuggestion
                            committeeAccess.viewSuggestion(name);
                            break;
                        case 2: //editSuggestion
                            committeeAccess.editSuggestion(name);
                            break;
                        case 3: //deleteSuggestion
                            committeeAccess.deleteSuggestion(name);
                            break;
                    }
                    break;
                case 7: //CheckEnquiries
                    System.out.println("Type in the camp that you are in charge to view its enquiries: ");
                    String camp = sc.nextLine();
                    //if (not in charge of that camp): deny access
                    committeeAccess.checkEnquiry(camp); //GET THE CAMP FIRST
                    break;
                case 8: //Reply Enquiries
                    System.out.println("Type in the camp that you are in charge to view its enquiries: ");
                    String camp1 = sc.nextLine();
                    committeeAccess.replyEnquiry(camp1);
                    break;
                case 9: //Change Password
                    PasswordChange();
                    break;
                case 10: //LogOff
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
