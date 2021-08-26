package DataAccess;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private User user;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();

        user = new User("something","goodPassword","coolKid@gmail.com",
                "Bob","Bobson","m","5476676");
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException{
        uDao.insert(user);
        boolean testPassed = user.equals(uDao.find(user.getUserName()));
        assertTrue(testPassed);
    }

    @Test
    public void insertFail() throws DataAccessException{
        uDao.insert(user);
        assertThrows(DataAccessException.class, ()-> uDao.insert(user));
    }

    @Test
    void findPass() throws DataAccessException{
        uDao.insert(user);
        boolean testPassed = user.equals(uDao.find(user.getUserName()));
        assertTrue(testPassed);
    }

    @Test
    void findFail() throws DataAccessException{
        assertNull(uDao.find(user.getUserName()));
    }

    @Test
    void testClear() throws DataAccessException{
        uDao.insert(user);
        User user2 = new User("goodUsername", "greatPassword", "coolerkid@gmail.com",
                             "Jim", "Jimson", "m", "u6y5i34o");
        uDao.insert(user2);
        assertNotNull(uDao.find(user.getUserName()));
        assertNotNull(uDao.find(user2.getUserName()));

        uDao.clear();
        assertNull(uDao.find(user.getUserName()));
        assertNull(uDao.find(user2.getUserName()));
    }
}