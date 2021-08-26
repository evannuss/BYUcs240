package RequestResult;

/**
 * Used to store the information needed to communicate the result of a
 * register request. Inherits from the Result class.
 */
public class RegisterResult extends Result{

    private String authToken;
    private String userName;
    private String personID;

    /**
     * Parameterized constructor used to populate all data members, including
     * those of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param authToken The unique authorization token generated after logging
     *                  the newly registered user in.
     * @param userName The username of the newly registered user.
     * @param personID The unique person ID of the newly registered user.
     */
    public RegisterResult(String authToken, String userName, String personID, String message, boolean success) {
        super(message, success);
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

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getPersonID() {
        return personID;
    }
}
