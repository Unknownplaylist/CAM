package Controllers.SuggestionManagementInterface;

import java.util.List;

public interface SuggestionFileHandlerInterface {

    List<String[]> findAllSuggestions();

    void writeCSV(List<String[]> dataList);

    void writeSuggestionToFile(String[] enquiry);

}