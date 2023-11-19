package CampAccess;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import Controllers.*;
import Controllers.CampManagementSystem.CampController;
import Models.*;

public class StaffAccess {
    static Scanner sc=new Scanner(System.in);
    
    private StaffController staffcont=new StaffController();
    private StudentsController studentscont=new StudentsController();
    private EnquiryController enq = new EnquiryController();
    private SuggestionController sugg = new SuggestionController();
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
            campcont.campService.writeCamp(campcont, camp);

            System.out.println("Camp Created");
        }
        catch(Exception e){
            System.out.println("Error creating Camp - Redirecting to Menu");
            sc.nextLine();
            sc.nextLine();
            return;
        }
    }

    public void generateReport(){
        List<Camp>toprint;
        System.out.println("Choose filter to view by: ");
        System.out.println("(1) Location\n(2) By Starting Alphabets \n(3) By Attendee");
        System.out.print("In: ");
        int choice;
        try{
            choice = sc.nextInt();
        }
        catch(Exception e){
            System.out.println("Invalid Input - Redirecting to Menu");
            sc.nextLine();
            sc.nextLine();
            return;
        } 
        switch(choice){
            case 1:
                System.out.print("Enter Location: ");
                String location = sc.nextLine();
                location = sc.nextLine();
                toprint=campcont.campService.getCampsByLocation(campcont, location);
            break;
            case 2:
                System.out.print("Enter starting alphabets: ");
                String st = sc.nextLine();
                st = sc.nextLine();
                toprint=campcont.campService.getCampsByStartingAlphabet(campcont, st);
            break;
            case 3:
                System.out.print("Enter attendee name: ");
                String attendee_name = sc.nextLine();
                attendee_name = sc.nextLine();
                toprint=campcont.campService.getCampsByAttendeeName(campcont, attendee_name);
            break;
            default:
                System.out.println("Invalid Input - Redirecting to Menu");
                return;
            }
        campcont.campReportingService.createStaffReport(toprint, "src\\Database\\StaffReport.csv");        
    }

    public void reviewSuggestions(){
        System.out.print("Enter the name of the camp whose Suggestions you want to review : ");
        String camp_name = sc.nextLine();
        if(campcont.campService.checkCamp(campcont, camp_name)){
            if(campcont.campService.isYourCamp(campcont, staffid, camp_name)){
                sugg.execReviewSuggestion(camp_name);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }

    public void viewSuggestions(){
        System.out.print("Enter the name of the camp whose Suggestions you want to view : ");
        String camp_name = sc.nextLine();
        if(campcont.campService.checkCamp(campcont, camp_name)){
            if(campcont.campService.isYourCamp(campcont, staffid, camp_name)){
                List<String[]> unrepliedSuggestionList = sugg.execFindUnrepliedSuggestion(camp_name);
                sugg.formatMessageList(unrepliedSuggestionList);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }

    public void replytoEnquiries(){
        System.out.print("Enter the name of the camp whose Enquiries you want to reply to : ");
        String camp_name = sc.nextLine();
        if(campcont.campService.checkCamp(campcont, camp_name)){
            if(campcont.campService.isYourCamp(campcont, staffid, camp_name)){
                enq.execReplyEnquiry(camp_name);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
            return;
        }
    }

    public void viewEnquiries(){
        System.out.print("Enter the name of the camp whose Enquiries you want to view : ");
        String camp_name = sc.nextLine();
        if(campcont.campService.checkCamp(campcont, camp_name)){
            if(campcont.campService.isYourCamp(campcont, staffid, camp_name)){
                List<String[]> unrepliedEnquiryList = enq.execFindUnrepliedEnquiry(camp_name);
                enq.formatMessageList(unrepliedEnquiryList);
            }
            else{
                System.out.println("Camp "+camp_name+" is not yours to access");
                return;
            }
        }
        else{
            System.out.println("Camp "+camp_name+" is not a camp");
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
            sc.nextLine();
            sc.nextLine();
            return;
        }
        switch(choice){
            case 1 : campcont.campSortingService.sortCampsAlphabeticallyAndWrite(campcont);
            break;
            case 2 : campcont.campSortingService.sortCampsByStartDateAndWrite(campcont);
            break;
            case 3 : campcont.campSortingService.sortCampsByEndDateAndWrite(campcont);
            break;
            case 4 : campcont.campSortingService.sortCampsLocationAndWrite(campcont);
            break;
            default:
                System.out.println("Invalid input - Redirecting you to Menu");
                return;
        }
        List <Camp> campviewer=campcont.campService.viewAllCamps(campcont).stream().collect(Collectors.toList());;
        if(your){
            campviewer = campcont.campService.viewAllCamps(campcont).stream()
                .filter(camp -> (camp.getStaffInCharge().getName()).equals(staff.getName()))
                .collect(Collectors.toList()); 
            if (campviewer.isEmpty()){
                System.out.println("You have created no camps");
                return;
            }

        }           

        campviewer.forEach(camp -> System.out.println(camp+"\n"+"Visibility: "+(camp.isVisible()?"Visible":"Hidden")+"\n"));
    }

    public void editCamp(){
        System.out.print("Enter the name of the Camp you wish to Edit : ");
        String camp_name=sc.nextLine();
        if(campcont.campService.checkCamp(campcont, camp_name)){
            if(campcont.campService.isYourCamp(campcont, staffid, camp_name)){
                try{
                    Camp curr=campcont.campService.getCamp(campcont, camp_name);

                    System.out.print("Enter Changed Starting Date (YYYY-MM-DD): ");
                    String start_date=sc.next();
                    LocalDate startdate = LocalDate.parse(start_date);

                    System.out.print("Enter Changed Ending Date (YYYY-MM-DD): ");
                    String end_date=sc.next();
                    LocalDate enddate = LocalDate.parse(end_date);
                    if(enddate.isBefore(startdate) || enddate.isEqual(startdate)){
                        System.out.println("Invalid Input");
                        return;
                    }

                    System.out.print("Enter Changed Registration Close Date (YYYY-MM-DD): ");
                    String reg_date=sc.next();
                    LocalDate regdate = LocalDate.parse(reg_date);
                    if(regdate.isAfter(startdate) || regdate.isEqual(startdate)){
                        System.out.println("Invalid Input - Last Registration Data must be before Start Date");
                        return;
                    }
                    System.out.print("Enter Location: ");
                    String location=sc.nextLine();
                    location=sc.nextLine();

                    System.out.print("Enter Description for the Camp: ");
                    String description=sc.nextLine();

                    Camp newcamp = new Camp(camp_name, startdate, enddate, regdate, curr.getFaculty(), location, curr.getTotalSlots(), curr.getCommitteeSlots(), description, staff);
                    campcont.updateCamp(camp_name, newcamp);

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

    public void delCamp() {
        System.out.print("Enter the name of the camp to delete: ");
        String campName = sc.nextLine();

        if (campcont.campService.checkCamp(campcont, campName)) {
            if(!campcont.campService.isYourCamp(campcont, staffid, campName)){
                System.out.println("Camp " + campName + " is not yours.");
                return;
            }
            campcont.campService.deleteCamp(campcont, campName);
            System.out.println("Camp " + campName + " deleted successfully.");
        } else {
            System.out.println("Camp " + campName + " not found.");
        }
    }

    public void changeVisibility() {
        System.out.print("Enter the name of the camp to change visibility: ");
        String campName = sc.nextLine();

        if (!campcont.campService.checkCamp(campcont, campName)) {
            System.out.println("Camp " + campName + " not found.");
            return;
        }

        if(!campcont.campService.isYourCamp(campcont, staffid, campName)){
            System.out.println("Camp " + campName + " is not yours.");
            return;
        }

        System.out.print("Set camp visibility (true for visible, false for hidden): ");
        boolean isVisible = sc.nextBoolean();

        campcont.campService.toggleCampVisibility(campcont, campName, isVisible);
        System.out.println("Visibility of camp " + campName + " updated to " + (isVisible ? "visible" : "hidden") + ".");
    }
}
