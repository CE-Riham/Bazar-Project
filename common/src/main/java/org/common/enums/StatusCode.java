package org.common.enums;

/**
 * This is an enumeration of HTTP status codes.
 * Each status code is associated with a specific HTTP response.
 * The status codes are represented as integers.
 */
public enum StatusCode {
    OK(200), // Successful request.
    CREATED(201), // A new resource is created
    BAD_REQUEST(400), // Client sent an invalid request
    UNAUTHORIZED(401), // Client failed to authenticate
    FORBIDDEN(403), //Authenticated client lacks permission to access the requested resource
    NOT_FOUND(404), //The requested resource does not exist
    INTERNAL_SERVER_ERROR(500); // Generic server error occurred

    // This is the status code associated with each HTTP response.
    private final int code;

    /**
     * This is the constructor for the StatusCode enum.
     * It initializes the status code for each HTTP response.
     *
     * @param status The status code associated with each HTTP response.
     */
    StatusCode(int status) {
        this.code = status;
    }

    /**
     * This method returns the status code associated with each HTTP response.
     * @return
     */
    public int getCode() {
        return this.code;
    }
}