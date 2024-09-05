package com.froi.hotel.booking.infrastructure.inputports.db;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;
import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;

public interface MakeBookingInputPort {

    void makeBooking(MakeBookingRequest makeBookingRequest) throws BookingException, LogicBookingException, InvalidBookingFormatException;

    // void makeInterruptedBooking(MakeBookingRequest makeBookingRequest);

}
