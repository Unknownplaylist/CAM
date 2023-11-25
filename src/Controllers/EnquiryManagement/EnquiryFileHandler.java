package Controllers.EnquiryManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.EnquiryManagementInterface.EnquiryFileHandlerInterface;

public class EnquiryFileHandler implements EnquiryFileHandlerInterface {
    static final String FILE_PATH = "src/Database/Enquiry.csv";
    /**
     * Retrieves all the enquiries from the file.
     * 
     * @return a list of String arrays representing the enquiries
     */
    @Override
    public List<String[]> findAllEnquiries(){
        List<String[]> enquiryList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {            
            String[] data;
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(EnquiryController.CSV_SEPARATOR);
                enquiryList.add(data);
            }
        } catch (IOException e) {
            System.out.println("Error reading enquiry file");
            e.printStackTrace();
        }
        return enquiryList;
    }

    /**
     * Writes the data from the given list to a CSV file.
     * Each element in the list represents a row in the CSV file.
     * The first element in each row is assumed to be the ID.
     * If the ID is not equal to "ID", it is replaced with a unique ID.
     * 
     * @param dataList the list of data to be written to the CSV file
     */
    @Override
    public void writeCSV(List<String[]> dataList){
        int ID = 1;
        String writeLine;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    for (String[] i : dataList){
                        if (!i[5].equals("ID")){
                            i[5] = Integer.toString(ID++);
                        }                       
                        writeLine = String.join(EnquiryController.CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }

    /**
     * Writes the given enquiry to the file.
     * 
     * @param enquiry the enquiry to be written to the file
     */
    @Override
    public void writeEnquiryToFile(String[] enquiry){
        List<String[]> existingEnquiry = findAllEnquiries();
        existingEnquiry.add(enquiry);
        writeCSV(existingEnquiry);
    }
    
}
