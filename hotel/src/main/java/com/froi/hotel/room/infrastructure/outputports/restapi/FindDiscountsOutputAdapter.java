package com.froi.hotel.room.infrastructure.outputports.restapi;

import com.froi.hotel.booking.application.checkinusecase.BillDiscount;

import java.time.LocalDate;

public interface FindDiscountsOutputAdapter {
    BillDiscount findRoomDiscount(String roomCode, String hotel, LocalDate date);

    BillDiscount findCustomerDiscount(String customerNit, LocalDate date);

}
