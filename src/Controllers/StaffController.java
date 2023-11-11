package Controllers;

import java.io.*;
import java.util.*;
import Models.*;

public class StaffController {
    private static final String FILE_PATH = "src/Database/staff.csv";
    private static final String CSV_SEPARATOR = ",";
    private static final String DEFAULT_PASSWORD = "password";

    public List<Staff> readStaff() {
        List<Staff> staffList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Staff file does not exist at: " + FILE_PATH);
            return staffList;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            //br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                if (data.length < 4) {
                    System.out.println("Invalid staff data: " + line);
                    continue;
                }
                Staff staff = new Staff(data[0], data[1], data[2], data[3]); // Assuming order: name, email, faculty, password
                staffList.add(staff);
            }
        } catch (IOException e) {
            System.out.println("Error reading staff file");
            e.printStackTrace();
        }
        return staffList;
    }

    public void writeStaff(Staff staff) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String data = String.join(CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty(), staff.getPassword());
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing staff file");
            e.printStackTrace();
        }
    }

    public void updateStaff(String email, Staff updatedStaff) {
        List<Staff> staffList = readStaff();
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
                    String data = String.join(CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty(), staff.getPassword());
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

    public boolean isFirstLogin(String email) {
        Staff staff = getStaffByEmail(email);
        return staff != null && staff.getPassword().equalsIgnoreCase(DEFAULT_PASSWORD);
    }

    public boolean checkPassword(String email, String password) {
        Staff staff = getStaffByEmail(email);
        return staff != null && staff.getPassword().equalsIgnoreCase(password);
    }

    public void changePassword(String email, String newPassword) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                staff.setPassword(newPassword);
                updateStaffList(staffList);
                return;
            }
        }
        System.out.println("Staff not found.");
    }

    private Staff getStaffByEmail(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff;
            }
        }
        return null;
    }

    private void updateStaffList(List<Staff> staffList) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Staff staff : staffList) {
                String data = String.join(CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty(), staff.getPassword());
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing staff file");
            e.printStackTrace();
        }
    }
    

    public String getStaffMail(String id){
        return (id+"@NTU.EDU.SG");
    }
    
    public String getStaffFaculty(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff.getFaculty();
            }
        }
        return "Staff not found";
    }
    
    public String getStaffName(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return staff.getName();
            }
        }
        return "Staff not found";
    }
    
    public boolean verifyStaff(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
