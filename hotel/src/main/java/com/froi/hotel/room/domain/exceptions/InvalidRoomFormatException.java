package com.froi.hotel.room.domain.exceptions;

public class InvalidRoomFormatException extends Exception{
    public InvalidRoomFormatException() {
    }

    public InvalidRoomFormatException(String message) {
        super(message);
    }
}
