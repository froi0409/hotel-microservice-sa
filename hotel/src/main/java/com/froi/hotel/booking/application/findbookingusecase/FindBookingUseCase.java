package com.froi.hotel.booking.application.findbookingusecase;

import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.infrastructure.inputadapters.restapi.CostsReportResponse;
import com.froi.hotel.booking.infrastructure.inputports.restapi.FindBookingInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.db.BookingDbOutputAdapter;
import com.froi.hotel.common.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Override
    public List<BookingCostsInfo> getCostsReport() {
        return bookingDbOutputAdapter.findAllCosts();
    }

    @Override
    public List<Booking> findBookingsByRoomCodeAndHotelId(String roomCode, String hotelId) {
        return bookingDbOutputAdapter.findBookingsByRoomCodeAndHotelId(roomCode, hotelId);
    }

    @Override
    public List<Booking> findBestRoomBookings() {
        return bookingDbOutputAdapter.findBestRoomBookings();
    }

}
