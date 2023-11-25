package Controllers.CampManagementSystem;

import java.util.List;
import java.util.stream.Collectors;

import Controllers.CampManagementSystemInterface.CampServiceInterface;
import Controllers.StaffManagement.StaffController;
import Models.Camp;
import Models.Staff;
import Models.Student;

public class CampService implements CampServiceInterface {

    /**
     * Retrieves a camp with the specified name from the camp controller.
     *
     * @param campController The camp controller object.
     * @param name The name of the camp to retrieve.
     * @return The camp object with the specified name, or null if not found.
     */
    @Override
    public Camp getCamp(CampController campController, String name) {
        return campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> camp.getCampName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if a camp with the given name exists in the camp controller.
     * 
     * @param campController the camp controller
     * @param name the name of the camp to check
     * @return true if a camp with the given name exists, false otherwise
     */
    @Override
    public boolean checkCamp(CampController campController, String name) {
        return campController.campFileHandler.readCamps(campController).stream()
                .anyMatch(camp -> camp.getCampName().equalsIgnoreCase(name));
    }

    /**
     * Writes a camp to the camp file.
     * 
     * @param campController The camp controller object.
     * @param camp The camp to be written.
     */
    @Override
    public void writeCamp(CampController campController, Camp camp) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
        camps.add(camp);
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Checks if a student has a date clash with a target camp.
     * 
     * @param campController the camp controller
     * @param student the student
     * @param targetCamp the target camp
     * @return true if there is a date clash, false otherwise
     */
    @Override
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

    /**
     * Deletes a camp with the specified camp name.
     *
     * @param campController the camp controller object
     * @param campName the name of the camp to be deleted
     */
    @Override
    public void deleteCamp(CampController campController, String campName) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> !camp.getCampName().equalsIgnoreCase(campName))
                .collect(Collectors.toList());
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Checks if the given staff member is in charge of the specified camp.
     * 
     * @param campController The camp controller object.
     * @param staff_id The ID of the staff member.
     * @param camp_name The name of the camp.
     * @return true if the staff member is in charge of the camp, false otherwise.
     */
    @Override
    public boolean isYourCamp(CampController campController, String staff_id, String camp_name){
        campController.staffController = new StaffController();
        Camp check_camp=getCamp(campController, camp_name);
        if((check_camp.getStaffInCharge().getName()).equals(campController.staffController.staffService.getStaffName(campController.staffController, campController.staffController.staffService.getStaffMail(staff_id)))){
            return true;
        }
        return false;
    }

    /**
     * Toggles the visibility of a camp.
     *
     * @param campController The camp controller.
     * @param campName The name of the camp.
     * @param isVisible The visibility status to set for the camp.
     */
    @Override
    public void toggleCampVisibility(CampController campController, String campName, boolean isVisible) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
        camps.forEach(camp -> {
            if (camp.getCampName().equalsIgnoreCase(campName)) {
                camp.setVisible(isVisible);
            }
        });
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Retrieves a list of all camps from the camp controller.
     *
     * @param campController the camp controller object
     * @return a list of all camps
     */
    @Override
    public List<Camp> viewAllCamps(CampController campController) {
        return campController.campFileHandler.readCamps(campController);
    }

    /**
     * Returns a list of camps that are assigned to the specified staff member.
     *
     * @param campController the camp controller object
     * @param staff the staff member
     * @return a list of camps assigned to the staff member
     */
    @Override
    public List<Camp> viewMyCamps(CampController campController, Staff staff) {
        return campController.campFileHandler.readCamps(campController).stream()
                .filter(camp -> camp.getStaffInCharge().equals(staff))
                .collect(Collectors.toList());
    }

    /**
     * Represents a camp.
     */
    @Override
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

    /**
     * Checks if a student is registered in a camp.
     * 
     * @param campController The camp controller.
     * @param studentEmail The email of the student.
     * @param campName The name of the camp.
     * @return true if the student is registered in the camp, false otherwise.
     */
    @Override
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

    /**
     * Retrieves a list of camps based on the specified location.
     *
     * @param campController the camp controller object
     * @param location the location to filter camps by
     * @return a list of camps matching the specified location, or null if no camps are found
     */
    @Override
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

    /**
     * Retrieves a list of camps whose names start with the specified alphabet.
     *
     * @param campController The CampController object used to access the camps.
     * @param startingAlphabet The alphabet used to filter the camps by their names.
     * @return A list of Camp objects whose names start with the specified alphabet.
     *         Returns null if no camps are found.
     */
    @Override
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

    /**
     * Retrieves a list of camps that have an attendee with the specified name.
     *
     * @param campController the camp controller object
     * @param attendeeName the name of the attendee
     * @return a list of camps that have an attendee with the specified name, or null if no camps are found
     */
    @Override
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

    /**
     * Updates a camp with the given camp name and the updated camp details.
     * 
     * @param campController The camp controller object.
     * @param campName The name of the camp to be updated.
     * @param updatedCamp The updated camp object containing the new details.
     */
    @Override
    public void updateCamp(CampController campController, String campName, Camp updatedCamp) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
        for(int i=0;i<camps.size();i++){
            if(updatedCamp.getCampName().equalsIgnoreCase(camps.get(i).getCampName())){
                camps.get(i).setStartDate(updatedCamp.getStartDate());
                camps.get(i).setEndDate(updatedCamp.getEndDate());
                camps.get(i).setRegistrationCloseDate(updatedCamp.getRegistrationCloseDate());
                camps.get(i).setLocation(updatedCamp.getLocation());
                camps.get(i).setDescription(updatedCamp.getDescription());
            }
        }
    
        campController.campFileHandler.writeAllCamps(camps);
    }
    
}
