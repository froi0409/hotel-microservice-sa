package com.froi.hotel.hotel.domain.exceptions;

public class InvalidHotelFormatException extends Exception{
    public InvalidHotelFormatException() {
    }

    public InvalidHotelFormatException(String message) {
        super(message);
    }
}
