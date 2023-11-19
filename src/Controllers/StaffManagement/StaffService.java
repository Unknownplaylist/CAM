package Controllers.StaffManagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import Controllers.StaffManagementInterface.StaffServiceInterface;
import Models.Staff;

public class StaffService implements StaffServiceInterface {
    static final String FILE_PATH = "src/Database/staff.csv";
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

    @Override
    public boolean isFirstLogin(StaffController staffController, String email) {
        Staff staff = staffController.staffSearchService.getStaffByEmail(staffController, email);
        return staff != null && staff.getPassword().equals(StaffController.DEFAULT_PASSWORD);
    }

    @Override
    public boolean checkPassword(StaffController staffController, String email, String password) {
        Staff staff = staffController.staffSearchService.getStaffByEmail(staffController, email);
        return staff != null && staff.getPassword().equals(password);
    }

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

    @Override
    public String getStaffMail(String id){
        return (id+"@NTU.EDU.SG");
    }

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
