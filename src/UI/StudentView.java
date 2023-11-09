package UI;
import java.util.*;
import Controllers.*;
import Models.*;

public class StudentView {
    String userid, name, faculty;
    public StudentView(String id){
        userid=id;

    }
    public void display(){
        System.out.println(userid);
    }

}
