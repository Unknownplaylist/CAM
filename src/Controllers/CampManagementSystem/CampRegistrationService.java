package Controllers.CampManagementSystem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import Controllers.CampManagementSystemInterface.CampRegistrationServiceInterface;
import Models.Camp;
import Models.Student;

public class CampRegistrationService implements CampRegistrationServiceInterface {

    @Override
    public void registerStudentForCamp(CampController campController, String campName, Student student, boolean asCommitteeMember) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
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
            campController.campFileHandler.writeAllCamps(camps); // Write back to storage only if registration happened
            System.out.println("Student successfully registered for " + (asCommitteeMember ? "committee member" : "participant") + " in the camp: " + campName);
        }
    }

    @Override
    public void withdrawStudentFromCamp(CampController campController, Camp camp, Student student) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Read all camps
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
                campController.campFileHandler.writeAllCamps(camps); // Pass the updated camps list
                System.out.println("Student successfully withdrawn from the camp.");
            } else {
                System.out.println("Student was not registered in this camp.");
            }
        } else {
            System.out.println("Camp not found in the list.");
        }
    }

    @Override
    public boolean withdrawStudentFromAttendees(CampController campController, Camp camp, Student student) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController);
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
    
                campController.campFileHandler.writeAllCamps(camps);
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

    @Override
    public void withdrawStudentFromCommittee(CampController campController, Camp camp, Student student) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Read all camps
        Optional<Camp> targetCamp = camps.stream()
                .filter(c -> c.getCampName().equalsIgnoreCase(camp.getCampName()))
                .findFirst();
    
        if (targetCamp.isPresent()) {
            Camp updatedCamp = targetCamp.get();
            boolean removed = updatedCamp.getCommitteeMembers()
                    .removeIf(s -> s.getEmail().equalsIgnoreCase(student.getEmail()));
    
            if (removed) {
                campController.campFileHandler.writeAllCamps(camps); // Update the camps list
                System.out.println("Student successfully withdrawn from the camp committee.");
            } else {
                System.out.println("Student was not registered as a committee member in this camp.");
            }
        } else {
            System.out.println("Camp not found in the list.");
        }
    }

    @Override
    public List<String> viewCampSlots(CampController campController) {
        return campController.campFileHandler.readCamps(campController).stream()
                .map(camp -> camp.getCampName() + ": " +
                        (camp.getTotalSlots() - camp.getRegisteredStudents().size()) +
                        " slots remaining (" + (camp.isVisible() ? "Visible" : "Hidden") + ")")
                .collect(Collectors.toList());
    }
    
}
