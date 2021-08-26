package Service;

import DataAccess.*;
import GivenData.*;
import Model.Event;
import Model.Person;
import Model.User;
import RequestResult.FillResult;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Random;

/**
 * Responsible for actually fulfilling the request to fill the generations of a user.
 */
public class FillService {

    private PersonDao pDao;
    private EventDao eDao;
    private Database db;
    private ServiceHelpers helper;
    private int birthYear;
    private int marriageYear;
    private int deathYear;
    private int newPeople;
    private int newEvents;
    private Location loc;

    /**
     * Interacts with the Model and DataAccess packages as necessary to try to complete
     * the fill request.
     * @param username The username of the user whose family tree we're filling.
     * @param generations The amount of generations we need to fill.
     * @return Returns a FillResult object describing the result of the request.
     */
    public FillResult fill(String username, int generations) throws DataAccessException {
        db = new Database();
        newEvents = 0;
        newPeople = 0;
        try {
            db.openConnection();
            helper = new ServiceHelpers(db.getConnection());
            UserDao uDao = new UserDao(db.getConnection());
            User currUser = uDao.find(username);

            Person userPerson = new Person(currUser.getPersonID(), username, currUser.getFirstName(),
                    currUser.getLastName(), currUser.getGender(), null, null, null);
            //Delete all previously existing people and events in the database associated with this username.
            pDao = new PersonDao(db.getConnection());
            pDao.deleteList(username);
            eDao = new EventDao(db.getConnection());
            eDao.deleteList(username);

            createUserBirth(userPerson); //The user only needs a birth event. Call the helper function specifically for that.

            if (generations == 0) { //If 0 is passed in, we just need to fill out the user and nothing else.
                pDao.insert(userPerson);
                newPeople++;
                db.closeConnection(true);
                return new FillResult("Successfully added " + newPeople + " persons and " + newEvents + " events to the database.", true);
            }

            fillGenerations(userPerson, generations);

            db.closeConnection(true);
            return new FillResult("Successfully added " + newPeople + " persons and " + newEvents + " events to the database.", true);
        }
        catch (DataAccessException e) {
            db.closeConnection(false);
            e.printStackTrace();
            return new FillResult("Error when filling.", false);
        }
    }

    private void fillGenerations(Person p, int generations) throws DataAccessException {
        if(generations == 0){
            pDao.insert(p);
            newPeople++;
            return;
        }

        String mom_id = helper.getRandomString("person");
        String dad_id = helper.getRandomString("person");
        p.setFatherID(dad_id);
        p.setMotherID(mom_id);

        pDao.insert(p);
        newPeople++;

        Random random = new Random();
        String mom_name = getFemaleNames()[random.nextInt(getFemaleNames().length)];
        String dad_name = getMaleNames()[random.nextInt(getMaleNames().length)];
        String last_name = getSurnames()[random.nextInt(getSurnames().length)];

        birthYear = birthYear - 25;
        marriageYear = birthYear + 22;
        deathYear = birthYear + 60;
        Person mom = new Person(mom_id, p.getAssociatedUsername(), mom_name, last_name, "f", null, null, dad_id);
        Person dad = new Person(dad_id, p.getAssociatedUsername(), dad_name, p.getLastName(), "m", null, null, mom_id);

        createEvent(mom, "birth");
        createEvent(dad, "birth");
        loc = getLocData()[random.nextInt(getLocData().length)];
        createEvent(mom, "marriage");
        createEvent(dad, "marriage");
        createEvent(mom, "death");
        createEvent(dad, "death");

        generations--;

        fillGenerations(mom, generations);
        fillGenerations(dad, generations);
        birthYear = birthYear + 25; //birthYear has to be reset at the end of the function to not create large age disaparities between generations.
    }

    private void createEvent(Person p, String event_type) throws DataAccessException{
        String event_id = helper.getRandomString("event");

        Random random = new Random();
        Location l = getLocData()[random.nextInt(getLocData().length)];

        if(event_type.equals("birth")){
            Event birth = new Event(event_id, p.getAssociatedUsername(), p.getPersonID(), Double.parseDouble(l.getLatitude()),
                    Double.parseDouble(l.getLongitude()), l.getCountry(), l.getCity(), "birth", birthYear);
            eDao.insert(birth);
            newEvents++;
        }
        else if(event_type.equals("marriage")){
            Event marriage = new Event(event_id, p.getAssociatedUsername(), p.getPersonID(), Double.parseDouble(loc.getLatitude()),
                    Double.parseDouble(loc.getLongitude()), loc.getCountry(), loc.getCity(), "marriage", marriageYear);
            eDao.insert(marriage);
            newEvents++;
        }
        else if(event_type.equals("death")){
            Event death = new Event(event_id, p.getAssociatedUsername(), p.getPersonID(), Double.parseDouble(l.getLatitude()),
                    Double.parseDouble(l.getLongitude()), l.getCountry(), l.getCity(), "death", deathYear);
            eDao.insert(death);
            newEvents++;
        }
        else{
            int randomYear = random.nextInt(deathYear - birthYear) + birthYear;
            Event rando = new Event(event_id, p.getAssociatedUsername(), p.getPersonID(), Double.parseDouble(l.getLatitude()),
                    Double.parseDouble(l.getLongitude()), l.getCountry(), l.getCity(), event_type, randomYear);
            eDao.insert(rando);
            newEvents++;
        }
    }

    private void createUserBirth(Person p) throws DataAccessException{
        Random random = new Random();
        Location location = getLocData()[random.nextInt(getLocData().length)];
        birthYear = random.nextInt(2010-1940) + 1940;

        String event_id = helper.getRandomString("event");

        Event birth = new Event(event_id, p.getAssociatedUsername(), p.getPersonID(), Double.parseDouble(location.getLatitude()),
                Double.parseDouble(location.getLongitude()), location.getCountry(), location.getCity(), "birth", birthYear);

        eDao.insert(birth);
        newEvents++;
    }

    private Location[] getLocData(){
        try{
            Reader reader = new FileReader("json/locations.json");
            Gson decoder = new Gson();
            LocationData locData = decoder.fromJson(reader, LocationData.class);

            return locData.getLocationData();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private String[] getFemaleNames(){
        try{
            Reader reader = new FileReader("json/fnames.json");
            Gson decoder = new Gson();
            FemaleNames femNames = decoder.fromJson(reader, FemaleNames.class);

            return femNames.getData();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private String[] getMaleNames(){
        try{
            Reader reader = new FileReader("json/mnames.json");
            Gson decoder = new Gson();
            MaleNames maleNames = decoder.fromJson(reader, MaleNames.class);

            return maleNames.getData();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private String[] getSurnames(){
        try{
            Reader reader = new FileReader("json/snames.json");
            Gson decoder = new Gson();
            Surnames surnames = decoder.fromJson(reader, Surnames.class);

            return surnames.getData();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
