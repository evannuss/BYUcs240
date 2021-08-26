package RequestResult;

/**
 * Used to store the necessary information from the result of a clear request.
 * Inherits from the Result class.
 */
public class ClearResult extends Result {

    /**
     * Parameterized constructor used to populate the data members of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     */
    public ClearResult(String errorMessage, boolean success) {
        super(errorMessage, success);
    }
}
