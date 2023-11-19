package CampAccess.StudentAccessManagementInterface;

import CampAccess.StudentAccessManagement.StudentAccess;

public interface StudentProfileServiceInterface {

    void updateStudentProfile(StudentAccess studentAccess, String email, String newName, String newFaculty);

}