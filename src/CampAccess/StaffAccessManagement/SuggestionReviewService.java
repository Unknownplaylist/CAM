package CampAccess.StaffAccessManagement;

import java.util.List;

import CampAccess.StaffAccessManagementInterface.SuggestionReviewServiceInterface;

public class SuggestionReviewService implements SuggestionReviewServiceInterface {

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
