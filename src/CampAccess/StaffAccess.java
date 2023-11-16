package CampAccess;
import java.time.LocalDate;
import java.util.*;
import Controllers.*;
import Models.*;

public class StaffAccess {
    static Scanner sc=new Scanner(System.in);
    private CampController campcont = new CampController();
    private StaffController staffcont=new StaffController();
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
            String location=sc.next();

            System.out.print("Enter total number of slots: ");
            int slots=sc.nextInt();

            System.out.print("Enter number of slots for Camp Committee (10 MAX): ");
            int commslots=sc.nextInt();
            if(commslots>10 || commslots>slots){
                System.out.println("Error Input");
                return;
            }

            System.out.print("Enter Description for the Camp: ");
            String description=sc.next();

            camp=new Camp(camp_name, startdate, enddate, regdate, group, location, slots, commslots, description, staff);
            campcont.writeCamp(camp);

            System.out.println("Camp Created");
        }
        catch(Exception e){
            System.out.println("Error creating Camp - Redirecting to Menu");
            return;
        }
    }

    public void delCamp() {
        System.out.print("Enter the name of the camp to delete: ");
        String campName = sc.nextLine();

        if (campcont.checkCamp(campName)) {
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

        System.out.print("Set camp visibility (true for visible, false for hidden): ");
        boolean isVisible = sc.nextBoolean();

        campcont.toggleCampVisibility(campName, isVisible);
        System.out.println("Visibility of camp " + campName + " updated to " + (isVisible ? "visible" : "hidden") + ".");
    }
}
