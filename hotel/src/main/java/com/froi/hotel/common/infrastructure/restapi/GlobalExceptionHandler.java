package com.froi.hotel.common.infrastructure.restapi;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.common.exceptions.DuplicatedEntityException;
import com.froi.hotel.common.exceptions.IllegalEnumException;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;
import com.froi.hotel.room.domain.exceptions.InvalidRoomFormatException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DuplicatedEntityException.class)
    public ResponseEntity<String> handleDuplicatedEntityException(DuplicatedEntityException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(NetworkMicroserviceException.class)
    public ResponseEntity<String> handleNetworkMicroserviceException(NetworkMicroserviceException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    @ExceptionHandler(BookingException.class)
    public ResponseEntity<String> handleBookingException(BookingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(LogicBookingException.class)
    public ResponseEntity<String> handleLogicBookingException(LogicBookingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalEnumException.class)
    public ResponseEntity<String> handleIllegalEnumException(IllegalEnumException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(InvalidRoomFormatException.class)
    public ResponseEntity<String> handleInvalidRoomFormatException(InvalidRoomFormatException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(InvalidBookingFormatException.class)
    public ResponseEntity<String> handleInvalidBookingFormatException(InvalidBookingFormatException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(InvalidHotelFormatException.class)
    public ResponseEntity<String> handleInvalidHotelFormatException(InvalidHotelFormatException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

}
