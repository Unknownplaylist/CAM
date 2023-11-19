package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;

public class StaffSuggestionUI {

    public void reviewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.reviewSuggestions(staffView.access);
    }

    public void viewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.viewSuggestions(staffView.access);
    }
    
}
