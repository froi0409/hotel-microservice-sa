package com.froi.hotel.booking.application.makebookingusecase;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.infrastructure.inputports.MakeBookingInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.BookingDbOutputAdapter;
import com.froi.hotel.common.UseCase;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.infrastructure.outputadapters.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@UseCase
public class MakeBookingUseCase implements MakeBookingInputPort {

    private BookingDbOutputAdapter bookingDbOutputAdapter;
    private RoomDbOutputAdapter roomDbOutputAdapter;
    private HotelDbOutputAdapter hotelDbOutputAdapter;

    private final int START_SPECIAL_SEASON = 9; // September
    private final int END_SPECIAL_SEASON = 12; // December
    @Autowired
    public MakeBookingUseCase(BookingDbOutputAdapter bookingDbOutputAdapter, RoomDbOutputAdapter roomDbOutputAdapter, HotelDbOutputAdapter hotelDbOutputAdapter) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
        this.roomDbOutputAdapter = roomDbOutputAdapter;
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
    }

    @Override
    public void makeBooking(MakeBookingRequest makeBookingRequest) throws BookingException {
        Room room = roomDbOutputAdapter
                .findRoomById(makeBookingRequest.getHotel(), makeBookingRequest.getRoomCode())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room %s not found", makeBookingRequest.getRoomCode())));
        Hotel hotel = hotelDbOutputAdapter
                .findHotelById(makeBookingRequest.getHotel())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel %s not found", makeBookingRequest.getHotel())));

        if (makeBookingRequest.getBookingUser() != null) {
            // verifica que el usuario exista
        }

        LocalDate checkin = LocalDate.parse(makeBookingRequest.getCheckinExpectedDate());
        LocalDate checkout = LocalDate.parse(makeBookingRequest.getCheckoutExpectedDate());
        List<Booking> bookingsBetweenCheckinAndCheckout = bookingDbOutputAdapter
                .findBookingBetweenCheckinAndCheckout(checkin, checkout);
        if (!bookingsBetweenCheckinAndCheckout.isEmpty()) {
            throw new BookingException("There are bookings between checkin (" + checkin + ") and checkout (" + checkout + ") dates");
        }

        Booking booking = MakeBookingRequest.convertToDomain(makeBookingRequest);
        booking.setRoom(room);
        booking.setHotel(hotel);

        bookingDbOutputAdapter.makeBooking(booking);
    }

    private boolean isSpecialDate() {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        return currentMonth >= START_SPECIAL_SEASON && currentMonth <= END_SPECIAL_SEASON;
    }

}
