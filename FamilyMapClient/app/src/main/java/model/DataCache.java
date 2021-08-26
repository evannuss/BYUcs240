package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCache {

    private static DataCache _instance = new DataCache();

    public static DataCache getInstance(){
        return _instance;
    }

    private Map<String, Person> people;
    private Map<String, Event> events;
    private Map<String, Float> colorMapping;
    private Event eventToDisplay;

    private DataCache(){
        people = new HashMap<>();
        events = new HashMap<>();
        colorMapping = new HashMap<>();
    }

    public void setPeople(List<Person> peopleList){
        for(Person p : peopleList){
            _instance.people.put(p.getPersonID(), p);
        }
    }

    public Map<String, Person> getPeople(){
        return _instance.people;
    }

    public void setEvents(List<Event> eventList){
        float[] colors = {210.0f, 0.0f, 180.0f, 120.0f, 240.0f, 300.0f, 30.0f, 330.0f, 270.0f, 60.0f};
        int i = 0;

        for(Event e : eventList){
            if(!colorMapping.containsKey(e.getEventType())){
                _instance.colorMapping.put(e.getEventType().toLowerCase(), colors[i]);
                i++;
                if(i == colors.length){
                    i = 0;
                }
            }
            _instance.events.put(e.getEventID(), e);
        }
    }

    public Map<String, Event> getEvents(){
        return _instance.events;
    }

    public Map<String, Float> getColorMapping() { return colorMapping; }

    public Event getEventToDisplay() {
        return eventToDisplay;
    }

    public void setEventToDisplay(Event eventToDisplay) {
        this.eventToDisplay = eventToDisplay;
    }
}
