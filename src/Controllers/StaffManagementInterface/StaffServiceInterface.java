package Controllers.StaffManagementInterface;

import Controllers.StaffManagement.StaffController;
import Models.Staff;

public interface StaffServiceInterface {

    void updateStaff(StaffController staffController, String email, Staff updatedStaff);

    boolean isFirstLogin(StaffController staffController, String email);

    boolean checkPassword(StaffController staffController, String email, String password);

    void changePassword(StaffController staffController, String email, String newPassword);

    String getStaffMail(String id);

    String getStaffFaculty(StaffController staffController, String email);

    String getStaffName(StaffController staffController, String email);

    boolean verifyStaff(StaffController staffController, String email);

}