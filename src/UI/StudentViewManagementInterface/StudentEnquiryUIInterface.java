package UI.StudentViewManagementInterface;

import UI.StudentViewManagement.StudentView;

public interface StudentEnquiryUIInterface {

    void SubmitEnquiry(StudentView studentView);

    void checkEnquiry(StudentView studentView);

    void removeEnquiry(StudentView studentView);

    void changeEnquiry(StudentView studentView);

}