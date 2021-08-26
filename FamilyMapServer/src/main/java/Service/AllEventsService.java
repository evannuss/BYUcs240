package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import RequestResult.AllEventsResult;

import java.util.List;

/**
 * Responsible for actually fulfilling the request to show all events for all
 * family members of the current user.
 */
public class AllEventsService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to get and return all
     * events related to the current user.
     * @param authToken The username of the user whose events we need to get.
     * @return Returns an AllEventsResult containing a list of all the needed events.
     */
    public AllEventsResult getAllEvents(String authToken) throws DataAccessException{
        Database db = new Database();
        try {
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao atDao = new AuthTokenDao(db.getConnection());

            AuthToken token = atDao.find(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new AllEventsResult(null, "Error: invalid auth token.", false);
            }

            List<Event> events = eDao.getList(token.getUser_id());

            db.closeConnection(true);
            return new AllEventsResult(events, null, true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new AllEventsResult(null, "Error while getting list of people.", false);
        }
    }
}
