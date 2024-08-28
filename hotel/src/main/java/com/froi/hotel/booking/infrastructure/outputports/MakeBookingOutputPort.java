package com.froi.hotel.booking.infrastructure.outputports;

import com.froi.hotel.booking.domain.Booking;

public interface MakeBookingOutputPort {
    Booking makeBooking(Booking booking);
}
