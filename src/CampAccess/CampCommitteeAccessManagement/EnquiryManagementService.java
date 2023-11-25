package CampAccess.CampCommitteeAccessManagement;

import java.util.List;

import CampAccess.CampCommitteeAccessManagementInterface.EnquiryManagementServiceInterface;
import Models.Camp;

public class EnquiryManagementService implements EnquiryManagementServiceInterface {

    /**
     * Checks the enquiry for a committee access and camp committee name.
     * Retrieves the camp based on the committee member's name and checks if it exists.
     * If the camp is not found, it prints "Camp not found" and returns.
     * Retrieves a list of unreplied enquiries for the camp and formats the message list.
     * 
     * @param committeeAccess The committee access object.
     * @param campCommName The name of the camp committee.
     */
    @Override
    public void checkEnquiry(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        List<String[]> unrepliedEnquiriesList = committeeAccess.enquiry_controller.staffEnquiryService.execFindUnrepliedEnquiry(committeeAccess.enquiry_controller, camp.getCampName());
        committeeAccess.enquiry_controller.enquiryService.formatMessageList(committeeAccess.enquiry_controller, unrepliedEnquiriesList);
    }

    /**
     * Displays the details of an enquiry and its reply.
     * 
     * @param committeeAccess The committee access object.
     * @param campCommName The name of the camp committee member.
     */
    @Override
    public void viewEnquiry(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        committeeAccess.enquiry_controller.enquiryService.viewReplyToEnquiry(committeeAccess.enquiry_controller, camp.getCampName());
    }

    /**
     * Replies to an enquiry made by a committee member.
     * 
     * @param committeeAccess The committee access object.
     * @param campCommName The name of the committee member.
     */
    @Override
    public void replyEnquiry(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        committeeAccess.enquiry_controller.staffEnquiryService.execReplyEnquiry(committeeAccess.enquiry_controller, camp.getCampName());
        //add one point for every reply to enquiry
        //Student student = student_controller.getStudentByName(campCommName);
        committeeAccess.student.addOnePoint();
    }
    
}
