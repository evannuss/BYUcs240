package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.UserDao;
import Model.Event;
import Model.User;
import RequestResult.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleEventServiceTest {

    private Database db;
    private String token;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        LoadRequest data = new TestData().getData();
        new EventDao(db.getConnection()).insert(new Event("Sheila_Birth", "sheila", "Sheila_Parker",
                -36.1833, 144.9667, "Australia", "Melbourne", "birth", 1970));
        new UserDao(db.getConnection()).insert(new User("sheila", "parker", "sheila@parker.com",
                "Sheila", "Parker", "f", "Sheila_Parker"));
        db.closeConnection(true);
        LoginResult logResult = new LoginService().login(new LoginRequest("sheila", "parker"));
        token = logResult.getAuthToken();
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void getSingleEventPass() throws DataAccessException{
        SingleEventResult result = new SingleEventService().getSingleEvent("Sheila_Birth", token);

        assertTrue(result.isSuccess());
        assertEquals("Sheila_Birth", result.getEventID());
    }

    @Test
    void getSingleEventFail() throws DataAccessException{
        SingleEventResult result = new SingleEventService().getSingleEvent("Sheila_something", token);
        assertFalse(result.isSuccess());
        assertEquals("Error with invalid eventID.", result.getMessage());

        SingleEventResult result2 = new SingleEventService().getSingleEvent("Sheila_Birth", "someToken");
        assertFalse(result2.isSuccess());
        assertEquals("Error: invalid token.", result2.getMessage());
    }
}