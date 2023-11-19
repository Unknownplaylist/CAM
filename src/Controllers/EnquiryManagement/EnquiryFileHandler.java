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

    @Override
    public void writeEnquiryToFile(String[] enquiry){
        List<String[]> existingEnquiry = findAllEnquiries();
        existingEnquiry.add(enquiry);
        writeCSV(existingEnquiry);
    }
    
}
