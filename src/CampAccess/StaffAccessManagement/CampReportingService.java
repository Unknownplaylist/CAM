package CampAccess.StaffAccessManagement;

import java.util.List;

import Models.Camp;

public class CampReportingService {

    public void generateReport(StaffAccess staffAccess){
        List<Camp>toprint;
        System.out.println("Choose filter to view by: ");
        System.out.println("(1) Location\n(2) By Starting Alphabets \n(3) By Attendee");
        System.out.print("In: ");
        int choice;
        try{
            choice = StaffAccess.sc.nextInt();
        }
        catch(Exception e){
            System.out.println("Invalid Input - Redirecting to Menu");
            StaffAccess.sc.nextLine();
            StaffAccess.sc.nextLine();
            return;
        } 
        switch(choice){
            case 1:
                System.out.print("Enter Location: ");
                String location = StaffAccess.sc.nextLine();
                location = StaffAccess.sc.nextLine();
                toprint=staffAccess.campcont.campService.getCampsByLocation(staffAccess.campcont, location);
            break;
            case 2:
                System.out.print("Enter starting alphabets: ");
                String st = StaffAccess.sc.nextLine();
                st = StaffAccess.sc.nextLine();
                toprint=staffAccess.campcont.campService.getCampsByStartingAlphabet(staffAccess.campcont, st);
            break;
            case 3:
                System.out.print("Enter attendee name: ");
                String attendee_name = StaffAccess.sc.nextLine();
                attendee_name = StaffAccess.sc.nextLine();
                toprint=staffAccess.campcont.campService.getCampsByAttendeeName(staffAccess.campcont, attendee_name);
            break;
            default:
                System.out.println("Invalid Input - Redirecting to Menu");
                return;
            }
        staffAccess.campcont.campReportingService.createStaffReport(toprint, "src\\Database\\StaffReport.csv");        
    }
    
}
