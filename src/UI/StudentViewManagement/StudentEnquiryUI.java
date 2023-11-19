package UI.StudentViewManagement;

import Controllers.EnquiryManagement.EnquiryController;
import UI.StudentViewManagementInterface.StudentEnquiryUIInterface;

public class StudentEnquiryUI implements StudentEnquiryUIInterface {

    @Override
    public void SubmitEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.createEnquiry(studentView.enq, studentView.name);
    }

    @Override
    public void checkEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.studentEnquiryService.viewEnquiry(studentView.enq, studentView.name);
    }

    @Override
    public void removeEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.deleteEnquiry(studentView.enq, studentView.name);
    }

    @Override
    public void changeEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.editEnquiry(studentView.enq, studentView.name);
    }
    
}
