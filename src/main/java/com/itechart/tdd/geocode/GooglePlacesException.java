package com.itechart.tdd.geocode;

public class GooglePlacesException extends RuntimeException {

    public GooglePlacesException(String message) {
        super(message);
    }

    public GooglePlacesException(String message, Throwable cause) {
        super(message, cause);
    }

}
