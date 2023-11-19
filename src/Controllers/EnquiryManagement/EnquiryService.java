package Controllers.EnquiryManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Controllers.EnquiryManagementInterface.EnquiryServiceInterface;

public class EnquiryService implements EnquiryServiceInterface {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
    @Override
    public void formatMessageList(EnquiryController enquiryController, List<String[]> enquiryList){
        if (enquiryList == null){
            System.out.println("No message to display.");
            return;
        }
        for (String[] i : enquiryList){
            System.out.println("--------------------------------");
            enquiryController.enquiryService.formatMessage(i);
            System.out.println("--------------------------------");
        }
    }

    @Override
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("--------------------------------");
            System.out.println("ENQUIRY ID: " + data[5]);
            System.out.println("From: "+data[1]);
            System.out.println("To "+data[2]);
            if (Objects.equals(data[3]," ")){
                System.out.println("Read Status: Not read || Reply Status: Not replied");
            }
            else {
                System.out.println("Read Status: Read || Reply Status: Replied");
                System.out.println("--------------------------------");
                System.out.println("Reply: " + data[4]);
            }
            System.out.println("Message: " + data[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough data");
        }
    }

    @Override
    public void viewReplyToEnquiry(EnquiryController enquiryController, String camp){
        List<String[]> enquiryList = enquiryController.staffEnquiryService.execFindEnquiry(camp);
        if (!enquiryList.isEmpty()){
            for (String[] i : enquiryList){
                formatMessage(i);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        else System.out.println("No visible enquiries.");       
    }

    @Override
    public void deleteEnquiry(EnquiryController enquiryController, String student){
        //check if camp exists
        String[] studentEnquiry = enquiryController.studentEnquiryService.studentEnquiryBasedOnIDandCamp(enquiryController, student);
    
        if (studentEnquiry == null){
            System.out.println("Cannot proceed with your delete request");
            return;
        }
    
        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){
    
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(EnquiryController.CSV_SEPARATOR);
                    if ((data[0].equalsIgnoreCase(studentEnquiry[0])) 
                    && (data[1].equalsIgnoreCase(studentEnquiry[1]))
                    && (data[5].equalsIgnoreCase(studentEnquiry[5])) ) {
                        continue;
                    }
                    dataList.add(data);
                }
                enquiryController.enquiryFileHandler.writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your delete request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot delete the enquiry.");
        }
    }

    @Override
    public void editEnquiry(EnquiryController enquiryController, String student){
        //check if camp exists
        String[] studentEnquiry = enquiryController.studentEnquiryService.studentEnquiryBasedOnIDandCamp(enquiryController, student);
    
        if (studentEnquiry == null){
            System.out.println("Cannot proceed with your edit request");
            return;
        }
    
        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){
            System.out.print("Edit your enquiry here: ");
            String message = EnquiryController.sc.nextLine();
            studentEnquiry[0] = message;
    
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(EnquiryController.CSV_SEPARATOR);
                    if ( (data[1].equalsIgnoreCase(studentEnquiry[1]))
                    && (data[5].equalsIgnoreCase(studentEnquiry[5])) ) {
                        dataList.add(studentEnquiry);
                        continue;
                    }
                    dataList.add(data);
                }
                enquiryController.enquiryFileHandler.writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your edit request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot edit the enquiry.");
        }
        
    }

    @Override
    public void createEnquiry(EnquiryController enquiryController, String student){
        String read = " ";
        String reply = " ";
        String ID = " "; //To be decided later
    
        //would be better to add in functions to see if the camp exists or not based on the input
        System.out.print("Select the camp to send the enquiry to: ");
        String camp = EnquiryController.sc.nextLine();
        
        System.out.print("Type in your message: ");
        String message = EnquiryController.sc.nextLine();
    
        String data = String.join(EnquiryController.CSV_SEPARATOR,message, student, camp,read,reply, ID);
        String[] enquiry = data.split(EnquiryController.CSV_SEPARATOR);
        enquiryController.enquiryFileHandler.writeEnquiryToFile(enquiry);
    }
    
}
