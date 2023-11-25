package Controllers.CampManagementSystem;

import java.util.Comparator;
import java.util.List;

import Controllers.CampManagementSystemInterface.CampSortingServiceInterface;
import Models.Camp;

public class CampSortingService implements CampSortingServiceInterface {

    /**
     * Sorts the camps alphabetically by name and writes the sorted list back to the CSV file.
     *
     * @param campController the CampController object used to access the camp file handler
     */
    @Override
    public void sortCampsAlphabeticallyAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by name
        camps.sort(Comparator.comparing(Camp::getCampName, String.CASE_INSENSITIVE_ORDER));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Sorts the camps by start date and writes the sorted list back to the CSV file.
     * 
     * @param campController the CampController object used to read and write camps
     */
    @Override
    public void sortCampsByStartDateAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by start date
        camps.sort(Comparator.comparing(Camp::getStartDate));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Sorts the camps by end date and writes the sorted list back to the CSV file.
     *
     * @param campController the CampController object used to access the camp file handler
     */
    @Override
    public void sortCampsByEndDateAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the camps by End date
        camps.sort(Comparator.comparing(Camp::getEndDate));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }

    /**
     * Sorts the camps by location name in ascending order and writes the sorted list back to the CSV file.
     * 
     * @param campController the camp controller object
     */
    @Override
    public void sortCampsLocationAndWrite(CampController campController) {
        List<Camp> camps = campController.campFileHandler.readCamps(campController); // Method to read camps from CSV
    
        // Sorting the Location by name
        camps.sort(Comparator.comparing(Camp::getLocation, String.CASE_INSENSITIVE_ORDER));
    
        // Write the sorted list back to the CSV
        campController.campFileHandler.writeAllCamps(camps);
    }
    
}
