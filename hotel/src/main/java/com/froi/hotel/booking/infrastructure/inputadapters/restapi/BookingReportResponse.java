package com.froi.hotel.booking.infrastructure.inputadapters.restapi;

import com.froi.hotel.booking.domain.Booking;
import lombok.Value;

import java.awt.print.Book;

@Value
public class BookingReportResponse {
    String bookingId;
    String bookingDate;
    String bookingName;
    String bookingUser;
    String hotelId;
    String hotelName;
    String roomCode;
    String checkinExpectedDate;
    String checkoutExpectedDate;
    String checkinDate;

    public static BookingReportResponse fromDomain(Booking booking) {
        String checkinDate = booking.getCheckinDate() != null ? booking.getCheckinDate().toString() : null;

        return new BookingReportResponse(
                booking.getId().toString(),
                booking.getBookingDate().toString(),
                booking.getBookingName(),
                booking.getBookingUser(),
                booking.getHotel().getId().toString(),
                booking.getHotel().getName(),
                booking.getRoom().getCode(),
                booking.getCheckinExpectedDate().toString(),
                booking.getCheckoutExpectedDate().toString(),
                checkinDate
        );
    }

}
