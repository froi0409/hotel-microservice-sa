package com.froi.hotel.booking.domain;

import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.common.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class BookingExtraCost {
    private int id;
    private String description;
    private double realPrice;

    public void validate() throws InvalidBookingFormatException {
        if (realPrice < 0) {
            throw new InvalidBookingFormatException(String.format("Booking extra cost real price should be a positive number. Founded: %s", realPrice));
        }
    }

}
