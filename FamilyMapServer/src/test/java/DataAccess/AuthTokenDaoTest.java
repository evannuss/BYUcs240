package DataAccess;

import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {

    private Database db;
    private AuthToken token;
    private AuthTokenDao atDao;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        token = new AuthToken("jh6h5kk3jj4kl5k4l", "8g99f9diifje4");
        Connection conn = db.getConnection();
        atDao = new AuthTokenDao(conn);
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException{
        atDao.insert(token);
        assertTrue(token.equals(atDao.find(token.getToken())));
    }

    @Test
    void insertFail() throws DataAccessException{
        atDao.insert(token);
        assertThrows(DataAccessException.class, ()-> atDao.insert(token));
    }

    @Test
    void findPass() throws DataAccessException{
        atDao.insert(token);
        assertTrue(token.equals(atDao.find(token.getToken())));
    }

    @Test
    void findFail() throws DataAccessException{
        assertNull(atDao.find(token.getToken()));
    }

    @Test
    void clearTest() throws DataAccessException {
        atDao.insert(token);
        AuthToken token2 = new AuthToken("randomsomething", "ohyouknow");

        atDao.insert(token2);
        assertNotNull(atDao.find(token.getToken()));
        assertNotNull(atDao.find(token2.getToken()));

        atDao.clear();
        assertNull(atDao.find(token.getToken()));
        assertNull(atDao.find(token2.getToken()));
    }
}