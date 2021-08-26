package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePersonServiceTest {

    private Database db;
    private String token;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        LoadRequest data = new TestData().getData();
        new PersonDao(db.getConnection()).insert(new Person("Sheila_Parker", "sheila", "Sheila",
                "Parker", "f", "Blaine_McGary", "Betty_White", "Davis_Hyer"));
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
    void getSinglePersonPass() throws DataAccessException{
        SinglePersonResult result = new SinglePersonService().getSinglePerson("Sheila_Parker", token);

        assertTrue(result.isSuccess());
        assertEquals("Sheila_Parker", result.getPersonID());
    }

    @Test
    void getSinglePersonFail() throws DataAccessException{
        SinglePersonResult result = new SinglePersonService().getSinglePerson("Sheila_something", token);
        assertFalse(result.isSuccess());
        assertEquals("Error with invalid personID.", result.getMessage());

        SinglePersonResult result2 = new SinglePersonService().getSinglePerson("Sheila_Parker", "someToken");
        assertFalse(result2.isSuccess());
        assertEquals("Error with invalid auth token.", result2.getMessage());
    }
}