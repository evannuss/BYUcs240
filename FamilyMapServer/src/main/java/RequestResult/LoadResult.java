package RequestResult;

/**
 * Used to store all necessary information for the result of a load request.
 * Inherits from the Result class.
 */
public class LoadResult extends Result {

    /**
     * Parameterized constructor used to populate the data members of the super class.
     * @param success Whether the request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed to
     *                     be completed.
     */
    public LoadResult(boolean success, String errorMessage) {
        super(errorMessage, success);
    }
}
