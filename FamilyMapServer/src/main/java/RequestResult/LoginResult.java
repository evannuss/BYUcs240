package RequestResult;

/**
 * Used to store all of the information needed for the result of a login request.
 * Inherits from the Result class.
 */
public class LoginResult extends Result{

    private String authToken;
    private String userName;
    private String personID;

    /**
     * Parameterized constructor used to populate all data members, including
     * those of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param authToken The unique authorization token generated after logging
     *                  the user in.
     * @param userName The username of the user.
     * @param personID The unique ID of the user within the database.
     */
    public LoginResult(String authToken, String userName, String personID, String message, boolean success) {
        super(message,success);
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
