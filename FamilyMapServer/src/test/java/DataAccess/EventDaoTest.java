package DataAccess;

import Model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {

    private Database db;
    private Event event;
    private EventDao eDao;

    @BeforeEach
    void setUp() throws DataAccessException{
        db = new Database();
        event = new Event("some_event", "greatUser", "some_person", 17.000,
                18.000, "USA", "Evanston", "some type", 1990);
        eDao = new EventDao(db.getConnection());
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException{
        eDao.insert(event);
        assertTrue(event.equals(eDao.find(event.getEventID())));
    }

    @Test
    void insertFail() throws DataAccessException{
        eDao.insert(event);
        assertThrows(DataAccessException.class, ()-> eDao.insert(event));
    }

    @Test
    void findPass() throws DataAccessException{
        eDao.insert(event);
        assertTrue(event.equals(eDao.find(event.getEventID())));
    }

    @Test
    void findFail() throws DataAccessException{
        assertNull(eDao.find(event.getEventID()));
    }

    @Test
    void testClear1() throws DataAccessException{
        eDao.insert(event);
        Event event2 = new Event("other_ID", "other_user", "other_personID", 17.565,
                10.223, "CountryPlace", "CountryVille", "type", 1876);
        eDao.insert(event2);

        assertNotNull(eDao.find(event.getEventID()));
        assertNotNull(eDao.find(event2.getEventID()));

        eDao.clear();
        assertNull(eDao.find(event.getEventID()));
        assertNull(eDao.find(event2.getEventID()));
    }

    @Test
    void testClear2() throws DataAccessException{
        eDao.clear();
        assertNull(eDao.find(event.getEventID()));
    }

    @Test
    void deleteList1() throws DataAccessException{
        eDao.insert(event);
        Event event2 = new Event("other_ID", "greatUser", "other_personID", 17.565,
                10.223, "CountryPlace", "CountryVille", "type", 1876);
        eDao.insert(event2);

        assertNotNull(eDao.find(event.getEventID()));
        assertNotNull(eDao.find(event2.getEventID()));

        eDao.deleteList("greatUser");
        assertNull(eDao.find(event.getEventID()));
        assertNull(eDao.find(event2.getEventID()));
    }

    @Test
    void deleteList2() throws DataAccessException{
        eDao.deleteList(event.getAssociatedUsername());
        assertNull(eDao.find(event.getEventID()));
    }

    @Test
    void getList1() throws DataAccessException{
        eDao.insert(event);
        Event event2 = new Event("other_ID", "greatUser", "other_personID", 17.565,
                10.223, "CountryPlace", "CountryVille", "type", 1876);
        eDao.insert(event2);

        assertNotNull(eDao.find(event.getEventID()));
        assertNotNull(eDao.find(event2.getEventID()));

        List<Event> events = eDao.getList(event.getAssociatedUsername());

        assertTrue(events.contains(event));
        assertTrue(events.contains(event2));
    }

    @Test
    void getList2() throws DataAccessException{
        List<Event> events = eDao.getList(event.getAssociatedUsername());
        assertTrue(events.isEmpty());
    }
}