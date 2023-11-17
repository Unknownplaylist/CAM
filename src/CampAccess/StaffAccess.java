package CampAccess;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import Controllers.*;
import Models.*;

public class StaffAccess {
    static Scanner sc=new Scanner(System.in);
    
    private StaffController staffcont=new StaffController();
    private StudentsController studentscont=new StudentsController();
    private CampController campcont = new CampController(studentscont, staffcont);
    private Camp camp;
    private String staffid;
    private Staff staff;

    public StaffAccess(String staffid,StaffController staffcont){
        this.staffid=staffid;
        this.staffcont=staffcont;
        staff=staffcont.getStaffByEmail(staffid+"@NTU.EDU.SG");
    }

    public void createCamp(){
        try{
            System.out.print("Camp Name : ");
            String camp_name=sc.next();

            System.out.print("Enter Starting Date (YYYY-MM-DD): ");
            String start_date=sc.next();
            LocalDate startdate = LocalDate.parse(start_date);

            System.out.print("Enter Ending Date (YYYY-MM-DD): ");
            String end_date=sc.next();
            LocalDate enddate = LocalDate.parse(end_date);
            if(enddate.isBefore(startdate) || enddate.isEqual(startdate)){
                System.out.println("Invalid Input");
                return;
            }

            System.out.print("Enter Registration Close Date (YYYY-MM-DD): ");
            String reg_date=sc.next();
            LocalDate regdate = LocalDate.parse(reg_date);
            if(regdate.isAfter(startdate) || regdate.isEqual(startdate)){
                System.out.println("Invalid Input - Last Registration Data must be before Start Date");
                return;
            }

            String [] allowed_groups = {"ADM", "NBS", "SCSE", "EEE", "SSS","ALL"};
            System.out.print("Enter User Group - A particular school or 'ALL': ");
            String group=sc.next();
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
            String location=sc.nextLine();
            location=sc.nextLine();

            System.out.print("Enter total number of slots: ");
            int slots=sc.nextInt();

            System.out.print("Enter number of slots for Camp Committee (10 MAX): ");
            int commslots=sc.nextInt();
            if(commslots>10 || commslots>slots){
                System.out.println("Error Input");
                return;
            }

            System.out.print("Enter Description for the Camp: ");
            String description=sc.nextLine();
            description=sc.nextLine();

            camp=new Camp(camp_name, startdate, enddate, regdate, group, location, slots, commslots, description, staff);
            campcont.writeCamp(camp);

            System.out.println("Camp Created");
        }
        catch(Exception e){
            System.out.println("Error creating Camp - Redirecting to Menu");
            return;
        }
    }

    public void viewCamps(boolean your){
        int choice;
        System.out.println("How do you want to view the Camps?");
        System.out.println("(1) By Name\n(2) By Starting Date\n(3) By Ending Date\n(4) By Location");
        System.out.print("Enter your preferred view (by number): ");
        try{
            choice = sc.nextInt();
            System.out.println();
        }
        catch(Exception e){
            System.out.println("Invalid input - Redirecting you to Menu");
            return;
        }
        switch(choice){
            case 1 : campcont.sortCampsAlphabeticallyAndWrite();
            break;
            case 2 : campcont.sortCampsByStartDateAndWrite();
            break;
            case 3 : campcont.sortCampsByEndDateAndWrite();
            break;
            case 4 : campcont.sortCampsLocationAndWrite();
            break;
            default:
                System.out.println("Invalid input - Redirecting you to Menu");
                return;
        }
        List <Camp> campviewer=campcont.viewAllCamps().stream().collect(Collectors.toList());;
        if(your){
            campviewer = campcont.viewAllCamps().stream()
                .filter(camp -> (camp.getStaffInCharge().getName()).equals(staff.getName()))
                .collect(Collectors.toList()); 
            if (campviewer.isEmpty()){
                System.out.println("You have created no camps");
                return;
            }

        }           

        campviewer.forEach(camp -> System.out.println(camp+"\n"));
    }

    public void delCamp() {
        System.out.print("Enter the name of the camp to delete: ");
        String campName = sc.nextLine();

        if (campcont.checkCamp(campName)) {
            if(!campcont.isYourCamp(staffid, campName)){
                System.out.println("Camp " + campName + " is not yours.");
                return;
            }
            campcont.deleteCamp(campName);
            System.out.println("Camp " + campName + " deleted successfully.");
        } else {
            System.out.println("Camp " + campName + " not found.");
        }
    }

    public void changeVisibility() {
        System.out.print("Enter the name of the camp to change visibility: ");
        String campName = sc.nextLine();

        if (!campcont.checkCamp(campName)) {
            System.out.println("Camp " + campName + " not found.");
            return;
        }

        if(!campcont.isYourCamp(staffid, campName)){
            System.out.println("Camp " + campName + " is not yours.");
            return;
        }

        System.out.print("Set camp visibility (true for visible, false for hidden): ");
        boolean isVisible = sc.nextBoolean();

        campcont.toggleCampVisibility(campName, isVisible);
        System.out.println("Visibility of camp " + campName + " updated to " + (isVisible ? "visible" : "hidden") + ".");
    }
}
