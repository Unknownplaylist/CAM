package Controllers.CampManagementSystem;

public interface CampSortingServiceInterface {

    void sortCampsAlphabeticallyAndWrite(CampController campController);

    void sortCampsByStartDateAndWrite(CampController campController);

    void sortCampsByEndDateAndWrite(CampController campController);

    void sortCampsLocationAndWrite(CampController campController);

}