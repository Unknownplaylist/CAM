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
        for(int i=0;i<camps.size();i++){
            if(updatedCamp.getCampName().equalsIgnoreCase(camps.get(i).getCampName())){
                camps.get(i).setStartDate(updatedCamp.getStartDate());
                camps.get(i).setEndDate(updatedCamp.getEndDate());
                camps.get(i).setRegistrationCloseDate(updatedCamp.getRegistrationCloseDate());
                camps.get(i).setLocation(updatedCamp.getLocation());
                camps.get(i).setDescription(updatedCamp.getDescription());
            }
        }

        writeAllCamps(camps);
    }

    public void deleteCamp(String campName) {
        List<Camp> camps = readCamps().stream()
                .filter(camp -> !camp.getCampName().equalsIgnoreCase(campName))
                .collect(Collectors.toList());
        writeAllCamps(camps);
    }

    public boolean isYourCamp(String staff_id,String camp_name){
        staffController = new StaffController();
        Camp check_camp=getCamp(camp_name);
        if((check_camp.getStaffInCharge().getName()).equals(staffController.getStaffName(staffController.getStaffMail(staff_id)))){
            return true;
        }
        return false;
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
        boolean isRegistered = false; // Flag to check if registration happened
    
        for (Camp camp : camps) {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                if (asCommitteeMember) {
                    int committeeSlots = camp.getCommitteeSlots();
                    if (committeeSlots > 0) { // Check if committee slots are available
                        camp.addCommitteeMember(student);
                        camp.setCommitteeSlots(committeeSlots - 1);
                        isRegistered = true;
                    } else {
                        System.out.println("No committee slots available in this camp.");
                        break; // Exit the loop if no committee slots are available
                    }
                } else {
                    int totalSlots = camp.getTotalSlots();
                    if (totalSlots > 0) { // Check if total slots are available
                        camp.addRegisteredStudent(student);
                        camp.setTotalSlots(totalSlots - 1);
                        isRegistered = true;
                    } else {
                        System.out.println("No slots available in this camp.");
                        break; // Exit the loop if no total slots are available
                    }
                }
                break; // Break the loop as the camp has been found
            }
        }
    
        if (isRegistered) {
            writeAllCamps(camps); // Write back to storage only if registration happened
            System.out.println("Student successfully registered for " + (asCommitteeMember ? "committee member" : "participant") + " in the camp: " + campName);
        }
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
            boolean removedFromRegistered = updatedCamp.getRegisteredStudents()
                    .removeIf(s -> s.getName().equalsIgnoreCase(student.getName()));
            boolean removedFromCommittee = updatedCamp.getCommitteeMembers()
                    .removeIf(s -> s.getName().equalsIgnoreCase(student.getName()));

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

    public boolean withdrawStudentFromAttendees(Camp camp, Student student) {
        List<Camp> camps = readCamps();
        Optional<Camp> targetCamp = camps.stream()
                                         .filter(c -> c.getCampName().equalsIgnoreCase(camp.getCampName()))
                                         .findFirst();
    
        if (targetCamp.isPresent()) {
            Camp updatedCamp = targetCamp.get();
            boolean removed = updatedCamp.getRegisteredStudents()
                                         .removeIf(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));
    
            if (removed) {
                int totalSlots = updatedCamp.getTotalSlots();
                updatedCamp.setTotalSlots(totalSlots + 1);
    
                writeAllCamps(camps);
                System.out.println("Student successfully withdrawn from the camp attendees.");
                return true;  // Indicate successful removal
            } else {
                System.out.println("Student was not registered as an attendee in this camp.");
            }
        } else {
            System.out.println("Camp not found in the list.");
        }
        return false; // Indicate unsuccessful removal or camp not found
    }
    
    

    public void withdrawStudentFromCommittee(Camp camp, Student student) {
        List<Camp> camps = readCamps(); // Read all camps
        Optional<Camp> targetCamp = camps.stream()
                .filter(c -> c.getCampName().equalsIgnoreCase(camp.getCampName()))
                .findFirst();

        if (targetCamp.isPresent()) {
            Camp updatedCamp = targetCamp.get();
            boolean removed = updatedCamp.getCommitteeMembers()
                    .removeIf(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));

            if (removed) {
                writeAllCamps(camps); // Update the camps list
                System.out.println("Student successfully withdrawn from the camp committee.");
            } else {
                System.out.println("Student was not registered as a committee member in this camp.");
            }
        } else {
            System.out.println("Camp not found in the list.");
        }
    }

    private void writeAllCamps(List<Camp> camps) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write(
                    "campName,startDate,endDate,registrationCloseDate,Faculty,location,totalSlots,committeeSlots,description,staffInCharge,listregisteredStudents,listcommitteeMembers,Visible\n");

            for (Camp camp : camps) {
                String registeredStudents = String.join(";",
                        camp.getRegisteredStudents().stream().map(Student::getName).collect(Collectors.toList()));
                String committeeMembers = String.join(";",
                        camp.getCommitteeMembers().stream().map(Student::getName).collect(Collectors.toList()));

                String staffInChargeName = camp.getStaffInCharge().getName(); // camp.getStaffInCharge() != null) ?
                                                                              // camp.getStaffInCharge().getName() : "";

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
                        String.valueOf(camp.isVisible())));

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to camps file: " + e.getMessage());
        }
    }

    public Camp getCampByCommitteeMember(String studentName) {
        List<Camp> camps = readCamps(); // Assuming this method returns the list of all camps
        for (Camp camp : camps) {
            for (Student committeeMember : camp.getCommitteeMembers()) {
                if (committeeMember.getName().equalsIgnoreCase(studentName)) {
                    return camp;
                }
            }
        }
        return null; // Return null if the student is not a committee member in any camp
    }

    public boolean isStudentRegisteredInCamp(String studentEmail, String campName) {
        try {
            Camp camp = getCamp(campName);
            if (camp == null) {
                return false; // Camp not found
            }

            boolean isRegistered = camp.getRegisteredStudents().stream()
                    .anyMatch(student -> student.getEmail().equalsIgnoreCase(studentEmail));
            boolean isCommitteeMember = camp.getCommitteeMembers().stream()
                    .anyMatch(student -> student.getEmail().equalsIgnoreCase(studentEmail));

            return isRegistered || isCommitteeMember;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return false;
        }
    }

    public List<Camp> getCampsByLocation(String location) {
        List<Camp> camps = viewAllCamps().stream()
            .filter(camp -> camp.getLocation().equalsIgnoreCase(location))
            .collect(Collectors.toList());
    
        if (camps.isEmpty()) {
            System.out.println("No camps found at location: " + location);
            return null;
        } 
        else {
            return camps;
        }
    }

    public List<Camp> getCampsByStartingAlphabet(String startingAlphabet) {
        List<Camp> camps = viewAllCamps().stream()
            .filter(camp -> (camp.getCampName().toUpperCase()).startsWith(startingAlphabet.toUpperCase()))
            .collect(Collectors.toList());
    
        if (camps.isEmpty()) {
            System.out.println("No camps found with names starting with : " + startingAlphabet);
            return null;
        } 
        else 
        {
            return camps;
        }
    }

    public List<Camp> getCampsByAttendeeName(String attendeeName) {
        List<Camp> camps = viewAllCamps().stream()
            .filter(camp -> camp.getRegisteredStudents().stream()
                .anyMatch(student -> student.getName().equalsIgnoreCase(attendeeName)))
            .collect(Collectors.toList());
    
        if (camps.isEmpty()) {
            System.out.println("No camps found for attendee with name: " + attendeeName);
            return null;
        } 
        else {
            return camps;
        }
    }

    public void createStaffReport(List<Camp> camps, String outputPath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath))) {
            // Write header
            bw.write("Camp Name,Start Date,End Date,Registration Close Date,Faculty,Location,Total Slots,Committee Slots,Attendees,Camp Committee,Visible");
            bw.newLine();

            
            for (Camp camp : camps) {
                String registeredStudents = String.join(";",
                        camp.getRegisteredStudents().stream().map(Student::getName).collect(Collectors.toList()));
                String committeeMembers = String.join(";",
                        camp.getCommitteeMembers().stream().map(Student::getName).collect(Collectors.toList()));
                String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%d,%d,\"%s\",\"%s\",%b",
                        camp.getCampName(),
                        camp.getStartDate(),
                        camp.getEndDate(),
                        camp.getRegistrationCloseDate(),
                        camp.getFaculty(),
                        camp.getLocation(),
                        camp.getTotalSlots(),
                        camp.getCommitteeSlots(),
                        registeredStudents,
                        committeeMembers,
                        camp.isVisible());

                bw.write(line);
                bw.newLine();
            }

            System.out.println("Staff report created successfully at: " + outputPath);
        } catch (IOException e) {
            System.out.println("Error creating staff report: " + e.getMessage());
        }
    }

    public void sortCampsAlphabeticallyAndWrite() {
        List<Camp> camps = readCamps(); // Method to read camps from CSV

        // Sorting the camps by name
        camps.sort(Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER));

        // Write the sorted list back to the CSV
        writeAllCamps(camps);
    }

    public void sortCampsByStartDateAndWrite() {
        List<Camp> camps = readCamps(); // Method to read camps from CSV

        // Sorting the camps by start date
        camps.sort(Comparator.comparing(Camp::getStartDate));

        // Write the sorted list back to the CSV
        writeAllCamps(camps);
    }

    public void sortCampsByEndDateAndWrite() {
        List<Camp> camps = readCamps(); // Method to read camps from CSV

        // Sorting the camps by End date
        camps.sort(Comparator.comparing(Camp::getEndDate));

        // Write the sorted list back to the CSV
        writeAllCamps(camps);
    }

    public void sortCampsLocationAndWrite() {
        List<Camp> camps = readCamps(); // Method to read camps from CSV

        // Sorting the Location by name
        camps.sort(Comparator.comparing(Camp::getLocation, String.CASE_INSENSITIVE_ORDER));

        // Write the sorted list back to the CSV
        writeAllCamps(camps);
    }

  
    public boolean hasDateClash(Student student, Camp targetCamp) {
        List<Camp> allCamps = readCamps();
        for (Camp camp : allCamps) {
            // Check if the student is registered in this camp
            
            if (isStudentRegisteredInCamp(student.getEmail(), camp.getCampName())) {
                
                // Check for date clash with the target camp
                if (targetCamp.getStartDate().equals(camp.getStartDate())) {
        
                    return true; // Date clash found
                }
            }
        }
        return false; // No date clash found
    }
    
}
