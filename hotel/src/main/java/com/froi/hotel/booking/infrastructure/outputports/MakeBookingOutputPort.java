package com.froi.hotel.booking.infrastructure.outputports;

import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;

import java.util.Optional;

public interface MakeBookingOutputPort {
    Optional<BookingExtraCost> findExtraCost(Integer id);

    Booking makeBooking(Booking booking);
}
