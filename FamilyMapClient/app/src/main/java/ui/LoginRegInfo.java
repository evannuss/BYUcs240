package ui;

public class LoginRegInfo {
    private boolean isLogin;
    private String hostName;
    private int port;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;

    public LoginRegInfo(boolean isLogin, String hostName, int port, String userName,
                        String password, String firstName, String lastName, String email, String gender) {
        this.isLogin = isLogin;
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
