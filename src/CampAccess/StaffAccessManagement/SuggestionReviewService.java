package CampAccess.StaffAccessManagement;

import java.util.List;

import CampAccess.StaffAccessManagementInterface.SuggestionReviewServiceInterface;

public class SuggestionReviewService implements SuggestionReviewServiceInterface {

    /**
     * This method allows a staff member to review suggestions for a specific camp.
     * It prompts the user to enter the name of the camp and checks if the camp exists and if the staff member has access to it.
     * If the camp exists and the staff member has access, it calls the execReviewSuggestion method to perform the review.
     * If the camp does not exist, it displays an error message.
     * If the staff member does not have access to the camp, it displays an error message.
     *
     * @param staffAccess the StaffAccess object representing the staff member's access and control over camps and suggestions
     */
    @Override
    public void reviewSuggestions(StaffAccess staffAccess){
        System.out.print("Enter the name of the camp whose Suggestions you want to review : ");
        String camp_name = StaffAccess.sc.nextLine();
        if(staffAccess.campcont.campService.checkCamp(staffAccess.campcont, camp_name)){
            if(staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, camp_name)){
                staffAccess.sugg.suggestionReviewService.execReviewSuggestion(staffAccess.sugg, camp_name);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }

    /**
     * Displays the suggestions for a specific camp.
     * 
     * @param staffAccess the staff access object
     */
    @Override
    public void viewSuggestions(StaffAccess staffAccess){
        System.out.print("Enter the name of the camp whose Suggestions you want to view : ");
        String camp_name = StaffAccess.sc.nextLine();
        if(staffAccess.campcont.campService.checkCamp(staffAccess.campcont, camp_name)){
            if(staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, camp_name)){
                List<String[]> unrepliedSuggestionList = staffAccess.sugg.suggestionReviewService.execFindUnrepliedSuggestion(camp_name);
                staffAccess.sugg.suggestionViewController.formatMessageList(staffAccess.sugg, unrepliedSuggestionList);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }
    
}
