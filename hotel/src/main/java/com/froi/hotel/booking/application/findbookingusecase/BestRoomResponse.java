package com.froi.hotel.booking.application.findbookingusecase;

import com.froi.hotel.booking.domain.Booking;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class BestRoomResponse {
    private String roomCode;
    private String hotelId;
    private String hotelName;
    private Integer bookingsCount;
    private List<Booking> bookings;
}
