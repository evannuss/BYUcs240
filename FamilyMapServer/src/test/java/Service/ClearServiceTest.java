package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.ClearResult;
import RequestResult.LoadRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private Database db;
    private LoadRequest data;
    private List<AuthToken> tokens;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        data = new TestData().getData();
        tokens = new TestData().getTokens();
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void clearTest1() throws DataAccessException{

        Connection conn = db.getConnection();
        UserDao uDao = new UserDao(conn);
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        AuthTokenDao atDao = new AuthTokenDao(conn);

        for(User u : data.getUsers()){
            uDao.insert(u);
        }
        for(Person p : data.getPersons()){
            pDao.insert(p);
        }
        for(Event e : data.getEvents()){
            eDao.insert(e);
        }
        for(AuthToken at : tokens){
            atDao.insert(at);
        }

        db.closeConnection(true);

        new ClearService().clear();

        conn = db.openConnection();
        UserDao uDao2 = new UserDao(conn);
        PersonDao pDao2 = new PersonDao(conn);
        EventDao eDao2 = new EventDao(conn);
        AuthTokenDao atDao2 = new AuthTokenDao(conn);

        assertTrue(pDao2.getList(data.getUsers().get(0).getUserName()).isEmpty());
        assertTrue(eDao2.getList(data.getUsers().get(1).getUserName()).isEmpty());
        assertNull(atDao2.find(tokens.get(0).getToken()));
        assertNull(uDao2.find(data.getUsers().get(0).getUserName()));
        db.closeConnection(true);
    }

    @Test
    void clearTest2() throws DataAccessException{
        new ClearService().clear();

        Connection conn = db.openConnection();
        UserDao uDao2 = new UserDao(conn);
        PersonDao pDao2 = new PersonDao(conn);
        EventDao eDao2 = new EventDao(conn);
        AuthTokenDao atDao2 = new AuthTokenDao(conn);

        assertTrue(pDao2.getList(data.getUsers().get(0).getUserName()).isEmpty());
        assertTrue(eDao2.getList(data.getUsers().get(1).getUserName()).isEmpty());
        assertNull(atDao2.find(tokens.get(0).getToken()));
        assertNull(uDao2.find(data.getUsers().get(0).getUserName()));
        db.closeConnection(true);
    }
}