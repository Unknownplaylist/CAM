package Controllers.CampEnquiryManagement;
//put interface to show similarity btw enquiry and suggestion
import java.io.*;
import java.util.*;

import Models.Camp;

//Enquiry.csv:
//Enquiry, Student, Camp, Read, Reply, ID

/*General Rule for Enquiry Controller
-Any student can write an enquiry to any camp that he/she can see.
-Only the staff and camp committee member in charge of the camp can read and reply to the enquiry 
 */

public class EnquiryController {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
    static final String CSV_SEPARATOR = ",";
    static Scanner sc = new Scanner(System.in);
    EnquiryFileHandler enquiryFileHandler;
    public EnquiryService enquiryService;
    public StudentEnquiryService studentEnquiryService;
    public StaffEnquiryService staffEnquiryService;

    public EnquiryController(){
        this.enquiryFileHandler = new EnquiryFileHandler();
        this.enquiryService = new EnquiryService();
        this.studentEnquiryService = new StudentEnquiryService();
        this.staffEnquiryService = new StaffEnquiryService();
    }

    // public void readReply(String student){
    //     //check if camp exists
    //     String[] studentEnquiry;
    //     System.out.println("Type in the name of the Camp to read Reply to Enquiry: ");
    //     String camp  = sc.nextLine();
    //     if (studentFindEnquiry(student, camp) != null){
    //         studentEnquiry = studentFindEnquiry(student, camp);
    //     }
    //     else {
    //         System.out.println("Cannot find the enquiry");
    //         return;
    //     }
    //     if ((!studentEnquiry[3].equals(" ")) && (!studentEnquiry[4].equals(" "))){
    //         formatMessage(studentEnquiry);
    //     }
    //     else {
    //         System.out.println("Your enquiry has not been replied. Stay tuned!");
    //     }
    // }

    //###################################################################################
    //IMPLEMENTATIONS FOR STAFF/CAMP COMM
    //###################################################################################

    // public String[] checkEnquiry(String camp){
    //     String[] data = findEnquiry(camp);
    //     if (data != null){
    //         System.out.println("There is an enquiry for your camp pending for reply.");
    //         formatMessage(data);
    //         return data;
    //     }
    //     else {
    //         System.out.println("No pending enquiry");
    //         return null;
    //     }
        
    // }

    // public String[] findEnquiry(String camp){
    //     String line;
    //     String[] data;
    //     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    //         while ((line = br.readLine()) != null) {
    //             data = line.split(CSV_SEPARATOR);
    //             if (data[2].equalsIgnoreCase(camp)){
    //                 return data;
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error reading enquiry file");
    //         e.printStackTrace();
    //     }
    //     System.out.println("Cannot find the enquiry for " + camp);
    //     return null;
    // }

    // public String[] findUnrepliedEnquiry(String camp){
    //     String line;
    //     String[] data;
    //     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    //         while ((line = br.readLine()) != null) {
    //             data = line.split(CSV_SEPARATOR);
    //             if (data[2].equalsIgnoreCase(camp) && 
    //             (!data[3].equals(" ")) && 
    //             (!data[4].equals(" "))){
    //                 return data;
    //             }
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error reading enquiry file");
    //         e.printStackTrace();
    //     }
    //     System.out.println("Cannot find the enquiry for " + camp);
    //     return null;
    // }


    


}
