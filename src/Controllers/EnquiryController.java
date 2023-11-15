package Controllers;
//put interface to show similarity btw enquiry and suggestion
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
    private static final String TEMP_FILE_PATH = "src/Database/temp.csv"; //The temporary file path used for the delete/edit/reply Enquiry function
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
        //if not null
        String[]data;
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

        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){ //if not read and not replied
            System.out.println("Edit your enquiry here: ");
            message = sc.nextLine();
            studentEnquiry[0] = message;

            String editedEnquiry = String.join(CSV_SEPARATOR, studentEnquiry);

            BufferedReader br = null;
            BufferedWriter bw = null;

            try {
                br = new BufferedReader(new FileReader(FILE_PATH));
                bw = new BufferedWriter(new FileWriter(TEMP_FILE_PATH));
                String line;
                String[] data;
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(student)) {
                        bw.write(line);
                        bw.newLine();
                    }
                    else {
                        bw.write(editedEnquiry);
                        bw.newLine();
                    }
                }
                br.close();
                bw.close();

                // Rename temp file to the original file
                java.nio.file.Files.deleteIfExists(new File(FILE_PATH).toPath());
                new File(TEMP_FILE_PATH).renameTo(new File(FILE_PATH));
            } catch (IOException e) {
                System.out.println("Cannot proceed with your edit request.");
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
            System.out.println("A staff or Camp Committee Member has viewed your enquiry, cannot edit the enquiry.");
        }
        
    }

    public void deleteEnquiry(String student){ //Copy the csv file excluding the enquiry to delete and rename
        //Only from the student side, staff and CampComm cannot delete the enquiry
        //Only when not read and replied
        //cannot be deleted once answered
        String[] studentEnquiry = studentFindEnquiry(student);
        if(studentEnquiry==null){
            System.out.println("No Enquiries to Remove");
            return;
        }
        if ((studentEnquiry[3].equals(" ")) && (studentEnquiry[4].equals(" "))){ //if not read and not replied

            BufferedReader br = null;
            BufferedWriter bw = null;
            try {
                br = new BufferedReader(new FileReader(FILE_PATH));
                bw = new BufferedWriter(new FileWriter("temp.csv"));
                String line;
                String[] data;
                while ((line = br.readLine()) != null) {
                    data = line.split(CSV_SEPARATOR);
                    if (!data[1].equalsIgnoreCase(student)) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                br.close();
                bw.close();

                // Rename temp file to the original file
                java.nio.file.Files.deleteIfExists(new File(FILE_PATH).toPath());
                new File("temp.csv").renameTo(new File(FILE_PATH));
            } catch (IOException e) {
                System.out.println("An error has occured. Cannot proceed with your delete request.");
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
    public String[] checkEnquiry(String camp){ //for the staff and CampComm
        //Find the enquiry relevant to the camp that is not read and replied
        //return the line number for enquiry in csv file

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

    public void replyEnquiry(String camp){ //for staff and CampComm only
        //ERROR: This function only takes in the data and uploads a new one, the unreplied enquiry is still in the csv
        //The same as editEnquiry from the student side
        //take in line num of the enquiry from findEnquiry() if there is a pending enquiry
        String[] enquiryToReply = findEnquiry(camp);

        if ((enquiryToReply[3] != " ") && (enquiryToReply[4] != " ")){
            System.out.println("The enquiry has already been replied!");
            return;
        } 
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in your reply: ");
        String Reply = sc.nextLine();
        enquiryToReply[3] = "Read";
        enquiryToReply[4] = Reply;
        System.out.println("Reply has been saved!");
        sc.close();

        String upload = String.join(CSV_SEPARATOR,enquiryToReply);

        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            br = new BufferedReader(new FileReader(FILE_PATH));
            bw = new BufferedWriter(new FileWriter(TEMP_FILE_PATH));
            String line;
            String[] data;
            while ((line = br.readLine()) != null) {
                data = line.split(CSV_SEPARATOR);
                if ((data[1] != enquiryToReply[1]) && (data[2] != enquiryToReply[2])) { //check if the data is the same as enquiryToReply
                    bw.write(line);
                    bw.newLine();
                }
                else {
                    bw.write(upload);
                    bw.newLine();
                }
            }
            br.close();
            bw.close();

            // Rename temp file to the original file
            java.nio.file.Files.deleteIfExists(new File(FILE_PATH).toPath());
            new File(TEMP_FILE_PATH).renameTo(new File(FILE_PATH));
        } catch (IOException e) {
            System.out.println("Cannot proceed with your reply request.");
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

    public Vector<String[]> execFindEnquiry(String camp){
        Vector<String[]> enquiryList = new Vector<String[]>();
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

    public void viewReplyToEnquiry(String camp){ //display all replied enquiries
        //all camp committee members and staff in charge of that camp can see the enquiry
        Vector<String[]> enquiryList = execFindEnquiry(camp);
        for (String[] i : enquiryList){
            formatMessage(i);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }        
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

}
