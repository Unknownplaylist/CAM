package Controllers.CampManagementSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import Controllers.CampManagementSystemInterface.CampReportingServiceInterface;
import Models.Camp;
import Models.Student;

public class CampReportingService implements CampReportingServiceInterface {

    /**
     * Creates a staff report based on the given list of camps and writes it to the specified output path.
     * The staff report includes information such as camp name, start date, end date, registration close date,
     * faculty, location, total slots, committee slots, attendees, camp committee, and visibility.
     *
     * @param camps      the list of camps to include in the staff report
     * @param outputPath the path where the staff report will be written
     */
    @Override
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
    
}
