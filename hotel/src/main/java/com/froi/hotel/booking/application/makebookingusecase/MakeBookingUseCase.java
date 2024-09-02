package com.froi.hotel.booking.application.makebookingusecase;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;
import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.booking.infrastructure.inputports.MakeBookingInputPort;
import com.froi.hotel.booking.infrastructure.outputadapters.BookingDbOutputAdapter;
import com.froi.hotel.common.UseCase;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.infrastructure.outputadapters.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class MakeBookingUseCase implements MakeBookingInputPort {

    private BookingDbOutputAdapter bookingDbOutputAdapter;
    private RoomDbOutputAdapter roomDbOutputAdapter;
    private HotelDbOutputAdapter hotelDbOutputAdapter;

    private final int START_SPECIAL_SEASON = 9; // September
    private final int END_SPECIAL_SEASON = 12; // December
    private final int NORMAL_SEASON = 1;
    private final int SPECIAL_SEASON = 2;


    @Autowired
    public MakeBookingUseCase(BookingDbOutputAdapter bookingDbOutputAdapter, RoomDbOutputAdapter roomDbOutputAdapter, HotelDbOutputAdapter hotelDbOutputAdapter) {
        this.bookingDbOutputAdapter = bookingDbOutputAdapter;
        this.roomDbOutputAdapter = roomDbOutputAdapter;
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
    }

    @Override
    public void makeBooking(MakeBookingRequest makeBookingRequest) throws BookingException, LogicBookingException, InvalidBookingFormatException {
        LocalDate checkin = LocalDate.parse(makeBookingRequest.getCheckinExpectedDate());
        LocalDate checkout = LocalDate.parse(makeBookingRequest.getCheckoutExpectedDate());

        Hotel hotel = validateHotel(makeBookingRequest);
        Room room = validateRoom(makeBookingRequest);
        List<BookingExtraCost> extraCosts = handleExtraCosts(makeBookingRequest, checkin, checkout);

        validateBookingsBetweenCheckinAndCheckout(hotel, room, checkin, checkout);

        Booking booking = MakeBookingRequest.convertToDomain(makeBookingRequest);
        booking.setRoom(room);
        booking.setHotel(hotel);
        booking.setCostsList(extraCosts);
        booking.calculateBookingPrice();
        booking.setBookingDate(LocalDate.now());
        booking.validate();
        bookingDbOutputAdapter.makeBooking(booking);
    }

    private ArrayList<BookingExtraCost> handleExtraCosts(MakeBookingRequest makeBookingRequest, LocalDate checkin, LocalDate checkout) {
        ArrayList<BookingExtraCost> extraCosts = new ArrayList<>();

        addNormalDateCosts(extraCosts, checkin, checkout);
        addSpecialDateCosts(extraCosts, checkin, checkout);

        if (makeBookingRequest.getExtraCosts() != null) {
            for (Integer extraCostId : makeBookingRequest.getExtraCosts()) {
                BookingExtraCost extraCost = bookingDbOutputAdapter.findExtraCost(extraCostId)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Extra cost %s not found", extraCostId)));
                extraCosts.add(extraCost);
            }
        }

        return extraCosts;
    }

    private void addNormalDateCosts(ArrayList<BookingExtraCost> extraCosts, LocalDate checkin, LocalDate checkout) {
        LocalDate date = checkin;
        double normalExtraCost = 0.00;

        BookingExtraCost normalSeasonExtraCost = bookingDbOutputAdapter.findExtraCost(NORMAL_SEASON)
                .orElseThrow(() -> new EntityNotFoundException("Normal season extra cost not found"));

        while (!date.isAfter(checkout) && !date.isEqual(checkout)) {
            if (!isSpecialDate(date)) {
                normalExtraCost += normalSeasonExtraCost.getRealPrice();
            }
            date = date.plusDays(1);
        }
        normalSeasonExtraCost.setRealPrice(normalExtraCost);
        extraCosts.add(normalSeasonExtraCost);
    }

    private void addSpecialDateCosts(ArrayList<BookingExtraCost> extraCosts, LocalDate checkin, LocalDate checkout) {
        LocalDate date = checkin;
        double specialExtraCost = 0.00;

        BookingExtraCost specialSeasonExtraCost = bookingDbOutputAdapter.findExtraCost(SPECIAL_SEASON)
                .orElseThrow(() -> new EntityNotFoundException("Special season extra cost not found"));

        while (!date.isAfter(checkout) && !date.isEqual(checkout)) {
            if (isSpecialDate(date)) {
                specialExtraCost += specialSeasonExtraCost.getRealPrice();
                System.out.println("se suma");
            }
            System.out.println("special");
            date = date.plusDays(1);
        }
        specialSeasonExtraCost.setRealPrice(specialExtraCost);
        extraCosts.add(specialSeasonExtraCost);
    }

    private Hotel validateHotel(MakeBookingRequest makeBookingRequest) {
        return hotelDbOutputAdapter
                .findHotelById(makeBookingRequest.getHotel())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel %s not found", makeBookingRequest.getHotel())));
    }

    private Room validateRoom(MakeBookingRequest makeBookingRequest) {
        return roomDbOutputAdapter
                .findRoomById(makeBookingRequest.getHotel(), makeBookingRequest.getRoomCode())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room %s not found", makeBookingRequest.getRoomCode())));
    }

    private void validateBookingsBetweenCheckinAndCheckout(Hotel hotel, Room room, LocalDate checkin, LocalDate checkout) throws BookingException {
        List<Booking> bookingsBetweenCheckinAndCheckout = bookingDbOutputAdapter
                .findBookingBetweenCheckinAndCheckout(hotel.getId(), room.getCode(), checkin, checkout);
        System.out.println(bookingsBetweenCheckinAndCheckout.size());
        if (!bookingsBetweenCheckinAndCheckout.isEmpty()) {
            throw new BookingException("There are bookings between checkin (" + checkin + ") and checkout (" + checkout + ") dates");
        }
    }

    private String validateUser(MakeBookingRequest makeBookingRequest) {
        if (makeBookingRequest.getBookingUser() != null) {
            // validation logic
        }
        return null;
    }

    private boolean isSpecialDate(LocalDate dateToCheck) {
        int currentMonth = dateToCheck.getMonthValue();
        return currentMonth >= START_SPECIAL_SEASON && currentMonth <= END_SPECIAL_SEASON;
    }

}
