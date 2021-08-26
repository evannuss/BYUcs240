package Model;

/**
 * This class stores all relevant information related
 * to a specific event from someone's life.
 */
public class Event {

    private String eventID;
    private String associatedUsername;
    private String personID;
    private double latitude;
    private double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;


    /**
     * Basic parameterized constructor used to populate all members of the class
     * @param eventID A unique ID corresponding this event.
     * @param associatedUsername The username of the user to whom this event corresponds.
     * @param personID The unique ID of the person to whom this event corresponds.
     * @param latitude The latitude of the location where the event took place.
     * @param longitude The longitude of the location where the event took place.
     * @param country The name of the country where the event took place.
     * @param city The name of the city where the event took place.
     * @param eventType The specific type of event.
     * @param year The year in which the event took place.
     */
    public Event(String eventID, String associatedUsername, String personID, double latitude, double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Event otherEvent = (Event) obj;

        //After going through the usual checks, compare the individual data members to see if the objects are identical.
        return ((this.longitude == otherEvent.getLongitude()) && (this.latitude == otherEvent.getLatitude()) &&
                (this.associatedUsername.equals(otherEvent.associatedUsername)) && (this.city.equals(otherEvent.city)) &&
                (this.country.equals(otherEvent.country)) && (this.eventID.equals(otherEvent.eventID)) &&
                (this.eventType.equals(otherEvent.eventType)) && (this.personID.equals(otherEvent.personID)) &&
                (this.year == otherEvent.year));

    }

    private String generateUniqueID(){
        return null;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
