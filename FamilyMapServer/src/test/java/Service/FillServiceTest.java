package Service;

import DataAccess.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

    private Database db;

    @BeforeEach
    void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        db.clearTables();
        new UserDao(db.getConnection()).insert(new TestData().getData().getUsers().get(0));
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown()throws DataAccessException {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void fill0()throws DataAccessException {
        new FillService().fill("sheila",0);
        db.openConnection();
        assertEquals(1, new PersonDao(db.getConnection()).getList("sheila").size());
        assertEquals(1, new EventDao(db.getConnection()).getList("sheila").size());
        db.closeConnection(true);
    }

    @Test
    void fill4()throws DataAccessException {
        new FillService().fill("sheila",4);
        db.openConnection();
        assertEquals(31, new PersonDao(db.getConnection()).getList("sheila").size());
        assertEquals(91, new EventDao(db.getConnection()).getList("sheila").size());
        db.closeConnection(true);
    }
}