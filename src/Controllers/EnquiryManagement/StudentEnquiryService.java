package Controllers.EnquiryManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentEnquiryService {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
    public String[] studentEnquiryBasedOnIDandCamp(EnquiryController enquiryController, String student){
        List<String[]> studentEnquiryList = enquiryController.studentEnquiryService.studentFindEnquiry(student);
        if(studentEnquiryList.isEmpty()){
            System.out.println("No Enquiries under the Student name.");
            return null;
        }
        System.out.print("Type in the Enquiry ID: ");
        String ID  = EnquiryController.sc.nextLine();
    
        System.out.print("Type in the name of the camp: ");
        String camp  = EnquiryController.sc.nextLine();
    
        for (String[] data: studentEnquiryList){
            if (data[5].equals(ID)){
                if (data[2].equals(camp)){
                    return data;
                } else {
                    System.out.println("Enquiry ID " + ID + " does not match the " + camp);
                    return null;
                }
            }
        }
        return null;
    }

    public void viewEnquiry(EnquiryController enquiryController, String student){ //View all enquiries under the student name
        List<String[]> enquiryList = enquiryController.studentEnquiryService.studentFindEnquiry(student);
        if(!enquiryList.isEmpty()){
            for (String[] enquiry : enquiryList){       
                enquiryController.enquiryService.formatMessage(enquiry);        
            }
        }
        else
            System.out.println("You have no enquiries");
    }

    public List<String[]> studentFindEnquiry(String student){
        List<String[]> enquiryList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(EnquiryController.CSV_SEPARATOR);
                if (data[1].equalsIgnoreCase(student)){
                    enquiryList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return enquiryList;
    }
    
}
