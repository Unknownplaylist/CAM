package Controllers.EnquiryManagementInterface;

import java.util.List;

import Controllers.EnquiryManagement.EnquiryController;

public interface StudentEnquiryServiceInterface {

    String[] studentEnquiryBasedOnIDandCamp(EnquiryController enquiryController, String student);

    void viewEnquiry(EnquiryController enquiryController, String student);

    List<String[]> studentFindEnquiry(String student);

}