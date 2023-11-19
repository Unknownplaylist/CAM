package Controllers.CampStaffManagement;

import java.util.List;

import Models.Staff;

public class StaffSearchService {

    public Staff getStaffByEmail(StaffController staffController, String email) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff;
            }
        }
        return null;
    }

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
