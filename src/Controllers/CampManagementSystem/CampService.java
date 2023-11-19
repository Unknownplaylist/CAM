package Controllers.CampManagementSystem;

import java.util.List;
import java.util.stream.Collectors;

import Controllers.StaffManagement.StaffController;
import Models.Camp;
import Models.Staff;
import Models.Student;

public class CampService {

    public Camp getCamp(CampController campController, String name) {
        return campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> camp.getCampName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkCamp(CampController campController, String name) {
        return campController.campFileHandler.readCamps(campController).stream()
                .anyMatch(camp -> camp.getCampName().equalsIgnoreCase(name));
    }

    public void writeCamp(CampController campController, Camp camp) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
        camps.add(camp);
        campController.campFileHandler.writeAllCamps(camps);
    }

    public boolean hasDateClash(CampController campController, Student student, Camp targetCamp) {
        List<Camp> allCamps = campController.campFileHandler.readCamps(campController);
        for (Camp camp : allCamps) {
            // Check if the student is registered in this camp
            
            if (campController.campService.isStudentRegisteredInCamp(campController, student.getEmail(), camp.getCampName())) {
                
                // Check for date clash with the target camp
                if (targetCamp.getStartDate().equals(camp.getStartDate())) {
        
                    return true; // Date clash found
                }
            }
        }
        return false; // No date clash found
    }

    public void deleteCamp(CampController campController, String campName) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> !camp.getCampName().equalsIgnoreCase(campName))
                .collect(Collectors.toList());
        campController.campFileHandler.writeAllCamps(camps);
    }

    public boolean isYourCamp(CampController campController, String staff_id, String camp_name){
        campController.staffController = new StaffController();
        Camp check_camp=getCamp(campController, camp_name);
        if((check_camp.getStaffInCharge().getName()).equals(campController.staffController.staffService.getStaffName(campController.staffController, campController.staffController.staffService.getStaffMail(staff_id)))){
            return true;
        }
        return false;
    }

    public void toggleCampVisibility(CampController campController, String campName, boolean isVisible) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
        camps.forEach(camp -> {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                camp.setVisible(isVisible);
            }
        });
        campController.campFileHandler.writeAllCamps(camps);
    }

    public List<Camp> viewAllCamps(CampController campController) {
        return campController.campFileHandler.readCamps(campController);
    }

    public List<Camp> viewMyCamps(CampController campController, Staff staff) {
        return campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> camp.getStaffInCharge().equals(staff))
                .collect(Collectors.toList());
    }

    public Camp getCampByCommitteeMember(CampController campController, String studentName) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Assuming this method returns the list of all camps
        for (Camp camp : camps) {
            for (Student committeeMember : camp.getCommitteeMembers()) {
                if (committeeMember.getName().equalsIgnoreCase(studentName)) {
                    return camp;
                }
            }
        }
        return null; // Return null if the student is not a committee member in any camp
    }

    public boolean isStudentRegisteredInCamp(CampController campController, String studentEmail, String campName) {
        try {
            Camp camp = getCamp(campController, campName);
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

    public List<Camp> getCampsByLocation(CampController campController, String location) {
        List<Camp> camps = viewAllCamps(campController).stream()
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

    public List<Camp> getCampsByStartingAlphabet(CampController campController, String startingAlphabet) {
        List<Camp> camps = viewAllCamps(campController).stream()
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

    public List<Camp> getCampsByAttendeeName(CampController campController, String attendeeName) {
        List<Camp> camps = viewAllCamps(campController).stream()
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
    
}
