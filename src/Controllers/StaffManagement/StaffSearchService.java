package Controllers.StaffManagement;

import java.util.List;

import Controllers.StaffManagementInterface.StaffSearchServiceInterface;
import Models.Staff;

public class StaffSearchService implements StaffSearchServiceInterface {

    /**
        * Retrieves the staff member with the specified email address.
        *
        * @param staffController the staff controller object
        * @param email the email address of the staff member to retrieve
        * @return the staff member with the specified email address, or null if not found
        */
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

    /**
        * Retrieves a staff member by their name.
        *
        * @param staffController The staff controller object.
        * @param name The name of the staff member to search for.
        * @return The staff member with the given name, or null if not found.
        */
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
