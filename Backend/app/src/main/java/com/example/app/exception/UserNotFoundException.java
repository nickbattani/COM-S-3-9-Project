package com.example.app.exception;

/**
 * Exception class thrown when userId or userName is invalid
 */

public class UserNotFoundException extends Exception {

    public UserNotFoundException(long id) {
        super(String.format("user is not found with id : '%s'", id));
    }

    public UserNotFoundException(String input) {
        super(String.format(input + " user is not found"));
    }

}
