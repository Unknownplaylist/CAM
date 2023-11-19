package CampAccess.StaffAccessManagementInterface;

import CampAccess.StaffAccessManagement.StaffAccess;

public interface CampManagementServiceInterface {

    void createCamp(StaffAccess staffAccess);

    void editCamp(StaffAccess staffAccess);

    void delCamp(StaffAccess staffAccess);

    void changeVisibility(StaffAccess staffAccess);

    void viewCamps(StaffAccess staffAccess, boolean your);

}