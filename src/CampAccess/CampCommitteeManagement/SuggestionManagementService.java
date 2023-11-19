package CampAccess.CampCommitteeManagement;

import Models.Camp;

public class SuggestionManagementService {

    public void submitSuggestion(CommitteeAccess committeeAccess, String campCommName) { //need to retrieve the camp name when creating the suggestion
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        committeeAccess.suggestion_controller.suggestionService.createSuggestion(committeeAccess.suggestion_controller, campCommName, campName);
    }

    public void viewSuggestion(CommitteeAccess committeeAccess, String campCommName) {
        committeeAccess.suggestion_controller.suggestionViewController.viewSuggestion(committeeAccess.suggestion_controller, campCommName);
    }

    public void editSuggestion(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        committeeAccess.suggestion_controller.suggestionService.editSuggestion(committeeAccess.suggestion_controller, campCommName, campName);
    }

    public void deleteSuggestion(CommitteeAccess committeeAccess, String campCommName) {// to extend the suggestion controller to take in camp
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        String campName = camp.getCampName();
        committeeAccess.suggestion_controller.suggestionService.deleteSuggestion(committeeAccess.suggestion_controller, campCommName, campName);
    }
    
}
