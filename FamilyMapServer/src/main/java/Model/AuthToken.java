package Model;

/**
 * This class stores a unique authorization token generated upon
 * a user's login, as well as the associated user's username.
 */
public class AuthToken {

    private String token;
    private String user_id;

    /**
     * A default constructor to initialize the data members to null values.
     */
    public AuthToken() {
        this.token = null;
        this.user_id = null;
    }

    /**
     * Basic parameterized constructor to create and populate a unique Authorization Token.
     * @param user_id This is the username associated with the auth token that is being created.
     */
    public AuthToken(String token, String user_id){
        setToken(token);
        setUser_id(user_id);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) { return false; }

        if (obj == this) { return true; }

        if (this.getClass() != obj.getClass()) { return false; }

        AuthToken otherToken = (AuthToken) obj;

        //After going through the usual checks, compare the individual data members to see if the objects are identical.
        return this.token.equals(otherToken.getToken()) && this.user_id.equals(otherToken.getUser_id());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
