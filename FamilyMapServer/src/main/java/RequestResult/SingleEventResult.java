package RequestResult;

import Model.Event;

/**
 * Used to store all necessary information for the result of a single event
 * find request. Inherits from the Result class.
 */
public class SingleEventResult extends Result {

    private String associatedUsername;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    /**
     *
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     * @param e The event from which this class will draw most of its data.
     */
    public SingleEventResult(Event e, String errorMessage, boolean success) {
        super(errorMessage, success);
        this.eventID = e.getEventID();
        this.associatedUsername = e.getAssociatedUsername();
        this.personID = e.getPersonID();
        this.latitude = e.getLatitude();
        this.longitude = e.getLongitude();
        this.country = e.getCountry();
        this.city = e.getCity();
        this.eventType = e.getEventType();
        this.year = e.getYear();
    }

    public SingleEventResult(String errorMessage, boolean success){
        super(errorMessage, success);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
