package Controllers.StaffManagementInterface;

import Controllers.StaffManagement.StaffController;
import Models.Staff;

public interface StaffSearchServiceInterface {

    Staff getStaffByEmail(StaffController staffController, String email);

    Staff getStaffByName(StaffController staffController, String name);

}