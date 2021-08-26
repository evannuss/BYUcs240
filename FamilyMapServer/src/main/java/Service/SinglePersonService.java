package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import RequestResult.SinglePersonResult;

/**
 * Responsible for actually fulfilling the request to find a single person.
 */
public class SinglePersonService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to
     * actually get the requested person.
     * @param person_id The unique ID of the person we're finding.
     * @return Returns a SinglePersonResult object describing the outcome of the request.
     */
    public SinglePersonResult getSinglePerson(String person_id, String auth_token) throws DataAccessException{
        Database db = new Database();
        try{
            PersonDao pDao = new PersonDao(db.getConnection());
            AuthTokenDao atDao = new AuthTokenDao(db.getConnection());

            AuthToken token = atDao.find(auth_token);
            if(token == null){
                db.closeConnection(false);
                return new SinglePersonResult(new Person(), "Error with invalid auth token.", false);
            }

            Person p = pDao.find(person_id);
            if(p == null){
                db.closeConnection(false);
                return new SinglePersonResult(new Person(), "Error with invalid personID.", false);
            }

            if(!p.getAssociatedUsername().equals(token.getUser_id())){
                db.closeConnection(false);
                return new SinglePersonResult(new Person(), "Error: this person does not correspond to the given user.", false);
            }

            db.closeConnection(true);
            return new SinglePersonResult(p, null, true);
        }
        catch(DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new SinglePersonResult(null, "Error while trying to find given person.", false);
        }
    }
}
