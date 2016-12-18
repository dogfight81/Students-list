package ivan.studentlist.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Student extends RealmObject{

    @PrimaryKey
    private int id;
    private String name;
    private String gitLogin;
    private String googleId;
    private String searchName;

    public Student(){

    }

    public Student(int id, String name, String gitLogin, String googleId) {
        this.id = id;
        this.name = name;
        this.gitLogin = gitLogin;
        this.googleId = googleId;
        searchName = name.toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getGitLogin() {
        return gitLogin;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getSearchName() {
        return searchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
