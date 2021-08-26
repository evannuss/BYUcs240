package DataAccess;

import Model.Event;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException{
        db = new Database();

        person = new Person("54hh3u", "greatUser", "Billy", "Billyson", "m",
                            "uu7hh6u5", "8h88gudk", "9s9f9fi");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException{
        db.closeConnection(false);
    }

    @Test
    void insertPass() throws DataAccessException{
        pDao.insert(person);
        boolean testPassed = person.equals(pDao.find(person.getPersonID()));
        assertTrue(testPassed);
    }

    @Test
    public void insertFail() throws DataAccessException{
        pDao.insert(person);
        assertThrows(DataAccessException.class, ()-> pDao.insert(person));
    }

    @Test
    void findPass() throws DataAccessException{
        pDao.insert(person);
        boolean testPassed = person.equals(pDao.find(person.getPersonID()));
        assertTrue(testPassed);
    }

    @Test
    void findFail() throws DataAccessException{
        assertNull(pDao.find(person.getPersonID()));
    }

    @Test
    void testClear1() throws DataAccessException{
        pDao.insert(person);
        Person person2 = new Person("7g78d00s88", "betterUsername", "Bobby", "Bobbyson",
                                    "m", "7g7dyshwhw", "09876554", "o0ok0ok");
        pDao.insert(person2);
        assertNotNull(pDao.find(person.getPersonID()));
        assertNotNull(pDao.find(person2.getPersonID()));

        pDao.clear();
        assertNull(pDao.find(person.getPersonID()));
        assertNull(pDao.find(person2.getPersonID()));
    }

    @Test
    void testClear2() throws DataAccessException{
        pDao.clear();
        assertNull(pDao.find(person.getPersonID()));
    }

    @Test
    void deleteList1() throws DataAccessException{
        pDao.insert(person);
        Person person2 = new Person("7g78d00s88", "greatUser", "Bobby", "Bobbyson",
                "m", "7g7dyshwhw", "09876554", "o0ok0ok");
        pDao.insert(person2);

        assertNotNull(pDao.find(person.getPersonID()));
        assertNotNull(pDao.find(person.getPersonID()));

        pDao.deleteList("greatUser");
        assertNull(pDao.find(person.getPersonID()));
        assertNull(pDao.find(person.getPersonID()));
    }

    @Test
    void deleteList2() throws DataAccessException{
        pDao.deleteList(person.getAssociatedUsername());
        assertNull(pDao.find(person.getPersonID()));
    }

    @Test
    void getList1() throws DataAccessException{
        pDao.insert(person);
        Person person2 = new Person("7g78d00s88", "greatUser", "Bobby", "Bobbyson",
                "m", "7g7dyshwhw", "09876554", "o0ok0ok");
        pDao.insert(person2);

        assertNotNull(pDao.find(person.getPersonID()));
        assertNotNull(pDao.find(person2.getPersonID()));

        List<Person> people = pDao.getList(person.getAssociatedUsername());

        assertTrue(people.contains(person));
        assertTrue(people.contains(person2));
    }

    @Test
    void getList2() throws DataAccessException{
        List<Person> people = pDao.getList(person.getAssociatedUsername());
        assertTrue(people.isEmpty());
    }

}