package com.froi.hotel.booking.application.makebookingusecase;

import com.froi.hotel.booking.domain.Booking;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Value
public class MakeBookingRequest {
    String roomCode;
    Integer hotel;
    String note;
    String checkinExpectedDate;
    String checkoutExpectedDate;
    String bookingName;
    String bookingUser;
    List<Integer> extraCosts;

    public static Booking convertToDomain(MakeBookingRequest booking) throws DateTimeParseException {
        LocalDate checkinExpectedDate = LocalDate.parse(booking.getCheckinExpectedDate());
        LocalDate checkoutExpectedDate = LocalDate.parse(booking.getCheckoutExpectedDate());

        return Booking.builder()
                .note(booking.getNote())
                .checkinExpectedDate(checkinExpectedDate)
                .checkoutExpectedDate(checkoutExpectedDate)
                .bookingName(booking.getBookingName())
                .bookingUser(booking.getBookingUser())
                .build();
    }
}
