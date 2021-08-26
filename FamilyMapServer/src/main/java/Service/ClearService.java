package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import RequestResult.ClearResult;

/**
 * This class is responsible for actually implementing the Clear requests
 */
public class ClearService {

    /**
     * Interacts with the Model and DataAccess packages as necessary to clear the
     * database.
     * @return Returns a ClearResult object describing the result of the request.
     */
    public ClearResult clear() throws DataAccessException{
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);

            return new ClearResult("Clear succeeded", true);
        }
        catch(DataAccessException e){
            db.closeConnection(false);
            e.printStackTrace();
            return new ClearResult("Error occurred while trying to clear.", false);
        }
    }
}
