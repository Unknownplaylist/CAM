package Controllers.EnquiryManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.EnquiryManagementInterface.StaffEnquiryServiceInterface;

public class StaffEnquiryService implements StaffEnquiryServiceInterface {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
    /**
     * Executes the reply to an enquiry.
     * 
     * @param enquiryController The EnquiryController object.
     * @param camp The camp name.
     */
    @Override
    public void execReplyEnquiry(EnquiryController enquiryController, String camp){
        System.out.print("Type in the Enquiry ID to reply to: ");
        String ID = EnquiryController.sc.nextLine();
    
        String[] enquiryToReply = null;
    
        List<String[]> enquiryList = enquiryController.staffEnquiryService.execFindUnrepliedEnquiry(enquiryController, camp);
        if (enquiryList.isEmpty()) {
            System.out.println("No pending enquiry to reply.");
        }
        for (String[] i : enquiryList){
            if (i[5].equals(ID)){
                if (i[2].equals(camp)){
                    enquiryToReply = i;
                    break;
                }
                else {
                    System.out.println("Enquiry ID " + ID + " does not match " + camp);
                    return;
                }               
            }
        }
    
        if (enquiryToReply != null){
            if ((!enquiryToReply[3].equals(" ")) && (!enquiryToReply[4].equals(" "))){
                System.out.println("The enquiry has already been replied!");
                return;
            }
            else {
                enquiryController.staffEnquiryService.replyEnquiry(enquiryController, enquiryToReply);
            }
        }
        else {
            System.out.println("No enquiry under ID " + ID + " for " + camp);
        }
    }

    /**
     * Executes a search for unreplied enquiries based on the given camp.
     * 
     * @param enquiryController The EnquiryController object.
     * @param camp The camp to search for unreplied enquiries.
     * @return A list of String arrays representing the unreplied enquiries.
     */
    @Override
    public List<String[]> execFindUnrepliedEnquiry(EnquiryController enquiryController, String camp){
        List<String[]> enquiryList = enquiryController.staffEnquiryService.execFindEnquiry(camp);
        List<String[]> unrepliedEnquiryList = new ArrayList<String[]>();
    
        if (enquiryList.isEmpty()){
            System.out.println("No enquiries for " + camp);
            return null;
        }
    
        for (String[] i : enquiryList){
            if ((i[3].equals(" ")) && (i[4].equals(" "))){
                unrepliedEnquiryList.add(i);
            }
        }
        if (unrepliedEnquiryList.size() !=  0){
            System.out.println(unrepliedEnquiryList.size() + " enquiry/enquiries for your camp pending for reply.");
        }
        else {
            System.out.println("No pending enquiry");
        }
        return unrepliedEnquiryList;
    }

    /**
     * Generates a list of all enquiries for a specific camp, including both replied and unreplied enquiries.
     *
     * @param camp the camp for which to retrieve the enquiries
     * @return a list of String arrays representing the enquiries
     */
    @Override
    public List<String[]> execFindEnquiry(String camp){ //generate the list of all enquiries for that camp, both replied and unreplied
        List<String[]> enquiryList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(EnquiryController.CSV_SEPARATOR);
                if (data[2].equals(camp)){
                    enquiryList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return enquiryList;
    }

    /**
     * This method is used to reply to an enquiry.
     * It prompts the user to enter a reply, updates the enquiry status and saves the reply in the enquiry data.
     * It also updates the enquiry data file with the new reply.
     *
     * @param enquiryController The EnquiryController object.
     * @param enquiryToReply The array containing the enquiry details to reply to.
     */
    @Override
    public void replyEnquiry(EnquiryController enquiryController, String[] enquiryToReply){       
        System.out.print("Type in your reply: ");
        String Reply = EnquiryController.sc.nextLine();
        enquiryToReply[3] = "Read";
        enquiryToReply[4] = Reply;
        System.out.println("Reply has been saved!");
    
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(EnquiryController.CSV_SEPARATOR);
                    if ((data[2].equalsIgnoreCase(enquiryToReply[2])) 
                        && (data[1].equalsIgnoreCase(enquiryToReply[1])) 
                        && (data[0].equalsIgnoreCase(enquiryToReply[0]))) { //cross-validate the student name and camp
                        dataList.add(enquiryToReply);
                    }
                    else {
                        dataList.add(data);
                    }
                }
                enquiryController.enquiryFileHandler.writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your reply request.");
            e.printStackTrace();
        }   
    }
    
}
