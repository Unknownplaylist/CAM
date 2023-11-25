package UI.CampCommitteeViewManagement;

import UI.CampCommitteeViewManagementInterface.CampCommitteeMenuServiceInterface;

public class CampCommitteeMenuService implements CampCommitteeMenuServiceInterface {

    /**
     * Displays the menu and handles user input for the Camp Committee view.
     * 
     * @param campCommitteeView The CampCommitteeView object representing the current view.
     */
    @Override
    public void display(CampCommitteeView campCommitteeView){
        campCommitteeView.logOff=0;
        do{
            System.out.println(campCommitteeView.name+" - Committee"+"\n"+campCommitteeView.faculty+"\n");
            if(campCommitteeView.student_controller.studentAuthenticationService.isFirstLogin(campCommitteeView.student_controller, campCommitteeView.email)&&campCommitteeView.logOff==0){
                System.out.println("This is your first login. Kindly set a new password\n");
                campCommitteeView.logOff=1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Camp Details");
            System.out.println("(4) Register for Camp");//implemented
            System.out.println("(5) Withdraw"); //Camp Committee cannot withdraw from camp
            System.out.println("(6) Submit Suggestions");
            System.out.println("(7) View/Edit/Delete Suggestions");
            System.out.println("(8) Check Enquiries");
            System.out.println("(9) Reply Enquiries");
            System.out.println("(10) View Current Point");
            System.out.println("(11) Change your password");
            System.out.println("(12) LogOff\n");
            System.out.print("In: ");
            int choice = CampCommitteeView.sc.nextInt();
            switch(choice){
                case 1: //ViewCamps
                    campCommitteeView.campCommitteeCampUI.viewCamps(campCommitteeView);
                    break;
                case 2: //View your Camps
                    campCommitteeView.campCommitteeCampUI.viewYourCamps(campCommitteeView);
                    break;
                case 3: // viewCampDetails
                    campCommitteeView.campCommitteeCampUI.viewCampDetails(campCommitteeView);
                    break;
                case 4: 
                    campCommitteeView.campCommitteeRegistrationUI.registerForCamp(campCommitteeView, campCommitteeView.campCommitteeMenuService);
                    break;
                case 5: //Withdraw
                    campCommitteeView.campCommitteeRegistrationUI.withdrawFromCamp(campCommitteeView);
                    break; 
                case 6: //submitSuggestions
                    campCommitteeView.campCommitteeSuggestionUI.submitSuggestions(campCommitteeView);
                    break;
                case 7: //View/Edit/Delete Suggestions
                    System.out.println(" Suggestions ");
                    System.out.println("======");
                    System.out.println("(1) View Suggestions");
                    System.out.println("(2) Edit Suggestions");
                    System.out.println("(3) Delete Suggestions");
                    int caseChoice =CampCommitteeView.sc.nextInt();
                
                    switch (caseChoice){
                        case 1: //viewSuggestion
                            campCommitteeView.campCommitteeSuggestionUI.viewSuggestion(campCommitteeView);
                            break;
                        case 2: //editSuggestion
                            campCommitteeView.campCommitteeSuggestionUI.editSuggestion(campCommitteeView);
                            break;
                        case 3: //deleteSuggestion
                            campCommitteeView.campCommitteeSuggestionUI.deleteSuggestion(campCommitteeView);
                            break;
                    }
                    break;
                case 8: //CheckEnquiries
                    campCommitteeView.campCommitteeEnquiryUI.checkEnquiries(campCommitteeView); //GET THE CAMP FIRST
                    break;
                case 9: //Reply Enquiries
                    campCommitteeView.campCommitteeEnquiryUI.replyEnquiries(campCommitteeView); //account for multiple number of enquiries for one camp
                    break;
                case 10:
                    campCommitteeView.campCommitteeCampUI.viewCurrentPoint(campCommitteeView);
                    break;
                case 11: //Change Password
                    campCommitteeView.campCommitteeProfileUI.PasswordChange(campCommitteeView);
                    break;
                case 12: //LogOff
                    if(campCommitteeView.student_controller.studentAuthenticationService.isFirstLogin(campCommitteeView.student_controller, campCommitteeView.email)){
                        System.out.println("Kindly Change your password\n");
                        continue;
                    }  
                    else{
                        campCommitteeView.logOff=2;
                        System.out.println("Logging Off...");
                    }
                break;
            }
            System.out.println("\n");
        }while(campCommitteeView.logOff!=2);
    }
    
}
