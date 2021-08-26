package RequestResult;

import Model.Event;

import java.util.List;

/**
 * Used to store all information needed for the result of a request
 * asking for all events associated with the current user.
 * Inherits from the Result class.
 */
public class AllEventsResult extends Result {

    private List<Event> data;

    /**
     * Parameterized constructor used to populate all data members,
     * including those of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     * @param data A list of Event objects containing the info
     *                  of all events associated with the current user.
     */
    public AllEventsResult(List<Event> data, String errorMessage, boolean success) {
        super(errorMessage, success);
        this.data = data;
    }

    public List<Event> getData() {
        return data;
    }
}
