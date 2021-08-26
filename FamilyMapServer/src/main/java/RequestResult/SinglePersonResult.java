package RequestResult;

import Model.Person;

/**
 * Used to store all necessary information for the result of a single person
 * find request. Inherits from the Result class.
 */
public class SinglePersonResult extends Result {

    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * Parameterized constructor to populate all data members,
     * including those of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     * @param p The Person object from which this class gets most of its data.
     */
    public SinglePersonResult(Person p, String errorMessage, boolean success) {
        super(errorMessage, success);
        this.associatedUsername = p.getAssociatedUsername();
        this.personID = p.getPersonID();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.gender = p.getGender();
        this.fatherID = p.getFatherID();
        this.motherID = p.getMotherID();
        this.spouseID = p.getSpouseID();
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
