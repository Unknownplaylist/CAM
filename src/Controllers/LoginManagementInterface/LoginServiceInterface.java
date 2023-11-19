package Controllers.LoginManagementInterface;

import Controllers.LoginManagement.LoginController;

public interface LoginServiceInterface {

    String userType(LoginController loginController, String id);

    boolean verifyCredentials(LoginController loginController, String id, String password);

}