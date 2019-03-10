package com.marion.treasuretracker.exceptions;

public class InvalidSaleException extends Exception {
    public InvalidSaleException() {
        super();
    }
    public InvalidSaleException(String message) {
        super(message);
    }
}
