package com.marion.treasuretracker.exceptions;

public class InvalidItemException extends Exception {
    public InvalidItemException() {
        super();
    }
    public InvalidItemException(String message) {
        super(message);
    }
}
