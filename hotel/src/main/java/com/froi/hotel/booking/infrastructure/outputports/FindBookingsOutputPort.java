package com.froi.hotel.booking.infrastructure.outputports;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;

import java.time.LocalDate;
import java.util.List;

public interface FindBookingsOutputPort {
    List<Booking> findBookingBetweenCheckinAndCheckout(Integer hotel, String room, LocalDate checkin, LocalDate checkout);

    Booking findHotelBooking(String bookingId, String hotelId);

    List<BookingExtraCost> findBookingExtraCosts(String bookingId) throws BookingException;
}
