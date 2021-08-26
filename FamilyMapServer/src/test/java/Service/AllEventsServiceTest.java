package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import RequestResult.AllEventsResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllEventsServiceTest {

    private Database db;
    private EventDao eDao;
    private AuthTokenDao atDao;
    private AuthToken token;
    private List<Event> events;
    private AllEventsService service;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        eDao = new EventDao(db.getConnection());
        atDao = new AuthTokenDao(db.getConnection());
        events = new ArrayList<>();
        service = new AllEventsService();
        token = new AuthToken("something_real", "greatUser");
        events.add(new Event("bleh", "greatUser",
                "bleh2", 14.334, 13.433, "Canada",
                "Alberta", "birth", 1990));
        events.add(new Event("bleh3", "greatUser",
                "bleh4", 15.334, 16.433, "Canada",
                "Alberta", "marriage", 2010));
        events.add(new Event("bleh7", "greatUser",
                "bleh19", 15.334, 16.433, "Canada",
                "Alberta", "death", 2020));
        atDao.insert(token);
        for(Event e : events){
            eDao.insert(e);
        }
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void getAllEventsPass() throws DataAccessException{
        AllEventsResult result = service.getAllEvents("something_real");
        assertTrue(result.isSuccess());
        for(Event e : events){
            assertTrue(result.getData().contains(e));
        }
    }

    @Test
    void getAllEventsFail() throws DataAccessException{
        AllEventsResult result = service.getAllEvents("something_not_real");
        assertFalse(result.isSuccess());
        assertNull(result.getData());
    }
}