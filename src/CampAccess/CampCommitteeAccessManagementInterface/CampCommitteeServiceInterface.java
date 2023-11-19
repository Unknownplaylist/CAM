package CampAccess.CampCommitteeAccessManagementInterface;

import CampAccess.CampCommitteeAccessManagement.CommitteeAccess;

public interface CampCommitteeServiceInterface {

    void viewAvailableCamps(CommitteeAccess committeeAccess, String studentEmail);

    void viewMyCamps(CommitteeAccess committeeAccess, String studentName);

    void registerForCamp(CommitteeAccess committeeAccess, String studentEmail, String campName,
            boolean asCommitteeMember);

    void withdrawFromCamp(CommitteeAccess committeeAccess, String studentEmail, String campName);

}