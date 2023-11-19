package UI.StudentViewManagementInterface;

import UI.StudentViewManagement.StudentView;

public interface StudentCampUIInterface {

    void registerForCamp(StudentView studentView);

    void withdrawFromCamp(StudentView studentView);

    void viewCamps(StudentView studentView);

    void viewMyCamps(StudentView studentView);

}