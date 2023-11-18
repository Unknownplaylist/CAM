package Models;

import java.util.*;
import java.time.LocalDate;

public class Camp {
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationCloseDate;
    private String faculty; // Updated from userGroup
    private String location;
    private int totalSlots;
    private int committeeSlots; // Max 10
    private String description;
    private Staff staffInCharge;
    private List<Student> registeredStudents;
    private List<Student> committeeMembers;
    private boolean isVisible;

    // Constructors
    public Camp(String campName, LocalDate startDate, LocalDate endDate,
                LocalDate registrationCloseDate, String faculty, String location,
                int totalSlots, int committeeSlots, String description, Staff staffInCharge) {
        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationCloseDate = registrationCloseDate;
        this.faculty = faculty; // Updated
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeSlots = committeeSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
        this.registeredStudents = new ArrayList<>();
        this.committeeMembers = new ArrayList<>();
        isVisible = true;
    }

    // Getters and Setters
    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getRegistrationCloseDate() {
        return registrationCloseDate;
    }

    public void setRegistrationCloseDate(LocalDate registrationCloseDate) {
        this.registrationCloseDate = registrationCloseDate;
    }

     public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public void setTotalSlots(int totalSlots) {
        this.totalSlots = totalSlots;
    }

    public int getCommitteeSlots() {
        return committeeSlots;
    }

    public void setCommitteeSlots(int committeeSlots) {
        this.committeeSlots = committeeSlots;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Staff getStaffInCharge() {
        return staffInCharge;
    }

    public void setStaffInCharge(Staff staffInCharge) {
        this.staffInCharge = staffInCharge;
    }

    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    public List<Student> getCommitteeMembers() {
        return committeeMembers;
    }

    public void setCommitteeMembers(List<Student> committeeMembers) {
        this.committeeMembers = committeeMembers;
    }
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void addRegisteredStudent(Student student) {
        
            this.registeredStudents.add(student);
        
    }

    public void addCommitteeMember(Student student) {
       
            this.committeeMembers.add(student);
        
    }

    public boolean isStudentRegistered(Student student) {
    return registeredStudents.contains(student) || committeeMembers.contains(student);
}
@Override
public String toString() {
    return "Camp Name: " + campName + "\n" +
           "Start Date: " + startDate + "\n" +
           "End Date: " + endDate + "\n" +
           "Registration Close Date: " + registrationCloseDate + "\n" +
           "Faculty: " + faculty + "\n" + 
           "Location: " + location + "\n" +
           "Total Slots: " + totalSlots + "\n" +
           "Committee Slots: " + committeeSlots + "\n" +
           "Description: " + description + "\n" +
           "Staff In Charge: " + staffInCharge.getName();
}


}

