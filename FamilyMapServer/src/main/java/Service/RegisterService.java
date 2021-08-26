package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;

/**
 * Responsible for actually fulfilling the request to register a new user.
 */
public class RegisterService {

    private Database db;

    /**
     * This method actually carries out the registering of the new user.
     * @param r Contains the information about the new user that is needed to register them.
     * @return Returns a RegisterResult object describing whether the request was successfully
     *         completed or not.
     */
    public RegisterResult register(RegisterRequest r) throws DataAccessException {
        db = new Database();
        try{
            db.openConnection();
            User user = new User(r.getUsername(), r.getPassword(), r.getEmail(),
                    r.getFirst_name(), r.getLast_name(), r.getGender(), null);
            String newToken = registerNewUser(user);

            if(newToken == null){
                return new RegisterResult(null, null, null,"Error with username not being unique.",false);
            }

            //db.closeConnection(true);
            return new RegisterResult(newToken, r.getUsername(), user.getPersonID(), null, true);
        }
        catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new RegisterResult(null, null, null,"Error when trying to register.",false);
        }
    }

    private String registerNewUser(User user) throws DataAccessException{
        try {
            UserDao uDao = new UserDao(db.getConnection());
            if (uDao.find(user.getUserName()) != null) {
                db.closeConnection(false);
                return null;
            }

            AuthTokenDao atDao = new AuthTokenDao(db.getConnection());
            ServiceHelpers help = new ServiceHelpers(db.getConnection());

            String newToken = help.getRandomString("auth_token");
            atDao.insert(new AuthToken(newToken, user.getUserName()));

            String newId = help.getRandomString("person");
            user.setPersonID(newId);
            uDao.insert(user);

            Person person = new Person(newId, user.getUserName(), user.getFirstName(),
                    user.getLastName(), user.getGender(), null, null, null);
            PersonDao pDao = new PersonDao(db.getConnection());
            pDao.insert(person);

            db.closeConnection(true);

            new FillService().fill(user.getUserName(), 4);

            return newToken;
        }
        catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return null;
        }

    }
}
