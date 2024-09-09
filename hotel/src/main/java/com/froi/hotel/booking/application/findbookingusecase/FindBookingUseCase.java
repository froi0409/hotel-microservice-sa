package com.froi.hotel.booking.application.findbookingusecase;

import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.infrastructure.inputports.restapi.FindBookingInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.db.BookingDbOutputAdapter;
import com.froi.hotel.common.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
public class FindBookingUseCase implements FindBookingInputPort {

    private BookingDbOutputAdapter bookingDbOutputAdapter;

    @Autowired
    public FindBookingUseCase(BookingDbOutputAdapter bookingDbOutputAdapter) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
    }

    @Override
    public Booking findBookingById(String bookingId) {
        return bookingDbOutputAdapter.findBookingById(bookingId);
    }
}
