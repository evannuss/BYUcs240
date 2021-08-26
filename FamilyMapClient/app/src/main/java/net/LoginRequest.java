package net;

/**
 * Stores the information needed to make a login request.
 */
public class LoginRequest {

    private String userName;
    private String password;

    /**
     * Parameterized constructor used to populate all data members.
     * @param username The username inputted by the person trying to login.
     * @param password The password inputted by the person trying to login.
     */
    public LoginRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public LoginRequest(){
        userName = null;
        password = null;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
