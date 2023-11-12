package Controllers;

import java.io.*;
import java.util.*;

//Enquiry.csv:
//Previously: Enquiry, Student, Camp, STAFF, Read, Reply
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
    // private String staff; //Don't need the staff name at all
    private String read;
    private String reply;


    //IMPLEMENTATIONS FOR STUDENTS
    public void createEnquiry(String student){
        //Generate the enquiry details, including the name of the student and set the read and reply section to ""
        this.student = student;
        read = " ";
        reply = " ";

        Scanner sc = new Scanner(System.in);

        System.out.println("Select the camp to send the enquiry to: ");
        camp = sc.nextLine(); //Need to access camp information to find out the staff in charge, put "TBD" for now
        
        System.out.println("Type in your message: ");
        message = sc.nextLine();

        //sc.close(); - closing the Scanner will affect the Scanner of the main class

        String data = String.join(CSV_SEPARATOR,message, student, camp,read,reply);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
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
                if (data[1] == student){
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
        //if not null
        String[] data = studentFindEnquiry(student);
        formatMessage(data);
    }

    public void editEnquiry(String student){ //Look for the enquiry based on the student's name, assuming a student can only send one enquiry to one camp

    }

    public void deleteEnquiry(String student){ //Copy the csv file excluding the enquiry to delete and rename
        //Only from the student side, staff and CampComm cannot delete the enquiry
        //Only when not read and replied
        //cannot be deleted once answered
        String[] studentEnquiry = studentFindEnquiry(student);
        if ((studentEnquiry[3] != " ") && (studentEnquiry[4] != " ")){

            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = new BufferedReader(new FileReader(FILE_PATH));
                bw = new BufferedWriter(new FileWriter("temp.csv"));
                String line;
                String[] data;
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (data[1] != student) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                br.close();
                bw.close();

                // Rename temp file to the original file
                java.nio.file.Files.deleteIfExists(new File("Enquiry.csv").toPath());
                new File("temp.csv").renameTo(new File("Enquiry.csv"));
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                    if (bw != null)
                        bw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        else {
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot delete the enquiry.");
        }
    }

    public void readReply(String student){
        //return the reply of the enquiry
        //if the enquiry has not been replied (i.e. read == " " and reply == " "), return nothing
    }

    //IMPLEMENTATIONS FOR STAFF/CAMP COMM
    public String[] checkEnquiry(String camp){ //for the staff and CampComm
        //Find the enquiry relevant to the camp that is not read and replied
        //return the line number for enquiry in csv file

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int lineNum = 0;
            while ((line = br.readLine()) != null) {
                lineNum ++;
                String[] data = line.split(CSV_SEPARATOR);
                if (Objects.equals(data[2], camp) && Objects.equals(data[3], " ")){                   
                    System.out.println("There is an enquiry for your camp pending for reply.");
                    formatMessage(data);
                    return data;                    
                }
            }
            System.out.println("No pending enquiry.");
            return null;
        } catch (IOException e) {
            System.out.println("Error displaying the enquiry");
            e.printStackTrace();
        }
        return null;
    }

    public String[] findEnquiry(int lineNum){
        String line;
        int current = 0;
        String[] data;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                if (current == lineNum){
                    data = line.split(CSV_SEPARATOR);
                    return data;
                }
                current ++;
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        System.out.println("Cannot find the enquiry.");
        return null;
    }

    public void replyEnquiry(String[] data){ //for staff and CampComm only
        //ERROR: This function only takes in the data and uploads a new one, the unreplied enquiry is still in the csv
        //need to find the enquiry first
        //take in line num of the enquiry from findEnquiry() if there is a pending enquiry
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in your reply: ");
        String Reply = sc.nextLine();
        data[3] = "Read";
        data[4] = Reply;
        System.out.println("Reply has been sent!");
        sc.close();

        String upload = String.join(CSV_SEPARATOR,data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(upload);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error uploading the enquiry");
            e.printStackTrace();
        }
    }

    public String viewReplyToEnquiry(){
        //all camp committee members and staff in charge of that camp can see the enquiry
        
        return reply;
    }

    //IMPLEMENTATIONS FOR ALL
    public void formatMessage(String[] data){
        //Print out formatted view of the enquiry
        try {
            System.out.println("From: "+data[1]);
            System.out.println("To "+data[2] + " Organinzing Committee");
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

}
