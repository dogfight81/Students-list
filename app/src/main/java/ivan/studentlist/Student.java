package ivan.studentlist;

/**
 * Created by Ivan on 07.11.2016.
 */

public class Student {

    private String name;
    private String gitLink;
    private String googleLink;
    private String gitLogin;
    private String googleId;

    public Student(String name, String gitLink, String googleLink, String gitLogin, String googleId) {
        this.name = name;
        this.gitLink = gitLink;
        this.googleLink = googleLink;
        this.gitLogin = gitLogin;
        this.googleId = googleId;
    }

    public String getName() {
        return name;
    }

    public String getGitLink() {
        return gitLink;
    }

    public String getGoogleLink() {
        return googleLink;
    }

    public String getGitLogin() {
        return gitLogin;
    }

    public String getGoogleId() {
        return googleId;
    }
}
