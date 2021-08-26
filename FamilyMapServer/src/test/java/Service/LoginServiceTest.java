package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.User;
import RequestResult.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private Database db;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
        RegisterRequest request = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f");
        RegisterResult regResult = new RegisterService().register(request);
        assertTrue(regResult.isSuccess());
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void loginPass() throws DataAccessException{
        LoginResult result = new LoginService().login(new LoginRequest("sheila", "parker"));

        assertTrue(result.isSuccess());
        assertEquals("sheila", result.getUserName());
    }

    @Test
    void loginFail() throws DataAccessException{
        LoginResult result = new LoginService().login(new LoginRequest("jim", "parker"));
        assertFalse(result.isSuccess());
        assertEquals("Error with username/password.", result.getMessage());

        LoginResult result2 = new LoginService().login(new LoginRequest("sheila", "gonzalez"));
        assertFalse(result2.isSuccess());
        assertEquals("Error with username/password.", result2.getMessage());
    }
}