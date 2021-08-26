package RequestResult;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.List;

/**
 * Used to stored all the necessary information for a load request.
 */
public class LoadRequest {

    private List<User> users;
    private List<Person> persons;
    private List<Event> events;

    /**
     * Parameterized constructor to populate all data members.
     * @param users A list of User objects that contains all necessary
     *                 information to register the new users.
     * @param persons A list of Person objects that contains all necessary
     *                  information to add the new people.
     * @param events A list of Event objects that contains all necessary
     *                  information to add the new events.
     */
    public LoadRequest(List<User> users, List<Person> persons, List<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Event> getEvents() {
        return events;
    }
}
