package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.User;
import RequestResult.LoginRequest;
import RequestResult.LoginResult;

/**
 * Responsible for actually fulfilling the request to login.
 */
public class LoginService {

    private Database db;

    /**
     * Interacts with the Model and DataAccess packages as necessary to log a user in.
     * @param l This LoginRequest will give us the info needed to try to log the user in.
     * @return Returns a LoginResult describing whether the login attempt was successful or not.
     */
    public LoginResult login(LoginRequest l) throws DataAccessException{
        db = new Database();
        try{
            db.openConnection();
            String newToken = getAuthToken(l.getUsername(), l.getPassword());
            if(newToken == null){
                db.closeConnection(false);
                return new LoginResult(null, null, null, "Error with username/password.", false);
            }
            UserDao uDao = new UserDao(db.getConnection());
            User user = uDao.find(l.getUsername());

            db.closeConnection(true);
            return new LoginResult(newToken, l.getUsername(), user.getPersonID(),null, true);
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            e.printStackTrace();
            return new LoginResult(null, null, null, "Error while logging in.", false);
        }
    }

    private String getAuthToken (String username, String password) throws DataAccessException{
        UserDao uDao = new UserDao(db.getConnection());
        User user = uDao.find(username);
        if(user == null || !user.getPassword().equals(password)){
            return null;
        }

        AuthTokenDao atDao = new AuthTokenDao(db.getConnection());

        String newToken = new ServiceHelpers(db.getConnection()).getRandomString("auth_token");

        atDao.insert(new AuthToken(newToken, username));

        return newToken;
    }
}
