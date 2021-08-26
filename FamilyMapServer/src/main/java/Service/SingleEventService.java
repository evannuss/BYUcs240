package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import RequestResult.SingleEventResult;

/**
 * Responsible for actually fulfilling the request to find a single event.
 */
public class SingleEventService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to
     * actually get the requested event.
     *
     * @param event_id The unique ID of the requested event.
     * @return Returns a SingleEventResult object describing the result of the request.
     */
    public SingleEventResult getSingleEvent(String event_id, String authToken) throws DataAccessException {
        Database db = new Database();
        try {
            EventDao eDao = new EventDao(db.getConnection());
            AuthTokenDao atDao = new AuthTokenDao(db.getConnection());

            AuthToken token = atDao.find(authToken);
            if (token == null) {
                db.closeConnection(false);
                return new SingleEventResult("Error: invalid token.", false);
            }

            Event e = eDao.find(event_id);
            if (e == null) {
                db.closeConnection(false);
                return new SingleEventResult("Error with invalid eventID.", false);
            }

            if (!e.getAssociatedUsername().equals(token.getUser_id())) {
                db.closeConnection(false);
                return new SingleEventResult("Error: this event does not correspond to the given user.", false);
            }

            db.closeConnection(true);
            return new SingleEventResult(e, null, true);
        } catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new SingleEventResult("Error while trying to find given event.", false);
        }
    }
}
