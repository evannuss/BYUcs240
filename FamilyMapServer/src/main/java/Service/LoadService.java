package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import RequestResult.LoadResult;

/**
 * Responsible for actually fulfilling the request to clear the database and fill it
 * with the given data.
 */
public class LoadService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to actually complete
     * the load result.
     * @param l This is the LoadRequest received from the client that has all the info needed
     *          to complete the load.
     * @return Returns a LoadResult object that describes whether the request was successfully
     *         completed or not.
     */
    public LoadResult load(LoadRequest l) throws DataAccessException {
        Database db = new Database();
        try {
            new ClearService().clear();

            PersonDao pDao = new PersonDao(db.getConnection());
            for(Person p : l.getPersons()){
                if(pDao.find(p.getPersonID()) == null) {
                    pDao.insert(p);
                }
            }

            UserDao uDao = new UserDao(db.getConnection());
            for(User u : l.getUsers()){
                uDao.insert(u);
            }

            EventDao eDao = new EventDao(db.getConnection());
            for(Event e : l.getEvents()){
                if(eDao.find(e.getEventID()) == null) {
                    eDao.insert(e);
                }
            }

            db.closeConnection(true);

            return new LoadResult(true, "Successfully added " + l.getUsers().size() + " users, "
                    + l.getPersons().size() + " persons, and " + l.getEvents().size() + " events to the database.");
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            e.printStackTrace();
            return new LoadResult(false, "Error occurred while trying to load.");
        }
    }
}
