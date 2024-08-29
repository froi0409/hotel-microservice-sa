package com.froi.hotel.booking.infrastructure.inputports;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;

public interface MakeBookingInputPort {

    void makeBooking(MakeBookingRequest makeBookingRequest) throws BookingException;

    // void makeInterruptedBooking(MakeBookingRequest makeBookingRequest);

}
