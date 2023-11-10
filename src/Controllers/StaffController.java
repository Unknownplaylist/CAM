package Controllers;

import java.io.*;
import java.util.*;
import Models.*;

public class StaffController {
    private static final String FILE_PATH = "src/Database/staff.csv";
    private static final String CSV_SEPARATOR = ",";
    
    public List<Staff> readStaff() {
        List<Staff> staffList = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File does not exist at: " + FILE_PATH);
            return staffList;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SEPARATOR);
                if (data.length < 3) {
                    System.out.println("Invalid line: " + line);
                    continue;
                }
                Staff staff = new Staff(data[0], data[1], data[2]);
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
            String data = String.join(CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty());
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
        for (int i = 0; i < staffList.size(); i++) {
            if (staffList.get(i).getEmail().equals(email)) {
                staffList.set(i, updatedStaff);
                staffExists = true;
                break;
            }
        }
        if (staffExists) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Staff staff : staffList) {
                    String data = String.join(CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty());
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

    public String getStaffMail(String id){
        return (id+"@NTU.EDU.SG");
    }
    
    public String getStaffFaculty(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equals(email)) {
                return staff.getFaculty();
            }
        }
        return "Staff not found";
    }
    
    public String getStaffName(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equals(email)) {
                return staff.getName();
            }
        }
        return "Staff not found";
    }
    
    public boolean verifyStaff(String email) {
        List<Staff> staffList = readStaff();
        for (Staff staff : staffList) {
            if (staff.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
