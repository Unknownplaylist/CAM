package Controllers;
//put interface to show similarity btw enquiry and suggestion
import java.io.*;
import java.util.*;

//Enquiry.csv:
//Enquiry, Student, Camp, Read, Reply, ID

/*General Rule for Enquiry Controller
-Any student can write an enquiry to any camp that he/she can see.
-Only the staff and camp committee member in charge of the camp can read and reply to the enquiry 
 */

public class EnquiryController {
    private static final String FILE_PATH = "src/Database/Enquiry.csv";
    private static final String CSV_SEPARATOR = ",";
    static Scanner sc = new Scanner(System.in);

    //private CampController campComtroller;

    //###################################################################################
    //IMPLEMENTATIONS FOR STUDENTS
    //###################################################################################
    public List<String[]> findAllEnquiries(){
        List<String[]> enquiryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {            
            String[] data;
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                enquiryList.add(data);
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return enquiryList;
    }   
    public void writeEnquiryToFile(String[] enquiry){
        List<String[]> existingEnquiry = findAllEnquiries();
        // int ID = existingEnquiry.size();
        // enquiry[5] = Integer.toString(ID);
        existingEnquiry.add(enquiry);
        writeCSV(existingEnquiry);
    }
    
    public void createEnquiry(String student){
        String read = " ";
        String reply = " ";
        String ID = " "; //To be decided later

        //would be better to add in functions to see if the camp exists or not based on the input
        System.out.print("Select the camp to send the enquiry to: ");
        String camp = sc.nextLine();
        
        System.out.print("Type in your message: ");
        String message = sc.nextLine();

        String data = String.join(CSV_SEPARATOR,message, student, camp,read,reply, ID);
        String[] enquiry = data.split(CSV_SEPARATOR);
        writeEnquiryToFile(enquiry);
        // try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
        //     //writer.newLine();
        //     writer.write(data);
        //     writer.newLine();
            
        // } catch (IOException e) {
        //     System.out.println("Error uploading the enquiry");
        //     e.printStackTrace();
        // }
    }

    public List<String[]> studentFindEnquiry(String student){
        List<String[]> enquiryList = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
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

    public void viewEnquiry(String student){ //View all enquiries under the student name
        List<String[]> enquiryList = studentFindEnquiry(student);
        if(!enquiryList.isEmpty()){
            for (String[] enquiry : enquiryList){       
                formatMessage(enquiry);        
            }
        }
        else
            System.out.println("You have no enquiries");
    }

    public String[] studentEnquiryBasedOnIDandCamp(String student){
        List<String[]> studentEnquiryList = studentFindEnquiry(student);
        if(studentEnquiryList.isEmpty()){
            System.out.println("No Enquiries under the Student name.");
            return null;
        }
        System.out.println("Type in the Enquiry ID: ");
        String ID  = sc.nextLine();

        System.out.println("Type in the name of the camp: ");
        String camp  = sc.nextLine();

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

    public void editEnquiry(String student){
        //check if camp exists
        String[] studentEnquiry = studentEnquiryBasedOnIDandCamp(student);

        if (studentEnquiry == null){
            System.out.println("Cannot proceed with your edit request");
            return;
        }

        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){
            System.out.println("Edit your enquiry here: ");
            String message = sc.nextLine();
            studentEnquiry[0] = message;

            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(student)) {
                        dataList.add(data);
                    }
                    else {
                        dataList.add(studentEnquiry);
                    }
                }
                writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your edit request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot edit the enquiry.");
        }
        
    }

    public void deleteEnquiry(String student){
        //check if camp exists
        String[] studentEnquiry = studentEnquiryBasedOnIDandCamp(student);

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
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(student)) {
                        dataList.add(data);
                    }
                }
                writeCSV(dataList);
            } catch (IOException e) {
                System.out.println("Cannot proceed with your delete request.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot delete the enquiry.");
        }
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
    public String[] checkEnquiry(String camp){
        String[] data = findEnquiry(camp);
        if (data != null){
            System.out.println("There is an enquiry for your camp pending for reply.");
            formatMessage(data);
            return data;
        }
        else {
            System.out.println("No pending enquiry");
            return null;
        }
        
    }

    public String[] findEnquiry(String camp){
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[2].equalsIgnoreCase(camp)){
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        System.out.println("Cannot find the enquiry for " + camp);
        return null;
    }

    public String[] findUnrepliedEnquiry(String camp){
        String line;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[2].equalsIgnoreCase(camp) && 
                (!data[3].equals(" ")) && 
                (!data[4].equals(" "))){
                    return data;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        System.out.println("Cannot find the enquiry for " + camp);
        return null;
    }

    public void replyEnquiry(String[] enquiryToReply){       
        System.out.print("Type in your reply: ");
        String Reply = sc.nextLine();
        enquiryToReply[3] = "Read";
        enquiryToReply[4] = Reply;
        System.out.println("Reply has been saved!");

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if ((data[2].equalsIgnoreCase(enquiryToReply[2])) 
                        && (data[1].equalsIgnoreCase(enquiryToReply[1])) 
                        && (data[0].equalsIgnoreCase(enquiryToReply[0]))) { //cross-validate the student name and camp
                        dataList.add(enquiryToReply);
                    }
                    else {
                        dataList.add(data);
                    }
                }
                writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your reply request.");
            e.printStackTrace();
        }   
    }

    public List<String[]> execFindEnquiry(String camp){ //generate the list of all enquiries, both replied and unreplied
        List<String[]> enquiryList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
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

    public List<String[]> execFindUnrepliedEnquiry(String camp){
        List<String[]> enquiryList = execFindEnquiry(camp);
        List<String[]> unrepliedEnquiryList = new ArrayList<String[]>();
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

    public void execReplyEnquiry(String camp){
        System.out.print("Type in the name of the sender to reply to: ");
        String name = sc.nextLine();

        String[] enquiryToReply = null;

        List<String[]> enquiryList = execFindEnquiry(camp);
        if (enquiryList == null) {
            System.out.println("Error loading the enquiry list.");
        }
        for (String[] i : enquiryList){
            if ((i[1].equals(name)) && (i[2].equals(camp))){
                enquiryToReply = i;
                break;
            }
        }

        if (enquiryToReply != null){
            if ((!enquiryToReply[3].equals(" ")) && (!enquiryToReply[4].equals(" "))){
                System.out.println("The enquiry has already been replied!");
                return;
            }
            else {
                replyEnquiry(enquiryToReply);
            }
        }
        else {
            System.out.println("No enquiry from student " + name);
        }
    }

    public void viewReplyToEnquiry(String camp){
        List<String[]> enquiryList = execFindEnquiry(camp);
        if (!enquiryList.isEmpty()){
            for (String[] i : enquiryList){
                formatMessage(i);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        else System.out.println("No visible enquiries.");       
    }

    //###################################################################################
    //IMPLEMENTATIONS FOR ALL
    //###################################################################################
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("ENQUIRY ID: " + data[5]);
            System.out.println("From: "+data[1]);
            System.out.println("To "+data[2] + " Organizing Committee");
            if (Objects.equals(data[3]," ")){
                System.out.println("Read Status: Not read || Reply Status: Not replied");
            }
            else {
                System.out.println("Read Status: Read || Reply Status: Replied");
                System.out.println("--------------------------------");
                System.out.println("Reply: " + data[4]);
            }
            System.out.println("--------------------------------");
            System.out.println("Message: " + data[0]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Not enough data");
        }
    }

    public void formatMessageList(List<String[]> enquiryList){
        for (String[] i : enquiryList){
            System.out.println("--------------------------------");
            formatMessage(i);
            System.out.println("--------------------------------");
        }
    }

    public void writeCSV(List<String[]> dataList){
        int ID = 1;
        String writeLine;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    for (String[] i : dataList){
                        if (!i[5].equals("ID")){
                            i[5] = Integer.toString(ID++);
                        }                       
                        writeLine = String.join(CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }

}
