package CampAccess.StaffAccessManagement;

import java.util.List;

import CampAccess.StaffAccessManagementInterface.EnquiryResponseServiceInterface;

public class EnquiryResponseService implements EnquiryResponseServiceInterface {

    /**
     * This method allows a staff member to reply to enquiries for a specific camp.
     * 
     * @param staffAccess The staff access object containing the necessary information.
     */
    @Override
    public void replytoEnquiries(StaffAccess staffAccess){
        System.out.print("Enter the name of the camp whose Enquiries you want to reply to : ");
        String camp_name = StaffAccess.sc.nextLine();
        if(staffAccess.campcont.campService.checkCamp(staffAccess.campcont, camp_name)){
            if(staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, camp_name)){
                staffAccess.enq.staffEnquiryService.execReplyEnquiry(staffAccess.enq, camp_name);
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

    /**
     * Displays the enquiries for a specific camp.
     * 
     * @param staffAccess The staff access object.
     */
    @Override
    public void viewEnquiries(StaffAccess staffAccess){
        System.out.print("Enter the name of the camp whose Enquiries you want to view : ");
        String camp_name = StaffAccess.sc.nextLine();
        if(staffAccess.campcont.campService.checkCamp(staffAccess.campcont, camp_name)){
            if(staffAccess.campcont.campService.isYourCamp(staffAccess.campcont, staffAccess.staffid, camp_name)){
                List<String[]> unrepliedEnquiryList = staffAccess.enq.staffEnquiryService.execFindUnrepliedEnquiry(staffAccess.enq, camp_name);
                staffAccess.enq.enquiryService.formatMessageList(staffAccess.enq, unrepliedEnquiryList);
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
    
}
