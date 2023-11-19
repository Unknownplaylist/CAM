package Controllers.EnquiryManagementInterface;

import java.util.List;

import Controllers.EnquiryManagement.EnquiryController;

public interface EnquiryServiceInterface {

    void formatMessageList(EnquiryController enquiryController, List<String[]> enquiryList);

    void formatMessage(String[] data);

    void viewReplyToEnquiry(EnquiryController enquiryController, String camp);

    void deleteEnquiry(EnquiryController enquiryController, String student);

    void editEnquiry(EnquiryController enquiryController, String student);

    void createEnquiry(EnquiryController enquiryController, String student);

}