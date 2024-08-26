package com.froi.hotel.booking.domain;

import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.common.DomainEntity;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.room.domain.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@DomainEntity
public class Booking {
    private int id;
    private String bookingName;
    private String bookingUser;
    private BookingStatus status;
    private LocalDate checkinExpectedDate;
    private LocalDate checkoutExpectedDate;
    private double bookingPrice;
    private String note;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private Hotel hotel;
    private Room room;
    private List<BookingExtraCost> costsList;

    public void calculateBookingPrice() {
        bookingPrice = room.getMaintenanceCost();
        for (BookingExtraCost cost : costsList) {
            bookingPrice += cost.getRealPrice();
        }
    }

    public void validate() throws InvalidBookingFormatException, LogicBookingException {
        validateDates();
        validateBookingPrice();
        validateEntity();
    }

    private void validateDates() throws LogicBookingException {
        if (checkinExpectedDate.isAfter(checkoutExpectedDate)) {
            throw new LogicBookingException("Checkin expected date should be before checkout expected date");
        }
        if (checkinDate != null && checkinDate.isBefore(checkinExpectedDate)) {
            throw new LogicBookingException("Checkin date should be after or same checkin expected expected date");
        }
        if (checkoutDate != null && checkoutDate.isBefore(checkoutExpectedDate)) {
            throw new LogicBookingException("Checkout date should be after checkout expected date");
        }
        if (checkinDate != null && checkoutDate != null && checkinDate.isAfter(checkoutDate)) {
            throw new LogicBookingException("Checkin date should be before checkout date");
        }
    }

    private void validateBookingPrice() throws InvalidBookingFormatException {
        if (bookingPrice < 0) {
            throw new InvalidBookingFormatException(String.format("Booking price should be a positive number. Founded: %s", bookingPrice));
        }
    }

    private void validateEntity() throws InvalidBookingFormatException {
        if (hotel == null) {
            throw new InvalidBookingFormatException("Hotel should be informed");
        }
        if (room == null) {
            throw new InvalidBookingFormatException("Room should be informed");
        }
    }

}
