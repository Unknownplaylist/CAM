package CampAccess.CampCommitteeManagement;

import java.util.List;

import Models.Camp;

public class EnquiryManagementService {

    public void checkEnquiry(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        List<String[]> unrepliedEnquiriesList = committeeAccess.enquiry_controller.staffEnquiryService.execFindUnrepliedEnquiry(committeeAccess.enquiry_controller, camp.getCampName());
        committeeAccess.enquiry_controller.enquiryService.formatMessageList(committeeAccess.enquiry_controller, unrepliedEnquiriesList);
    }

    public void viewEnquiry(CommitteeAccess committeeAccess, String campCommName) {
        Camp camp = committeeAccess.camp_controller.campService.getCampByCommitteeMember(committeeAccess.camp_controller, campCommName);
        if (camp == null) {
            System.out.println("Camp not found.");
            return;
        }
        committeeAccess.enquiry_controller.enquiryService.viewReplyToEnquiry(committeeAccess.enquiry_controller, camp.getCampName());
    }

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
