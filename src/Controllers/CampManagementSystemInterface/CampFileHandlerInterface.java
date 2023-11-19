package Controllers.CampManagementSystemInterface;

import java.util.List;

import Controllers.CampManagementSystem.CampController;
import Models.Camp;

public interface CampFileHandlerInterface {

    List<Camp> readCamps(CampController campController);

    void writeAllCamps(List<Camp> camps);

}