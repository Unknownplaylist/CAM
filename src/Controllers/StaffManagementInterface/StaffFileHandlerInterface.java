package Controllers.StaffManagementInterface;

import java.util.List;

import Models.Staff;

public interface StaffFileHandlerInterface {

    List<Staff> readStaff();

    void writeStaff(Staff staff);

    void updateStaffList(List<Staff> staffList);

}