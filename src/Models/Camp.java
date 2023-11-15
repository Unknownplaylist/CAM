package Models;

import java.util.*;
import java.time.LocalDate;
import java.util.List;

public class Camp {
    private String campName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate registrationCloseDate;
    private String userGroup; // "Own School" or "Whole NTU"
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
                LocalDate registrationCloseDate, String userGroup, String location,
                int totalSlots, int committeeSlots, String description, Staff staffInCharge) {
        this.campName = campName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationCloseDate = registrationCloseDate;
        this.userGroup = userGroup;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeSlots = committeeSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
        // Initialize the lists
        this.registeredStudents = new ArrayList<Student>();
        this.committeeMembers = new ArrayList<Student>();
        isVisible=true;
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

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
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
        if (this.registeredStudents.size() < this.totalSlots - this.committeeSlots) {
            this.registeredStudents.add(student);
        } else {
            System.out.println("No available slots for registered students.");
        }
    }

    public void addCommitteeMember(Student student) {
        if (this.committeeMembers.size() < this.committeeSlots) {
            this.committeeMembers.add(student);
        } else {
            System.out.println("No available slots for committee members.");
        }
    }

}

