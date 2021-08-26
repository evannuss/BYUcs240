package net;

import java.util.List;

import model.Person;

/**
 * Used to store all information needed for the result of a request
 * asking for all family members of the current user.
 * Inherits from the Result class.
 */
public class AllPeopleResult extends Result {

    private List<Person> data;

    /**
     * Parameterized constructor used to populate all data members,
     * including those of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     * @param data An array of SinglePersonResult objects containing the info
     *                  of all family members of the current user.
     */
    public AllPeopleResult(List<Person> data, String errorMessage,boolean success) {
        super(errorMessage, success);
        this.data = data;
    }

    public List<Person> getData() {
        return data;
    }
}
