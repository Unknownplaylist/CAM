package Controllers.EnquiryManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.EnquiryManagementInterface.StaffEnquiryServiceInterface;

public class StaffEnquiryService implements StaffEnquiryServiceInterface {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
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
