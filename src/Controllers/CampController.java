package Controllers;

import Models.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CampController {
    private static final String FILE_PATH = "src/Database/CampInformation.csv";
    private static final String CSV_SEPARATOR = ",";
    private StudentsController studentController;
    private StaffController staffController;

    public CampController(StudentsController studentController, StaffController staffController) {
        this.studentController = studentController;
        this.staffController = staffController;
    }

    public List<Camp> readCamps() {
        List<Camp> camps = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("File does not exist at: " + FILE_PATH);
            return camps;
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                br.readLine(); // Skipping header line
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(CSV_SEPARATOR);
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
                    Staff staffInCharge = staffController.getStaffByName(data[9]);

                    List<Student> registeredStudents = Arrays.stream(data[10].split(";"))
                            .map(studentController::getStudentByName) // Assuming getStudentByName is a method in
                                                                      // StudentController
                            .collect(Collectors.toList());

                    List<Student> committeeMembers = Arrays.stream(data[11].split(";"))
                            .map(studentController::getStudentByName) // Assuming getStudentByName is a method in
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

    public static void main(String[] args) {
        // Assuming you have constructors for these controllers without parameters
        StudentsController studentController = new StudentsController();
        StaffController staffController = new StaffController();

        // Create CampController with the required controllers
        CampController campController = new CampController(studentController, staffController);

        // Call readCamps method and print the results
        List<Camp> camps = campController.readCamps();
        for (Camp camp : camps) {
            System.out.println("Camp Name: " + camp.getCampName());
            System.out.println("Start Date: " + camp.getStartDate());
            System.out.println("End Date: " + camp.getEndDate());
            // Continue printing other details of the camp as needed
            // ...
            System.out.println("Registered Students: ");
            camp.getRegisteredStudents().forEach(student -> System.out.println(student));
            System.out.println("Committee Members: ");
            camp.getCommitteeMembers().forEach(student -> System.out.println(student));
            System.out.println("Is Visible: " + camp.isVisible());
            System.out.println("---------------------------------");
        }
    }

    public Camp getCamp(String name) {
        return readCamps().stream()
                .filter(camp -> camp.getCampName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkCamp(String name) {
        return readCamps().stream()
                .anyMatch(camp -> camp.getCampName().equalsIgnoreCase(name));
    }

    public void writeCamp(Camp camp) {
        List<Camp> camps = readCamps();
        camps.add(camp);
        writeAllCamps(camps);
    }

    public void updateCamp(String campName, Camp updatedCamp) {
        List<Camp> camps = readCamps();
        camps = camps.stream()
                .map(camp -> camp.getCampName().equalsIgnoreCase(campName) ? updatedCamp : camp)
                .collect(Collectors.toList());
        writeAllCamps(camps);
    }

    public void deleteCamp(String campName) {
        List<Camp> camps = readCamps().stream()
                .filter(camp -> !camp.getCampName().equalsIgnoreCase(campName))
                .collect(Collectors.toList());
        writeAllCamps(camps);
    }

    public void toggleCampVisibility(String campName, boolean isVisible) {
        List<Camp> camps = readCamps();
        camps.forEach(camp -> {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                camp.setVisible(isVisible);
            }
        });
        writeAllCamps(camps);
    }

    public List<Camp> viewAllCamps() {
        return readCamps();
    }

    public List<Camp> viewMyCamps(Staff staff) {
        return readCamps().stream()
                .filter(camp -> camp.getStaffInCharge().equals(staff))
                .collect(Collectors.toList());
    }

    public void registerStudentForCamp(String campName, Student student, boolean asCommitteeMember) {
        List<Camp> camps = readCamps();
        for (Camp camp : camps) {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                if (asCommitteeMember) {
                    camp.addCommitteeMember(student);
                } else {
                    camp.addRegisteredStudent(student);
                }
                break;
            }
        }
        writeAllCamps(camps);
    }

    public List<String> viewCampSlots() {
        return readCamps().stream()
                .map(camp -> camp.getCampName() + ": " +
                        (camp.getTotalSlots() - camp.getRegisteredStudents().size()) +
                        " slots remaining (" + (camp.isVisible() ? "Visible" : "Hidden") + ")")
                .collect(Collectors.toList());
    }

    public void withdrawStudentFromCamp(Camp camp, Student student) {
        List<Camp> camps = readCamps(); // Read all camps
        Optional<Camp> targetCamp = camps.stream()
                                         .filter(c -> c.getCampName().equalsIgnoreCase(camp.getCampName()))
                                         .findFirst();
    
        if (targetCamp.isPresent()) {
            Camp updatedCamp = targetCamp.get();
            boolean removedFromRegistered = updatedCamp.getRegisteredStudents().removeIf(s -> s.getName().equalsIgnoreCase(student.getName()));
            boolean removedFromCommittee = updatedCamp.getCommitteeMembers().removeIf(s -> s.getName().equalsIgnoreCase(student.getName()));
    
            if (removedFromRegistered || removedFromCommittee) {
                writeAllCamps(camps); // Pass the updated camps list
                System.out.println("Student successfully withdrawn from the camp.");
            } else {
                System.out.println("Student was not registered in this camp.");
            }
        } else {
            System.out.println("Camp not found in the list.");
        }
    }
    
    
    
    

    private void writeAllCamps(List<Camp> camps) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("CampName,StartDate,EndDate,RegistrationCloseDate,Faculty,Location,TotalSlots,CommitteeSlots,Description,StaffInCharge,RegisteredStudents,CommitteeMembers,IsVisible\n");
    
            for (Camp camp : camps) {
                String registeredStudents = String.join(";", camp.getRegisteredStudents().stream().map(Student::getName).collect(Collectors.toList()));
                String committeeMembers = String.join(";", camp.getCommitteeMembers().stream().map(Student::getName).collect(Collectors.toList()));
    
                String staffInChargeName =  camp.getStaffInCharge().getName(); //camp.getStaffInCharge() != null) ? camp.getStaffInCharge().getName() : "";
    
                String line = String.join(CSV_SEPARATOR, Arrays.asList(
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
                    String.valueOf(camp.isVisible())
                ));
    
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to camps file: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    

   /*  public List<Camp> getCampsForStudent(Student student) {
        List<Camp> eligibleCamps = new ArrayList<>();
        List<Camp> allCamps = readCamps();
        LocalDate currentDate = LocalDate.now(); 

        for (Camp camp : allCamps) {
            if (camp.isVisible() &&
                    camp.getFaculty().equals(student.getFaculty()) &&
                    camp.getRegistrationCloseDate().isAfter(currentDate) &&
                    camp.getTotalSlots() > 0 &&
                    !hasDateClash(student, camp)) {
                eligibleCamps.add(camp);
            }
        }

        return eligibleCamps;
    }

    public List<Camp> filterCamps(LocalDate startDate, LocalDate endDate, String location) {
        List<Camp> allCamps = readCamps();

        return allCamps.stream()
                .filter(camp -> (startDate == null || !camp.getStartDate().isBefore(startDate)) &&
                        (endDate == null || !camp.getEndDate().isAfter(endDate)) &&
                        (location == null || camp.getLocation().equalsIgnoreCase(location)))
                .sorted(Comparator.comparing(Camp::getCampName))
                .collect(Collectors.toList());
    }

    private boolean hasDateClash(Student student, Camp camp) {
        // Implement logic to check for date clashes with the student's schedule
        // This can be based on other camps the student is registered for, or any other
        // criteria relevant to the system
        return false; // Placeholder
    }*/
}
