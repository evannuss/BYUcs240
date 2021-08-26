package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.LoadRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

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
    void loadPass() throws DataAccessException{
        LoadRequest data = new TestData().getData();
        new LoadService().load(data);

        db.openConnection();
        assertEquals(16, new EventDao(db.getConnection()).getList(data.getUsers().get(0).getUserName()).size());
        assertEquals(3, new EventDao(db.getConnection()).getList(data.getUsers().get(1).getUserName()).size());
        assertEquals(8, new PersonDao(db.getConnection()).getList(data.getUsers().get(0).getUserName()).size());
        assertEquals(3, new PersonDao(db.getConnection()).getList(data.getUsers().get(1).getUserName()).size());
        assertTrue(data.getUsers().get(0).equals(new UserDao(db.getConnection()).find(data.getUsers().get(0).getUserName())));
        db.closeConnection(true);
    }

    @Test
    void loadEmpty() throws DataAccessException{
        LoadRequest data = new LoadRequest(new ArrayList<User>(), new ArrayList<Person>(), new ArrayList<Event>());
        new LoadService().load(data);

        db.openConnection();
        assertTrue(new EventDao(db.getConnection()).getList("sheila").isEmpty());
        assertTrue(new PersonDao(db.getConnection()).getList("sheila").isEmpty());
        assertNull(new UserDao(db.getConnection()).find("sheila"));
        db.closeConnection(true);
    }
}