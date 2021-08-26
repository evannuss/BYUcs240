package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import RequestResult.AllPeopleResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AllPeopleServiceTest {

    private Database db;
    private PersonDao pDao;
    private AuthTokenDao atDao;
    private AuthToken token;
    private List<Person> people;
    private AllPeopleService service;

    @BeforeEach
    void setUp()throws DataAccessException {
        db = new Database();
        pDao = new PersonDao(db.getConnection());
        atDao = new AuthTokenDao(db.getConnection());
        people = new ArrayList<>();
        service = new AllPeopleService();
        token = new AuthToken("something_real", "greatUser");
        people.add(new Person("thisPerson","greatUser",
                "Jim", "Parker", "m", "JimsFather",
                "JimsMother", "JimsWife"));
        people.add(new Person("thatPerson","greatUser",
                "Bob", "Parker", "m", "null",
                "BobsMother", "BobsSpouse"));
        people.add(new Person("otherPerson","greatUser",
                "Agatha", "Christie", "f", "othernotnull",
                "Something", "notnull"));
        atDao.insert(token);
        for(Person p : people){
            pDao.insert(p);
        }
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() throws DataAccessException{
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void getAllPeoplePass() throws DataAccessException{
        AllPeopleResult result = service.getAllPeople("something_real");
        assertTrue(result.isSuccess());
        for(Person p : people){
            assertTrue(result.getData().contains(p));
        }
    }

    @Test
    void getAllPeopleFail() throws DataAccessException{
        AllPeopleResult result = service.getAllPeople("something__real");
        assertFalse(result.isSuccess());
        assertNull(result.getData());
    }
}