package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import RequestResult.RegisterRequest;
import RequestResult.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    Database db;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);

    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void registerPass() throws DataAccessException{
        RegisterRequest request = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        RegisterResult regResult = new RegisterService().register(request);

        assertTrue(regResult.isSuccess());
        assertEquals(31, new PersonDao(db.getConnection()).getList("sheila").size());
        assertEquals(91, new EventDao(db.getConnection()).getList("sheila").size());
        db.closeConnection(true);
    }

    @Test
    void registerFail() throws DataAccessException{
        registerPass();

        RegisterRequest request = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        RegisterResult regResult = new RegisterService().register(request);

        assertFalse(regResult.isSuccess());
        assertEquals("Error with username not being unique.", regResult.getMessage());
    }
}