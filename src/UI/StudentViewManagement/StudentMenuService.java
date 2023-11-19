package UI.StudentViewManagement;

public class StudentMenuService {

    public void display(StudentView studentView) {
        studentView.logOff = 0;
        do {
            System.out.println(studentView.name + "\n" + studentView.faculty + "\n");
            if (studentView.studentCont.studentAuthenticationService.isFirstLogin(studentView.studentCont, studentView.email) && studentView.logOff == 0) {
                System.out.println("Your account is not secure - Change from the Default Password\n");
                studentView.logOff = 1;
            }
            System.out.println(" Menu ");
            System.out.println("======");
            System.out.println("(1) View Camps");
            System.out.println("(2) View your Camps");
            System.out.println("(3) Register for Camp");
            System.out.println("(4) Register as Camp Committee");
            System.out.println("(5) Withdraw");
            System.out.println("(6) Submit Enquiries");
            System.out.println("(7) Check Enquiries");
            System.out.println("(8) Delete Enquiries");
            System.out.println("(9) Edit Enquiries\n");
            System.out.println("(10) Change your password");
            System.out.println("(11) LogOff\n");
            System.out.print("In: ");
            int choice = StudentView.sc.nextInt();
            switch (choice) {
                case 1:
                    studentView.studentCampUI.viewCamps(studentView);
                    break;
                case 2:
                    studentView.studentCampUI.viewMyCamps(studentView);
                    break;
                case 3:
                studentView.studentCampUI.registerForCamp(studentView);
                    break;    
                case 4:
                    studentView.studentCommitteeRegistrationUI.registerAsCampCommittee(studentView);
                    break;
                case 5:
                    studentView.studentCampUI.withdrawFromCamp(studentView);
                    break;
                case 6:
                    studentView.studentEnquiryUI.SubmitEnquiry(studentView);
                    break;
                case 7:
                    studentView.studentEnquiryUI.checkEnquiry(studentView);
                    break;
                case 8:
                    studentView.studentEnquiryUI.removeEnquiry(studentView);
                    break;
                case 9:
                    studentView.studentEnquiryUI.changeEnquiry(studentView);
                    break;
                case 10:
                    studentView.studentProfileUI.PasswordChange(studentView);
                    break;
                case 11:
                    if (studentView.studentCont.studentAuthenticationService.isFirstLogin(studentView.studentCont, studentView.email)) {
                        System.out.println("Kindly Change your password\n");
                        continue;
                    } else {
                        studentView.logOff = 2;
                        System.out.println("Logging Off...");
                    }
                    break;
            }
            System.out.println("\n");
        } while (studentView.logOff != 2);
    }
    
}
