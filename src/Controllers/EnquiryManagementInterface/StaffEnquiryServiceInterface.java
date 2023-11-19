package Controllers.EnquiryManagementInterface;

import java.util.List;

import Controllers.EnquiryManagement.EnquiryController;

public interface StaffEnquiryServiceInterface {

    void execReplyEnquiry(EnquiryController enquiryController, String camp);

    List<String[]> execFindUnrepliedEnquiry(EnquiryController enquiryController, String camp);

    List<String[]> execFindEnquiry(String camp);

    void replyEnquiry(EnquiryController enquiryController, String[] enquiryToReply);

}