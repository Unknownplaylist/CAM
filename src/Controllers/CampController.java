package Controllers;

import Models.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class CampController {
    private static final String FILE_PATH = "Database\\camps.csv";
    private static final String CSV_SEPARATOR = ",";
    StaffController staffcont;

    public CampController() {
        staffcont=new StaffController();
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
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(CSV_SEPARATOR);
                    // for(int i=0;i<data.length;i++)
                    //      System.out.println(i+"-"+data[i]);
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
                        Staff staffInCharge = staffcont.getStaffByEmail(data[9]+"@NTU.EDU.SG");
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

    public Camp getCamp(String name){
        List <Camp> camps = readCamps();
        for(Camp camp:camps){
            if(name.equalsIgnoreCase(camp.getCampName()))
                return camp;
        }
        return null;
    }

    public boolean checkCamp(String name){
        List <Camp> camps = readCamps();
        for(Camp camp:camps){
            if(camp.getCampName().equalsIgnoreCase(name))
                return true;
        }
        return false;
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
                    camp.getStaffInCharge().getUserId(camp.getStaffInCharge().getEmail()) + CSV_SEPARATOR +
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
            if (camps.get(i).getCampName().equalsIgnoreCase(campName)) {
                camps.set(i, updatedCamp);
                break;
            }
        }
        writeAllCamps(camps);
    }

    private void writeAllCamps(List<Camp> camps) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write the header (first line)
            bw.write("campName,startDate,endDate,registrationCloseDate,userGroup,location,totalSlots,committeeSlots,description,staffInCharge,visible");
            bw.newLine();
    
            // Write the data starting from the second line
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
                        camp.getStaffInCharge().getUserId(camp.getStaffInCharge().getEmail()) + CSV_SEPARATOR +
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
                .filter(camp -> !camp.getCampName().equalsIgnoreCase(campName))
                .collect(Collectors.toList());
        writeAllCamps(updatedCamps);
    }

    public void toggleCampVisibility(String campName, boolean isVisible) {
        List<Camp> camps = readCamps();
        for (Camp camp : camps) {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                camp.setVisible(isVisible);
                break;
            }
        }
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
        if (student.hasWithdrawnFromCamp(campName)) {
            System.out.println("Student " + student.getName() + " cannot re-register for the camp " + campName + " as they have previously withdrawn from it.");
            return;
        }
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

        // if (student.hasWithdrawnFromCamp(camp.getCampName())) {
        //     System.out.println("Student " + student.getName() + " cannot re-register for the camp " + camp.getCampName() + " as they have previously withdrawn from it.");
        //     return;
        // }

        if (camp.getRegisteredStudents().remove(student)) {
            student.addWithdrawnCamp(camp.getCampName());
            System.out.println("Student " + student.getName() + " has been withdrawn from " + camp.getCampName());
            updateCamp(camp.getCampName(), camp);
        } else {
            System.out.println("Student " + student.getName() + " is not registered in " + camp.getCampName());
        }
    }


  

}

