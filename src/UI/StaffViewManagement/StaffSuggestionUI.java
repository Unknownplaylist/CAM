package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffSuggestionUIInterface;

public class StaffSuggestionUI implements StaffSuggestionUIInterface {

    @Override
    public void reviewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.reviewSuggestions(staffView.access);
    }

    @Override
    public void viewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.viewSuggestions(staffView.access);
    }
    
}
