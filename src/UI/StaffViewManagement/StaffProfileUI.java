package UI.StaffViewManagement;

import UI.StaffViewManagementInterface.StaffProfileUIInterface;

public class StaffProfileUI implements StaffProfileUIInterface {

    /**
     * Changes the password for the staff member.
     * Prompts the user to enter a new password and updates it in the staff service.
     * After changing the password, logs off the staff member.
     *
     * @param staffView The StaffView object associated with the staff member.
     */
    @Override
    public void PasswordChange(StaffView staffView){
        try{
            System.out.print("Enter your new password: ");
            String new_pass=StaffView.sc.next();
            staffView.staffcont.staffService.changePassword(staffView.staffcont, staffView.email, new_pass);
            System.out.println("\nYou will now be logged out.");
            staffView.logOff=2;
        }
        catch(Exception e){
            System.out.println("Invalid Input");
            return;
        }
    }
    
}
