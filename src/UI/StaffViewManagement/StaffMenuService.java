package UI.StaffViewManagement;

import java.util.InputMismatchException;

import UI.StaffViewManagementInterface.StaffMenuServiceInterface;

public class StaffMenuService implements StaffMenuServiceInterface {

    @Override
    public void display(StaffView staffView){
        staffView.logOff=0;
        int choice;
        do{
            System.out.println(staffView.name+"\n"+staffView.faculty+"\n");
            if(staffView.staffcont.staffService.isFirstLogin(staffView.staffcont, staffView.email)&&staffView.logOff==0){
                System.out.println("Your account is not secure - Change from the Default Password\n");
                staffView.logOff=1;
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
            System.out.println("(8) Reply to Enquiries");
            System.out.println("(9) View Suggestions");
            System.out.println("(10) Review Suggestions");
            System.out.println("(11) Generate Report\n");
            System.out.println("(12) Change Password");
            System.out.println("(13) LogOff\n");
            System.out.print("In: ");
            try{
               choice=StaffView.sc.nextInt();
            }
            catch(InputMismatchException  e){
                System.out.println("\nInvalid\n\n");
                StaffView.sc.nextLine();
                StaffView.sc.nextLine();
                continue;
            }
            System.out.println();
            switch(choice){
                default:
                    System.out.println("Needs Implementation!");
                break;
                case 1:
                    staffView.staffCampManagementUI.CreatCamp(staffView);
                break;
                case 2:
                    staffView.staffCampManagementUI.editCamp(staffView);
                break;
                case 3:
                    staffView.staffCampManagementUI.removeCamp(staffView);
                break;
                case 4:
                    staffView.staffCampManagementUI.visibility(staffView);
                break;
                case 5:
                    staffView.staffCampManagementUI.viewCamps(staffView);
                break;
                case 6:
                    staffView.staffCampManagementUI.viewMyCamps(staffView);
                break;
                case 7:
                    staffView.staffEnquiryUI.viewEnquiries(staffView);
                break;
                case 8:
                    staffView.staffEnquiryUI.replyEnquiries(staffView);
                break;
                case 9:
                    staffView.staffSuggestionUI.viewSuggestions(staffView);
                break;
                case 10:
                    staffView.staffSuggestionUI.reviewSuggestions(staffView);
                break;
                case 11:
                    staffView.staffReportUI.generateReport(staffView);
                break;
                case 12:
                    staffView.staffProfileUI.PasswordChange(staffView);
                case 13:
                    if(staffView.staffcont.staffService.isFirstLogin(staffView.staffcont, staffView.email)){
                        System.out.println("Kindly Change your password\n");
                        continue;
                    }
                    else{
                        staffView.logOff=2;
                        System.out.println("Logging Off...");
                    }
                break;
            }
            System.out.println("\n");
        }while(staffView.logOff!=2);
    }
    
}
