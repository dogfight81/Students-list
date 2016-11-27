package ivan.studentlist.models;

public class Student {

    private String name;
    private String gitLogin;
    private String googleId;

    public Student(String name, String gitLogin, String googleId) {
        this.name = name;
        this.gitLogin = gitLogin;
        this.googleId = googleId;
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
}
