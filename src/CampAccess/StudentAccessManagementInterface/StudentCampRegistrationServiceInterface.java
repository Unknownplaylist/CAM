package CampAccess.StudentAccessManagementInterface;

import CampAccess.StudentAccessManagement.StudentAccess;

public interface StudentCampRegistrationServiceInterface {

    void registerForCamp(StudentAccess studentAccess, String studentEmail, String campName, boolean asCommitteeMember);

    void withdrawFromCamp(StudentAccess studentAccess, String studentEmail, String campName);

}