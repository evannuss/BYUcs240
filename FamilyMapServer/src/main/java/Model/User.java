package Model;

/**
 * This class stores all the information for a user. Created upon user registration.
 */
public class User {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    /**
     * A default constructor used to initialize all data members to null.
     */
    public User() {
        this.userName = null;
        this.password = null;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.personID = null;
    }

    /**
     * A parameterized constructor used to populate the class with usable values
     * for its data members.
     * @param userName The unique username created by the user.
     * @param password The password created by the user.
     * @param email The user's email address.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param gender The user's gender.
     * @param personID The user's unique ID used within the database.
     */
    public User(String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){ return false; }

        if (obj == this) { return true; }

        if (this.getClass() != obj.getClass()) { return false; }

        User otherUser = (User) obj;

        //After going through the usual checks, compare the individual data members to see if the objects are identical.
        return (this.userName.equals(otherUser.getUserName()) && this.password.equals(otherUser.getPassword())
                && this.email.equals(otherUser.getEmail()) && this.firstName.equals(otherUser.getFirstName())
                && this.lastName.equals(otherUser.getLastName()) && this.gender.equals(otherUser.getGender())
                && this.personID.equals(otherUser.getPersonID()));
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
