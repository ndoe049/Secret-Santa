package com.nathanieldoe.santa.api.exception;

/**
 * Indicates a state of attempting to add an invalid exclusion
 */
public class InvalidExclusionException extends Exception{

    public InvalidExclusionException(String message) {
        super(message);
    }

}
