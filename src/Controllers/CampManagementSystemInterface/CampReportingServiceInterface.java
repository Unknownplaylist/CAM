package Controllers.CampManagementSystemInterface;

import java.util.List;

import Models.Camp;

public interface CampReportingServiceInterface {

    void createStaffReport(List<Camp> camps, String outputPath);

}