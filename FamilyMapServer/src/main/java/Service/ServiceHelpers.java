package Service;

import DataAccess.*;

import java.sql.Connection;
import java.util.Random;

public class ServiceHelpers {

    private Connection conn;

    public ServiceHelpers(Connection conn){
        this.conn = conn;
    }

    public String randomString(int length){
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder randomSb = new StringBuilder();
        Random rnd = new Random();
        while (randomSb.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * possibleChars.length());
            randomSb.append(possibleChars.charAt(index));
        }
        return randomSb.toString();
    }

    public String getRandomString(String type) throws DataAccessException {
        String id = null;
        if(type.equals("event")){
            EventDao eDao = new EventDao(conn);
            id = randomString(25);
            while((eDao.find(id)) != null) {
                id = randomString(25);
            }
        }
        if(type.equals("person")){
            PersonDao pDao = new PersonDao(conn);
            id = randomString(25);
            while(pDao.find(id) != null){
                id = randomString(25);
            }
        }
        if(type.equals("auth_token")){
            AuthTokenDao atDao = new AuthTokenDao(conn);
            id = randomString(20);
            while(atDao.find(id) != null){
                id = randomString(20);
            }
        }
        if(type.equals("user")){
            UserDao uDao = new UserDao(conn);
            id = randomString(25);
            while(uDao.find(id) != null){
                id = randomString(25);
            }
        }
        return id;
    }
}
