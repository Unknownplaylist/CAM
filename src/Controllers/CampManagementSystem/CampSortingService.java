package Controllers.CampManagementSystem;

import java.util.Comparator;
import java.util.List;

import Models.Camp;

public class CampSortingService {

    public void sortCampsAlphabeticallyAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by name
        camps.sort(Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    public void sortCampsByStartDateAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by start date
        camps.sort(Comparator.comparing(Camp::getStartDate));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    public void sortCampsByEndDateAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by End date
        camps.sort(Comparator.comparing(Camp::getEndDate));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    public void sortCampsLocationAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the Location by name
        camps.sort(Comparator.comparing(Camp::getLocation, String.CASE_INSENSITIVE_ORDER));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }
    
}
