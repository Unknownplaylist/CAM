package UI.StudentViewManagement;

import UI.StudentViewManagementInterface.StudentProfileUIInterface;

public class StudentProfileUI implements StudentProfileUIInterface {

    @Override
    public void PasswordChange(StudentView studentView) {
        System.out.print("Enter your new password: ");
        String new_pass = StudentView.sc.next();
        studentView.studentCont.studentService.changePassword(studentView.studentCont, studentView.email, new_pass);
        System.out.println("\nYou will now be logged out.");
        studentView.logOff = 2;
    }
    
}
