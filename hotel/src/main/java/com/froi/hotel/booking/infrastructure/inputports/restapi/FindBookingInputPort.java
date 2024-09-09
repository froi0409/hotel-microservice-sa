package com.froi.hotel.booking.infrastructure.inputports.restapi;

import com.froi.hotel.booking.application.checkinusecase.BillDetail;
import com.froi.hotel.booking.application.findbookingusecase.BestRoomResponse;
import com.froi.hotel.booking.application.findbookingusecase.BookingCostsInfo;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.infrastructure.inputadapters.restapi.CostsReportResponse;

import java.util.List;

public interface FindBookingInputPort {
    Booking findBookingById(String bookingId);

    List<BookingCostsInfo> getCostsReport();

    List<Booking> findBookingsByRoomCodeAndHotelId(String roomCode, String hotelId);

    BestRoomResponse findBestRoomBookings();
}
