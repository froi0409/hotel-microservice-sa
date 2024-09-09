package com.froi.hotel.booking.domain.exceptions;

public class InvalidBookingFormatException extends Exception{
    public InvalidBookingFormatException() {
    }

    public InvalidBookingFormatException(String message) {
        super(message);
    }
}
