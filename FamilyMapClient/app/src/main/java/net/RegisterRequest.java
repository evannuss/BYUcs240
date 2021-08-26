package net;

/**
 * Used to store the different parts of a Register Request
 */
public class RegisterRequest {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;

    /**
     * Parameterized constructor used to populate the data members.
     * @param username The username chosen by the user.
     * @param password The password chosen by the user.
     * @param email The email given by the user.
     * @param first_name The user's first name.
     * @param last_name The user's last name.
     * @param gender The user's gender.
     */
    public RegisterRequest(String username, String password, String email, String first_name, String last_name, String gender) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = first_name;
        this.lastName = last_name;
        this.gender = gender;
    }

    public RegisterRequest() {
        userName = null;
        password = null;
        email = null;
        firstName = null;
        firstName = null;
        lastName = null;
        gender = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

