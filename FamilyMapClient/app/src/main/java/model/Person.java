package model;

/**
 * This class stores all the information for an individual in a family tree.
 */
public class Person {

    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String gender;
    private String fatherID;
    private String motherID;
    private String spouseID;

    /**
     * A default constructor used to initialize all data members to null.
     */
    public Person() {
        this.personID = null;
        this.associatedUsername = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    /**
     * A parameterized constructor used to populate the class with usable values for its
     * data members.
     * @param personID A unique ID generated corresponding to this person.
     * @param associatedUsername The username of the user to whom this person corresponds.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @param gender The gender of the person.
     * @param fatherID The unique person_id of this person's father.
     * @param motherID The unique person_id of this person's mother.
     * @param spouseID The unique person_id of this person's spouse.
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        Person otherPerson = (Person) obj;

        //After going through the usual checks, compare the individual data members to see if the objects are identical.
        return (this.personID.equals(otherPerson.getPersonID()) && this.associatedUsername.equals(otherPerson.getAssociatedUsername())
                && this.firstName.equals(otherPerson.getFirstName()) && this.lastName.equals(otherPerson.getLastName())
                && this.gender.equals(otherPerson.getGender()) && this.fatherID.equals(otherPerson.getFatherID())
                && this.motherID.equals(otherPerson.getMotherID()) && this.spouseID.equals(otherPerson.getSpouseID()));
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
