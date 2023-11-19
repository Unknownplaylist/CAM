package Controllers.StaffManagement;

import java.util.List;

import Controllers.StaffManagementInterface.StaffSearchServiceInterface;
import Models.Staff;

public class StaffSearchService implements StaffSearchServiceInterface {

    @Override
    public Staff getStaffByEmail(StaffController staffController, String email) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff;
            }
        }
        return null;
    }

    @Override
    public Staff getStaffByName(StaffController staffController, String name) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getName().equalsIgnoreCase(name)) {
                return staff;
            }
        }
        return null; // Return null if no staff with the given name is found
    }
    
}
