package net;

/**
 * Parent class of all Results
 */
public class Result {

    private String message;
    private boolean success;

    /**
     * Parameterized constructor used to populate the data members.
     * @param success Whether a request was successfully completed or not.
     * @param errorMessage The associated error message if the request failed
     *                     to be completed.
     */
    public Result(String errorMessage, boolean success) {
        this.message = errorMessage;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
