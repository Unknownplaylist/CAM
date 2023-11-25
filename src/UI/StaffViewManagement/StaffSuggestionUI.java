package UI.StaffViewManagement;

import CampAccess.StaffAccessManagement.StaffAccess;
import UI.StaffViewManagementInterface.StaffSuggestionUIInterface;

public class StaffSuggestionUI implements StaffSuggestionUIInterface {

    /**
     * Reviews the suggestions for the given staff view.
     * 
     * @param staffView the staff view to review suggestions for
     */
    @Override
    public void reviewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.reviewSuggestions(staffView.access);
    }

    /**
     * Displays the suggestions for a staff member in the staff view.
     * 
     * @param staffView the staff view object
     */
    @Override
    public void viewSuggestions(StaffView staffView){
        staffView.access = new StaffAccess(staffView.id,staffView.staffcont);
        staffView.access.suggestionReviewService.viewSuggestions(staffView.access);
    }
    
}
