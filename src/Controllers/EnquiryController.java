package Controllers;
//put interface to show similarity btw enquiry and suggestion
import java.io.*;
import java.util.*;

//Enquiry.csv:
//Enquiry, Student, Camp, Read, Reply

/*General Rule for Enquiry Controller
-Any student can write an enquiry to any camp that he/she can see.
-Only the staff and camp committee member in charge of the camp can read and reply to the enquiry 
-If the enquiry has not been uploaded, the student can choose to delete the enquiry
-Student can view, edit, delete the enquiry before it is processed.

A student can submit enquiries to any camp he/she can see.
Cannot be deleted once the enquiry has been answered. It will be stored.
That reply can be seen by all the committee members and staff of that camp, besides the student who sent inquiry.

Enquiry flow:
-Student create an enquiry, type in the message (Each camp will only have one active enquiry at a time for simplicity)
-Take note of the name of the student, the name of the camp and the staff in charge
-The staff in charge can checkEnquiry to see if there is any pending enquiry
-The staff reads and replies to the enquiry, after which the read flag will be ticked and stored in the database. 
 */

public class EnquiryController {
    private static final String FILE_PATH = "src/Database/Enquiry.csv";
    private static final String CSV_SEPARATOR = ",";

    private String message;
    private String student;
    private String camp;
    private String read;
    private String reply;


    //IMPLEMENTATIONS FOR STUDENTS
    public void createEnquiry(String student){
        read = " ";
        reply = " ";

        Scanner sc = new Scanner(System.in);

        System.out.println("Select the camp to send the enquiry to: ");
        camp = sc.nextLine();
        
        System.out.println("Type in your message: ");
        message = sc.nextLine();

        String data = String.join(CSV_SEPARATOR,message, student, camp,read,reply);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            //writer.newLine();
            writer.write(data);
            writer.newLine();
            
        } catch (IOException e) {
            System.out.println("Error uploading the enquiry");
            e.printStackTrace();
        }
    }

    public String[] studentFindEnquiry(String student){
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[1].equalsIgnoreCase(student)){
                    return data;
                }
            }
            System.out.println("Cannot find your enquiry. Have you submitted an enquiry?");
            return null;
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return null;
    }

    public void viewEnquiry(String student){ //customized for student use 
        String[] data;
        if(studentFindEnquiry(student)!=null){
            data = studentFindEnquiry(student);
            formatMessage(data);
        }
        else
            System.out.println("No Enquiries to View");
    }

    public void editEnquiry(String student){ //Look for the enquiry based on the student's name, assuming a student can only send one enquiry to one camp
        String[] studentEnquiry = studentFindEnquiry(student);
        if(studentEnquiry == null){
            System.out.println("No Enquiries to Edit");
            return;
        }
        Scanner sc = new Scanner(System.in);

        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){
            System.out.println("Edit your enquiry here: ");
            message = sc.nextLine();
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
        String[] studentEnquiry = studentFindEnquiry(student);
        if(studentEnquiry==null){
            System.out.println("No Enquiries to Remove");
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

    public void readReply(String student){
        String[] studentEnquiry;
        if (studentFindEnquiry(student) != null){
            studentEnquiry = studentFindEnquiry(student);
        }
        else {
            System.out.println("Cannot find the enquiry");
            return;
        }
        if ((!studentEnquiry[3].equals(" ")) && (!studentEnquiry[4].equals(" "))){
            formatMessage(studentEnquiry);
        }
        else {
            System.out.println("Your enquiry has not been replied. Stay tuned!");
        }
    }

    //IMPLEMENTATIONS FOR STAFF/CAMP COMM
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

    public void replyEnquiry(String camp){
        String[] enquiryToReply = findEnquiry(camp);

        if ((!enquiryToReply[3].equals(" ")) && (!enquiryToReply[4].equals(" "))){
            System.out.println("The enquiry has already been replied!");
            return;
        } 
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in your reply: ");
        String Reply = sc.nextLine();
        enquiryToReply[3] = "Read";
        enquiryToReply[4] = Reply;
        System.out.println("Reply has been saved!");
        //sc.close();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))){                
                String line;
                String[] data;
                List<String[]> dataList = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if ((!data[2].equalsIgnoreCase(enquiryToReply[2])) && (!data[1].equals(enquiryToReply[1]))) { //cross-validate the student name and camp
                        dataList.add(data);
                    }
                    else {
                        dataList.add(enquiryToReply);
                    }
                }
                writeCSV(dataList);
        } catch (IOException e) {
            System.out.println("Cannot proceed with your edit request.");
            e.printStackTrace();
        }   
    }

    public List<String[]> execFindEnquiry(String camp){
        List<String[]> enquiryList = new ArrayList<String[]>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if (data[2] == camp){
                    enquiryList.add(data);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return enquiryList;
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

    //IMPLEMENTATIONS FOR ALL
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
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

    public void writeCSV(List<String[]> dataList){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    for (String[] i : dataList){
                        String writeLine = String.join(CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }

}
