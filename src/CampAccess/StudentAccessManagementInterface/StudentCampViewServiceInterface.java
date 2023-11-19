package CampAccess.StudentAccessManagementInterface;

import CampAccess.StudentAccessManagement.StudentAccess;

public interface StudentCampViewServiceInterface {

    void viewAvailableCamps(StudentAccess studentAccess, String studentEmail);

    void viewMyCamps(StudentAccess studentAccess, String studentEmail);

}