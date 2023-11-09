package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StaffView {
    String userid, name, faculty;
    public StaffView(String id){
        userid=id;
    }
    public void display(){
        System.out.println(userid);
    }

}
