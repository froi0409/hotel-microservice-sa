package com.froi.hotel.booking.application.findbookingusecase;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingCostsInfo {
    String bookingId;
    String bookingName;
    LocalDate date;
    Integer hotelId;
    String hotelName;
    String roomCode;
    Double maintenanceCost;
}
