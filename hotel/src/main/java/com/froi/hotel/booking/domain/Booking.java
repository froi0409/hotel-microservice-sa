package com.froi.hotel.booking.domain;

import com.froi.hotel.booking.application.exceptions.BookingException;
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
import java.util.UUID;

@Getter
@Setter
@Builder
@DomainEntity
public class Booking {
    private UUID id;
    private String bookingName;
    private String bookingUser;
    private LocalDate bookingDate;
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

    public void validateDates() throws LogicBookingException {
        if (checkinExpectedDate.isAfter(checkoutExpectedDate)) {
            throw new LogicBookingException("Checkin expected date should be before checkout expected date");
        }
        if (checkinExpectedDate.isEqual(checkoutExpectedDate)) {
            throw new LogicBookingException("Checkin expected date should be different from checkout expected date");
        }
        if (checkinDate != null && !isValidCheckinDate(checkinDate)) {
            throw new LogicBookingException("Checkin date should be between checkin and checkout expected expected dates. Checkin expected Date: " + checkinExpectedDate + " Checkout expected Date: " + checkoutExpectedDate + ". Founded: " + checkinDate);
        }
        if (checkoutDate != null && checkoutDate.isBefore(checkoutExpectedDate)) {
            throw new LogicBookingException("Checkout date should be after checkout expected date");
        }
        if (checkinDate != null && checkoutDate != null && checkinDate.isAfter(checkoutDate)) {
            throw new LogicBookingException("Checkin date should be before checkout date");
        }
    }

    private boolean isValidCheckinDate(LocalDate checkinDate) {
        return checkinDate.isEqual(checkinExpectedDate)
                || (checkinDate.isAfter(checkinExpectedDate) && checkinDate.isBefore(checkoutExpectedDate));
    }

    public void validateIsNotCheckin() throws BookingException {
        if (checkinDate != null) {
            throw new BookingException("Booking already checked in");
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
