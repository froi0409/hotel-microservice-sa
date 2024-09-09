package com.froi.hotel.booking.domain.exceptions;

public class LogicBookingException extends Exception{
    public LogicBookingException() {
    }

    public LogicBookingException(String message) {
        super(message);
    }
}
