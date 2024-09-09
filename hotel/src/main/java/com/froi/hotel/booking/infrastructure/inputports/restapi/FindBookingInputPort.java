package com.froi.hotel.booking.infrastructure.inputports.restapi;

import com.froi.hotel.booking.domain.Booking;

public interface FindBookingInputPort {
    Booking findBookingById(String bookingId);
}
