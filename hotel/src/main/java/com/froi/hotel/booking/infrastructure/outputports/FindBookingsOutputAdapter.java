package com.froi.hotel.booking.infrastructure.outputports;

import com.froi.hotel.booking.domain.Booking;

import java.time.LocalDate;
import java.util.List;

public interface FindBookingsOutputAdapter {
    List<Booking> findBookingBetweenCheckinAndCheckout(Integer hotel, String room, LocalDate checkin, LocalDate checkout);
}
