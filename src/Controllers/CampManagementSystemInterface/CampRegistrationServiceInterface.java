package Controllers.CampManagementSystemInterface;

import java.util.List;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;
import Models.Student;

public interface CampRegistrationServiceInterface {

    void registerStudentForCamp(CampController campController, String campName, Student student,
            boolean asCommitteeMember);

    void withdrawStudentFromCamp(CampController campController, Camp camp, Student student);

    boolean withdrawStudentFromAttendees(CampController campController, Camp camp, Student student);

    void withdrawStudentFromCommittee(CampController campController, Camp camp, Student student);

    List<String> viewCampSlots(CampController campController);

}