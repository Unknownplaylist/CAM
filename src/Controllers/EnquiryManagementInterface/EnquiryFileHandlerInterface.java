package Controllers.EnquiryManagementInterface;

import java.util.List;

public interface EnquiryFileHandlerInterface {

    List<String[]> findAllEnquiries();

    void writeCSV(List<String[]> dataList);

    void writeEnquiryToFile(String[] enquiry);

}