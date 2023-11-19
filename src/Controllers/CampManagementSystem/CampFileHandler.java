package Controllers.CampManagementSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Models.Camp;
import Models.Staff;
import Models.Student;

public class CampFileHandler {

    public List<Camp> readCamps(CampController campController) {
        List<Camp> camps = new ArrayList<>();
        File file = new File(CampController.FILE_PATH);
        if (!file.exists()) {
            System.out.println("File does not exist at: " + CampController.FILE_PATH);
            return camps;
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // Skipping header line
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(CampController.CSV_SEPARATOR);
                    if (data.length < 13) {
                        System.out.println("Invalid line: " + line);
                        continue;
                    }
    
                    String campName = data[0];
                    LocalDate startDate = LocalDate.parse(data[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    LocalDate endDate = LocalDate.parse(data[2], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    LocalDate registrationCloseDate = LocalDate.parse(data[3], DateTimeFormatter.ofPattern("d/M/yyyy"));
                    String faculty = data[4];
                    String location = data[5];
                    int totalSlots = Integer.parseInt(data[6]);
                    int committeeSlots = Integer.parseInt(data[7]);
                    String description = data[8];
                    Staff staffInCharge = campController.staffController.staffSearchService.getStaffByName(campController.staffController, data[9]);
    
                    List<Student> registeredStudents = Arrays.stream(data[10].split(";"))
                            .map(campController.studentController::getStudentByName) // Assuming getStudentByName is a method in
                                                                      // StudentController
                            .collect(Collectors.toList());
    
                    List<Student> committeeMembers = Arrays.stream(data[11].split(";"))
                            .map(campController.studentController::getStudentByName) // Assuming getStudentByName is a method in
                                                                      // StudentController
                            .collect(Collectors.toList());
    
                    boolean isVisible = Boolean.parseBoolean(data[12]);
    
                    Camp camp = new Camp(campName, startDate, endDate, registrationCloseDate, faculty, location,
                            totalSlots, committeeSlots, description, staffInCharge);
                    camp.setRegisteredStudents(registeredStudents);
                    camp.setCommitteeMembers(committeeMembers);
                    camp.setVisible(isVisible);
    
                    camps.add(camp);
                }
            } catch (IOException | DateTimeParseException | NumberFormatException e) {
                System.out.println("Error reading camps file: " + e.getMessage());
            }
            return camps;
        }
    }

    void writeAllCamps(List<Camp> camps) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CampController.FILE_PATH))) {
            bw.write(
                    "campName,startDate,endDate,registrationCloseDate,Faculty,location,totalSlots,committeeSlots,description,staffInCharge,listregisteredStudents,listcommitteeMembers,Visible\n");
    
            for (Camp camp : camps) {
                String registeredStudents = String.join(";",
                        camp.getRegisteredStudents().stream().map(Student::getName).collect(Collectors.toList()));
                String committeeMembers = String.join(";",
                        camp.getCommitteeMembers().stream().map(Student::getName).collect(Collectors.toList()));
    
                String staffInChargeName = camp.getStaffInCharge().getName(); // camp.getStaffInCharge() != null) ?
                                                                              // camp.getStaffInCharge().getName() : "";
    
                String line = String.join(CampController.CSV_SEPARATOR, Arrays.asList(
                        camp.getCampName(),
                        camp.getStartDate().format(DateTimeFormatter.ofPattern("d/M/yyyy")),
                        camp.getEndDate().format(DateTimeFormatter.ofPattern("d/M/yyyy")),
                        camp.getRegistrationCloseDate().format(DateTimeFormatter.ofPattern("d/M/yyyy")),
                        camp.getFaculty(),
                        camp.getLocation(),
                        String.valueOf(camp.getTotalSlots()),
                        String.valueOf(camp.getCommitteeSlots()),
                        camp.getDescription(),
                        staffInChargeName,
                        registeredStudents,
                        committeeMembers,
                        String.valueOf(camp.isVisible())));
    
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to camps file: " + e.getMessage());
        }
    }
    
}
