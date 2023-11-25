package UI.StudentViewManagement;

import UI.StudentViewManagementInterface.StudentProfileUIInterface;

public class StudentProfileUI implements StudentProfileUIInterface {

    /**
     * Changes the password for the student.
     * Prompts the user to enter a new password and updates it in the student's account.
     * Logs off the student after the password change.
     *
     * @param studentView the StudentView object representing the student's view
     */
    @Override
    public void PasswordChange(StudentView studentView) {
        System.out.print("Enter your new password: ");
        String new_pass = StudentView.sc.next();
        studentView.studentCont.studentService.changePassword(studentView.studentCont, studentView.email, new_pass);
        System.out.println("\nYou will now be logged out.");
        studentView.logOff = 2;
    }
    
}
