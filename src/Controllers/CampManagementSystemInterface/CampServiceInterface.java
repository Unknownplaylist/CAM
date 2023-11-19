package Controllers.CampManagementSystemInterface;

import java.util.List;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;
import Models.Staff;
import Models.Student;

public interface CampServiceInterface {

    Camp getCamp(CampController campController, String name);

    boolean checkCamp(CampController campController, String name);

    void writeCamp(CampController campController, Camp camp);

    boolean hasDateClash(CampController campController, Student student, Camp targetCamp);

    void deleteCamp(CampController campController, String campName);

    boolean isYourCamp(CampController campController, String staff_id, String camp_name);

    void toggleCampVisibility(CampController campController, String campName, boolean isVisible);

    List<Camp> viewAllCamps(CampController campController);

    List<Camp> viewMyCamps(CampController campController, Staff staff);

    Camp getCampByCommitteeMember(CampController campController, String studentName);

    boolean isStudentRegisteredInCamp(CampController campController, String studentEmail, String campName);

    List<Camp> getCampsByLocation(CampController campController, String location);

    List<Camp> getCampsByStartingAlphabet(CampController campController, String startingAlphabet);

    List<Camp> getCampsByAttendeeName(CampController campController, String attendeeName);

    void updateCamp(CampController campController, String campName, Camp updatedCamp);

}