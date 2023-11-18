package Controllers;

import Models.Camp;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.Comparator;


public class CampController {
    private static final String FILE_PATH = "src/Database/camps.csv";
    private static final String CSV_SEPARATOR = ",";

    public CampController() {
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
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(CSV_SEPARATOR);
                    if (data.length < 11) {
                        System.out.println("Invalid line: " + line);
                    } else {
                        String campName = data[0];
                        LocalDate startDate = LocalDate.parse(data[1]);
                        LocalDate endDate = LocalDate.parse(data[2]);
                        LocalDate registrationCloseDate = LocalDate.parse(data[3]);
                        String userGroup = data[4];
                        String location = data[5];
                        int totalSlots = Integer.parseInt(data[6]);
                        int committeeSlots = Integer.parseInt(data[7]);
                        String description = data[8];
                        Staff staffInCharge = new Staff(data[9]);
                        boolean isVisible = Boolean.parseBoolean(data[10]);

                        Camp camp = new Camp(campName, startDate, endDate, registrationCloseDate, userGroup, location, totalSlots, committeeSlots, description, staffInCharge);
                        camp.setVisible(isVisible);

                        camps.add(camp);
                    }
                }
            } catch (IOException | DateTimeParseException | NumberFormatException e) {
                System.out.println("Error reading camps file: " + e.getMessage());
                e.printStackTrace();
            }
            return camps;
        }
    }
    public void createCamp(String campName, LocalDate startDate, LocalDate endDate, LocalDate registrationCloseDate, String userGroup, String location, int totalSlots, int committeeSlots, String description, Staff staffInCharge) {
        Camp newCamp = new Camp(campName, startDate, endDate, registrationCloseDate, userGroup, location, totalSlots, committeeSlots, description, staffInCharge);

        List<Camp> camps = readCamps();
        camps.add(newCamp);

        writeAllCamps(camps);
    }



    public void writeCamp(Camp camp) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String data = camp.getCampName() + CSV_SEPARATOR +
                    camp.getStartDate() + CSV_SEPARATOR +
                    camp.getEndDate() + CSV_SEPARATOR +
                    camp.getRegistrationCloseDate() + CSV_SEPARATOR +
                    camp.getUserGroup() + CSV_SEPARATOR +
                    camp.getLocation() + CSV_SEPARATOR +
                    camp.getTotalSlots() + CSV_SEPARATOR +
                    camp.getCommitteeSlots() + CSV_SEPARATOR +
                    camp.getDescription() + CSV_SEPARATOR +
                    camp.getStaffInCharge().getUserId() + CSV_SEPARATOR +
                    camp.isVisible();
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing camps file");
            e.printStackTrace();
        }
    }

    public void updateCamp(String campName, Camp updatedCamp) {
        List<Camp> camps = readCamps();
        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName().equals(campName)) {
                camps.set(i, updatedCamp);
                break;
            }
        }
        writeAllCamps(camps);
    }

    private void writeAllCamps(List<Camp> camps) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Camp camp : camps) {
                String data = camp.getCampName() + CSV_SEPARATOR +
                        camp.getStartDate() + CSV_SEPARATOR +
                        camp.getEndDate() + CSV_SEPARATOR +
                        camp.getRegistrationCloseDate() + CSV_SEPARATOR +
                        camp.getUserGroup() + CSV_SEPARATOR +
                        camp.getLocation() + CSV_SEPARATOR +
                        camp.getTotalSlots() + CSV_SEPARATOR +
                        camp.getCommitteeSlots() + CSV_SEPARATOR +
                        camp.getDescription() + CSV_SEPARATOR +
                        camp.getStaffInCharge().getUserId() + CSV_SEPARATOR +
                        camp.isVisible();
                bw.write(data);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing camps file");
            e.printStackTrace();
        }
    }

    public void deleteCamp(String campName) {
        List<Camp> camps = readCamps();
        List<Camp> updatedCamps = camps.stream()
                .filter(camp -> !camp.getCampName().equals(campName))
                .collect(Collectors.toList());
        writeAllCamps(updatedCamps);
    }
    public void toggleCampVisibility(String campName, boolean isVisible) {
        List<Camp> camps = readCamps();
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                camp.setVisible(isVisible);
                break;
            }
        }
        writeAllCamps(camps);
    }

    public List<Camp> viewAllCamps() {
        return readCamps();
    }
    public List<Camp> viewAllCamps(LocalDate startDate, LocalDate endDate, String location) {
        return filterCamps(startDate, endDate, location);
    }

    public List<Camp> viewMyCamps(Staff staff) {
        return readCamps().stream()
                .filter(camp -> camp.getStaffInCharge().equals(staff))
                .collect(Collectors.toList());
    }
    public List<Camp> viewMyCamps(Staff staff, LocalDate startDate, LocalDate endDate, String location) {
        return filterCamps(startDate, endDate, location).stream()
                .filter(camp -> camp.getStaffInCharge().equals(staff))
                .collect(Collectors.toList());
    }
    public List<Camp> viewCampsForStudent(Student student) {
        return readCamps().stream()
                .filter(camp -> camp.isVisible() && camp.getUserGroup().equals(student.getUserGroup()))
                .collect(Collectors.toList());
    }

    public List<Camp> viewCampsForStudents(Student student, LocalDate startDate, LocalDate endDate, String location) {
        return filterCamps(startDate, endDate, location).stream()
                .filter(camp -> camp.isVisible() && camp.getUserGroup().equals(student.getUserGroup()))
                .collect(Collectors.toList());
    }


    public void registerStudentForCamp(String campName, Student student, boolean asCommitteeMember) {
        List<Camp> camps = readCamps();
        for (Camp camp : camps) {
            if (camp.getCampName().equals(campName)) {
                if (hasDateClash(student, camp)) {
                    System.out.println("Cannot register for " + campName + ". Dates clash with another registered camp.");
                    return;
                }

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
    private boolean hasDateClash(Student student, Camp newCamp) {
        LocalDate newCampStart = newCamp.getStartDate();
        LocalDate newCampEnd = newCamp.getEndDate();

        for (Camp registeredCamp : student.getRegisteredCamps()) { // Assuming Student class has a method getRegisteredCamps()
            if (newCampStart.isBefore(registeredCamp.getEndDate()) && newCampEnd.isAfter(registeredCamp.getStartDate())) {
                return true;
            }
        }
        return false;
    }
    public List<String> viewCampSlots() {
        List<Camp> camps = readCamps(); // Assumes readCamps() method exists and reads all camps

        return camps.stream()
                .map(camp -> {
                    int availableSlots = camp.getTotalSlots() - camp.getRegisteredStudents().size();
                    String visibilityStatus = camp.isVisible() ? "Visible" : "Hidden";
                    return camp.getCampName() + ": " + availableSlots + " slots remaining (" + visibilityStatus + ")";
                })
                .collect(Collectors.toList());
    }
    public void withdrawStudentFromCamp(Camp camp, Student student) {
        if (camp.getCommitteeMembers().contains(student)) {
            System.out.println("Student " + student.getName() + " cannot withdraw from " + camp.getCampName() + " as they are a committee member.");
            return;
        }

        if (student.hasWithdrawnFromCamp(camp.getCampName())) {
            System.out.println("Student " + student.getName() + " cannot re-register for the camp " + camp.getCampName() + " as they have previously withdrawn from it.");
            return;
        }

        if (camp.getRegisteredStudents().remove(student)) {
            student.addWithdrawnCamp(camp.getCampName());
            System.out.println("Student " + student.getName() + " has been withdrawn from " + camp.getCampName());
            updateCamp(camp.getCampName(), camp);
        } else {
            System.out.println("Student " + student.getName() + " is not registered in " + camp.getCampName());
        }
    }


    // displaying camps for students
    public List<Camp> getCampsForStudent(Student student) {
        List<Camp> eligibleCamps = new ArrayList<>();
        List<Camp> allCamps = readCamps();
        LocalDate currentDate = LocalDate.now(); // Current date

        for (Camp camp : allCamps) {
            if (camp.isVisible() &&
                    camp.getUserGroup().equals(student.getUserGroup()) &&
                    camp.getRegistrationCloseDate().isAfter(currentDate) &&
                    camp.getAvailableSlots() > 0 &&
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



}

