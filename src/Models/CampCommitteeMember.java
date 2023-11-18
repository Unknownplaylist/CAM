package Models;

public class CampCommitteeMember extends Student {
    private String name;
    private String email;
    private String faculty;
    private String role;
    private String password; 
    private int point;

    CampCommitteeMember(String name, String email, String faculty, String role, String password, int point){
        super(name, email, faculty, role, password, null);
        point = 0;
    }

    public int getPoint(){
        return point;
    }
}
