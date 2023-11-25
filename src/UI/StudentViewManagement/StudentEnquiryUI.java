package UI.StudentViewManagement;

import Controllers.EnquiryManagement.EnquiryController;
import UI.StudentViewManagementInterface.StudentEnquiryUIInterface;

public class StudentEnquiryUI implements StudentEnquiryUIInterface {

    /**
     * Submits an enquiry for a student.
     * 
     * @param studentView The student view object.
     */
    @Override
    public void SubmitEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.createEnquiry(studentView.enq, studentView.name);
    }

    /**
     * Checks the enquiry for a student.
     * 
     * @param studentView the student view object
     */
    @Override
    public void checkEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.studentEnquiryService.viewEnquiry(studentView.enq, studentView.name);
    }

    /**
     * Removes the enquiry for the specified student view.
     * 
     * @param studentView the student view object
     */
    @Override
    public void removeEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.deleteEnquiry(studentView.enq, studentView.name);
    }

    /**
     * Changes the enquiry for a student view.
     * 
     * @param studentView the student view to change the enquiry for
     */
    @Override
    public void changeEnquiry(StudentView studentView) {
        studentView.enq = new EnquiryController();
        studentView.enq.enquiryService.editEnquiry(studentView.enq, studentView.name);
    }
    
}
