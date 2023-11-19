package CampAccess.CampCommitteeAccessManagementInterface;

import CampAccess.CampCommitteeAccessManagement.CommitteeAccess;

public interface StudentManagementServiceInterface {

    void generateStudentList(CommitteeAccess committeeAccess, String studentEmail);

    void viewPoint(CommitteeAccess committeeAccess, String campCommName);

}