package Controllers.StaffManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Models.Staff;

public class StaffFileHandler {
    static final String FILE_PATH = "src/Database/staff.csv";
    public List<Staff> readStaff() {
        List<Staff> staffList = new ArrayList<>();
        File file = new File(StaffController.FILE_PATH);
        if (!file.exists()) {
            System.out.println("Staff file does not exist at: " + StaffController.FILE_PATH);
            return staffList;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            //br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(StaffController.CSV_SEPARATOR);
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
            String data = String.join(StaffController.CSV_SEPARATOR, staff.getName(), staff.getEmail(), staff.getFaculty(), staff.getPassword());
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing staff file");
            e.printStackTrace();
        }
    }

    void updateStaffList(List<Staff> staffList) {
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
    }
    
}
