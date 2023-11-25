package Controllers.StaffManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Controllers.StaffManagementInterface.StaffServiceInterface;
import Models.Staff;

public class StaffService implements StaffServiceInterface {
    static final String FILE_PATH = "src/Database/staff.csv";
    /**
     * Updates the information of a staff member based on their email.
     * If the staff member with the given email exists, their name, faculty, and password (if needed) will be updated.
     * If the staff member does not exist, a message will be printed indicating that the staff member was not found.
     *
     * @param staffController The StaffController object used to access the staff file handler.
     * @param email The email of the staff member to be updated.
     * @param updatedStaff The updated Staff object containing the new information.
     */
    @Override
    public void updateStaff(StaffController staffController, String email, Staff updatedStaff) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        boolean staffExists = false;
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                staff.setName(updatedStaff.getName());
                staff.setFaculty(updatedStaff.getFaculty());
                staff.setPassword(updatedStaff.getPassword()); // Only update password if needed
                staffExists = true;
                break;
            }
        }
        if (staffExists) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Staff staff : staffList) {
                    String data = String.join(StaffController.CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty(), staff.getPassword());
                    bw.write(data);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error writing staff file");
                e.printStackTrace();
            }
        } else {
            System.out.println("Staff with email " + email + " not found");
        }
    }

    /**
     * Checks if the staff member with the given email is logging in for the first time.
     * 
     * @param staffController the StaffController object
     * @param email the email of the staff member
     * @return true if the staff member is logging in for the first time, false otherwise
     */
    @Override
    public boolean isFirstLogin(StaffController staffController, String email) {
        Staff staff = staffController.staffSearchService.getStaffByEmail(staffController, email);
        return staff != null && staff.getPassword().equals(StaffController.DEFAULT_PASSWORD);
    }

    /**
     * Checks if the provided password matches the password of the staff member with the given email.
     *
     * @param staffController the staff controller object
     * @param email the email of the staff member
     * @param password the password to be checked
     * @return true if the password matches, false otherwise
     */
    @Override
    public boolean checkPassword(StaffController staffController, String email, String password) {
        Staff staff = staffController.staffSearchService.getStaffByEmail(staffController, email);
        return staff != null && staff.getPassword().equals(password);
    }

    /**
     * Changes the password of a staff member identified by their email.
     * 
     * @param staffController The staff controller object.
     * @param email The email of the staff member.
     * @param newPassword The new password to set.
     */
    @Override
    public void changePassword(StaffController staffController, String email, String newPassword) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                staff.setPassword(newPassword);
                staffController.staffFileHandler.updateStaffList(staffList);
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    /**
     * Returns the staff email address based on the provided ID.
     *
     * @param id the ID of the staff
     * @return the staff email address
     */
    @Override
    public String getStaffMail(String id){
        return (id+"@NTU.EDU.SG");
    }

    /**
        * Retrieves the faculty of a staff member based on their email.
        * 
        * @param staffController the staff controller object
        * @param email the email of the staff member
        * @return the faculty of the staff member, or "Staff not found" if the staff member is not found
        */
    @Override
    public String getStaffFaculty(StaffController staffController, String email) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff.getFaculty();
            }
        }
        return "Staff not found";
    }

    /**
        * Retrieves the name of a staff member based on their email.
        *
        * @param staffController The staff controller object.
        * @param email The email of the staff member.
        * @return The name of the staff member if found, otherwise "Staff not found".
        */
    @Override
    public String getStaffName(StaffController staffController, String email) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff.getName();
            }
        }
        return "Staff not found";
    }

    /**
     * Verifies if a staff member with the given email exists in the staff list.
     *
     * @param staffController the staff controller object
     * @param email the email to be verified
     * @return true if a staff member with the given email exists, false otherwise
     */
    @Override
    public boolean verifyStaff(StaffController staffController, String email) {
        List<Staff> staffList = staffController.staffFileHandler.readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    
}
