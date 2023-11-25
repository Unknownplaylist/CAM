package Controllers.SuggestionManagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Controllers.SuggestionManagementInterface.SuggestionFileHandlerInterface;

public class SuggestionFileHandler implements SuggestionFileHandlerInterface {
    static final String FILE_PATH = "src/Database/Suggestion.csv";
    /**
     * Retrieves all suggestions from the suggestion file.
     * 
     * @return A list of string arrays representing the suggestions.
     */
    @Override
    public List<String[]> findAllSuggestions(){
        List<String[]> suggestionList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {            
            String[] data;
            String line;
            while ((line = br.readLine()) != null) {
                data = line.split(SuggestionController.CSV_SEPARATOR);
                suggestionList.add(data);
            }
        } catch (IOException e) {
            System.out.println("Error reading suggestion file");
            e.printStackTrace();
        }
        return suggestionList;
    }

    /**
     * Writes the data from the given list to a CSV file.
     * Each element in the list represents a row in the CSV file.
     * The first element in each row is the ID, which is automatically generated.
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
                        writeLine = String.join(SuggestionController.CSV_SEPARATOR, i);
                        bw.write(writeLine);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error updating the file");
                    e.printStackTrace();
                }
    }

    
    /** 
     * @param enquiry
     */
    @Override
    public void writeSuggestionToFile(String[] enquiry){
        List<String[]> existingSuggestion = findAllSuggestions();
        existingSuggestion.add(enquiry);
        writeCSV(existingSuggestion);
    }
    
}
