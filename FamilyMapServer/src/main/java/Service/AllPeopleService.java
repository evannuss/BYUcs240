package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import RequestResult.AllPeopleResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for actually fulfilling the request to show all people related to the current user.
 */
public class AllPeopleService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to get and return all
     * people related to the current user.
     * @param authToken The auth token of the user whose family members we are getting.
     * @return Returns an AllPeopleResult containing a list all the necessary people.
     */
    public AllPeopleResult getAllPeople(String authToken) throws DataAccessException {
        Database db = new Database();
        try {
            PersonDao pDao = new PersonDao(db.getConnection());
            AuthTokenDao atDao = new AuthTokenDao(db.getConnection());

            AuthToken token = atDao.find(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new AllPeopleResult(null, "Error: invalid auth token.", false);
            }

            List<Person> people = pDao.getList(token.getUser_id());

            db.closeConnection(true);
            return new AllPeopleResult(people, null, true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new AllPeopleResult(null, "Error while getting list of people.", false);
        }
    }
}
