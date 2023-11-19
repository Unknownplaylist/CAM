package UI.StaffViewManagement;

public class StaffProfileUI {

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
