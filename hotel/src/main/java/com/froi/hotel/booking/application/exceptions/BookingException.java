package com.froi.hotel.booking.application.exceptions;

public class BookingException extends Exception{
    public BookingException() {
    }

    public BookingException(String message) {
        super(message);
    }
}
