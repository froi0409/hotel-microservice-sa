package com.froi.hotel.booking.infrastructure.inputports;

import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;

public interface MakeBookingInputPort {

    void makeBooking(MakeBookingRequest makeBookingRequest);

    // void makeInterruptedBooking(MakeBookingRequest makeBookingRequest);

}
