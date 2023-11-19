package Controllers.LoginManagementInterface;

import Controllers.StaffManagement.StaffController;
import Controllers.StudentManagement.StudentsController;
import UI.Main;

public interface LoginControllerInterface {

    boolean loginUser(String id, String password);

    void UIlogIn(Main main, StudentsController sdc, StaffController sfc);

}