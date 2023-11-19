package CampAccess.CampStaffAccessManagement;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import Models.Camp;

public class CampManagementService {

    public void createCamp(StaffAccess staffAccess){
        try{
            System.out.print("Camp Name : ");
            String camp_name=StaffAccess.sc.next();
    
            System.out.print("Enter Starting Date (YYYY-MM-DD): ");
            String start_date=StaffAccess.sc.next();
            LocalDate startdate = LocalDate.parse(start_date);
    
            System.out.print("Enter Ending Date (YYYY-MM-DD): ");
            String end_date=StaffAccess.sc.next();
            LocalDate enddate = LocalDate.parse(end_date);
            if(enddate.isBefore(startdate) || enddate.isEqual(startdate)){
                System.out.println("Invalid Input");
                return;
            }
    
            System.out.print("Enter Registration Close Date (YYYY-MM-DD): ");
            String reg_date=StaffAccess.sc.next();
            LocalDate regdate = LocalDate.parse(reg_date);
            if(regdate.isAfter(startdate) || regdate.isEqual(startdate)){
                System.out.println("Invalid Input - Last Registration Data must be before Start Date");
                return;
            }
    
            String [] allowed_groups = {"ADM", "NBS", "SCSE", "EEE", "SSS","ALL"};
            System.out.print("Enter User Group - A particular school or 'ALL': ");
            String group=StaffAccess.sc.next();
            for(String i:allowed_groups){
                if(i.equalsIgnoreCase(group))
                    group=group+"yes";
            }
            if(!group.endsWith("yes")){
                System.out.println("Error Input");
                return;
            }
            group=group.substring(0, group.length()-3);
    
            System.out.print("Enter Location: ");
            String location=StaffAccess.sc.nextLine();
            location=StaffAccess.sc.nextLine();
    
            System.out.print("Enter total number of slots: ");
            int slots=StaffAccess.sc.nextInt();
    
            System.out.print("Enter number of slots for Camp Committee (10 MAX): ");
            int commslots=StaffAccess.sc.nextInt();
            if(commslots>10 || commslots>slots){
                System.out.println("Error Input");
                return;
            }
    
            System.out.print("Enter Description for the Camp: ");
            String description=StaffAccess.sc.nextLine();
            description=StaffAccess.sc.nextLine();
    
            staffAccess.camp=new Camp(camp_name, startdate, enddate, regdate, group, location, slots, commslots, description, staffAccess.staff);
            staffAccess.campcont.campService.writeCamp(staffAccess.campcont, staffAccess.camp);
    
            System.out.println("Camp Created");
        }
        catch(Exception e){
            System.out.println("Error creating Camp - Redirecting to Menu");
            StaffAccess.sc.nextLine();
            StaffAccess.sc.nextLine();
            return;
        }
    }

    public void editCamp(StaffAccess staffAccess){
        System.out.print("Enter the name of the Camp you wish to Edit : ");
        String camp_name=StaffAccess.sc.nextLine();
        if(staffAccess.campcont.campService.checkCamp(staffAccess.campcont, camp_name)){
            if(staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, camp_name)){
                try{
                    Camp curr=staffAccess.campcont.campService.getCamp(staffAccess.campcont, camp_name);
    
                    System.out.print("Enter Changed Starting Date (YYYY-MM-DD): ");
                    String start_date=StaffAccess.sc.next();
                    LocalDate startdate = LocalDate.parse(start_date);
    
                    System.out.print("Enter Changed Ending Date (YYYY-MM-DD): ");
                    String end_date=StaffAccess.sc.next();
                    LocalDate enddate = LocalDate.parse(end_date);
                    if(enddate.isBefore(startdate) || enddate.isEqual(startdate)){
                        System.out.println("Invalid Input");
                        return;
                    }
    
                    System.out.print("Enter Changed Registration Close Date (YYYY-MM-DD): ");
                    String reg_date=StaffAccess.sc.next();
                    LocalDate regdate = LocalDate.parse(reg_date);
                    if(regdate.isAfter(startdate) || regdate.isEqual(startdate)){
                        System.out.println("Invalid Input - Last Registration Data must be before Start Date");
                        return;
                    }
                    System.out.print("Enter Location: ");
                    String location=StaffAccess.sc.nextLine();
                    location=StaffAccess.sc.nextLine();
    
                    System.out.print("Enter Description for the Camp: ");
                    String description=StaffAccess.sc.nextLine();
    
                    Camp newcamp = new Camp(camp_name, startdate, enddate, regdate, curr.getFaculty(), location, curr.getTotalSlots(), curr.getCommitteeSlots(), description, staffAccess.staff);
                    staffAccess.campcont.updateCamp(camp_name, newcamp);
    
                }
                catch(Exception e){
                    System.out.println("Invalid Input - Redirecting to Menu");
                    return;
                }
            }
            else{
                System.out.println("Camp "+camp_name+"is not yours to Edit");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }

    public void delCamp(StaffAccess staffAccess) {
        System.out.print("Enter the name of the camp to delete: ");
        String campName = StaffAccess.sc.nextLine();
    
        if (staffAccess.campcont.campService.checkCamp(staffAccess.campcont, campName)) {
            if(!staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, campName)){
                System.out.println("Camp " + campName + " is not yours.");
                return;
            }
            staffAccess.campcont.campService.deleteCamp(staffAccess.campcont, campName);
            System.out.println("Camp " + campName + " deleted successfully.");
        } else {
            System.out.println("Camp " + campName + " not found.");
        }
    }

    public void changeVisibility(StaffAccess staffAccess) {
        System.out.print("Enter the name of the camp to change visibility: ");
        String campName = StaffAccess.sc.nextLine();
    
        if (!staffAccess.campcont.campService.checkCamp(staffAccess.campcont, campName)) {
            System.out.println("Camp " + campName + " not found.");
            return;
        }
    
        if(!staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, campName)){
            System.out.println("Camp " + campName + " is not yours.");
            return;
        }
    
        System.out.print("Set camp visibility (true for visible, false for hidden): ");
        boolean isVisible = StaffAccess.sc.nextBoolean();
    
        staffAccess.campcont.campService.toggleCampVisibility(staffAccess.campcont, campName, isVisible);
        System.out.println("Visibility of camp " + campName + " updated to " + (isVisible ? "visible" : "hidden") + ".");
    }

    public void viewCamps(StaffAccess staffAccess, boolean your){
        int choice;
        System.out.println("How do you want to view the Camps?");
        System.out.println("(1) By Name\n(2) By Starting Date\n(3) By Ending Date\n(4) By Location");
        System.out.print("Enter your preferred view (by number): ");
        try{
            choice = StaffAccess.sc.nextInt();
            System.out.println();
        }
        catch(Exception e){
            System.out.println("Invalid input - Redirecting you to Menu");
            StaffAccess.sc.nextLine();
            StaffAccess.sc.nextLine();
            return;
        }
        switch(choice){
            case 1 : staffAccess.campcont.campSortingService.sortCampsAlphabeticallyAndWrite(staffAccess.campcont);
            break;
            case 2 : staffAccess.campcont.campSortingService.sortCampsByStartDateAndWrite(staffAccess.campcont);
            break;
            case 3 : staffAccess.campcont.campSortingService.sortCampsByEndDateAndWrite(staffAccess.campcont);
            break;
            case 4 : staffAccess.campcont.campSortingService.sortCampsLocationAndWrite(staffAccess.campcont);
            break;
            default:
                System.out.println("Invalid input - Redirecting you to Menu");
                return;
        }
        List <Camp> campviewer=staffAccess.campcont.campService.viewAllCamps(staffAccess.campcont).stream().collect(Collectors.toList());;
        if(your){
            campviewer = staffAccess.campcont.campService.viewAllCamps(staffAccess.campcont).stream()
                .filter(camp -> (camp.getStaffInCharge().getName()).equals(staffAccess.staff.getName()))
                .collect(Collectors.toList()); 
            if (campviewer.isEmpty()){
                System.out.println("You have created no camps");
                return;
            }
    
        }           
    
        campviewer.forEach(camp -> System.out.println(camp+"\n"+"Visibility: "+(camp.isVisible()?"Visible":"Hidden")+"\n"));
    }
    
}
