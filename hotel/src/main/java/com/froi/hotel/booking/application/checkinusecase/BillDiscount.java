package com.froi.hotel.booking.application.checkinusecase;

import lombok.Value;

@Value
public class BillDiscount {
    private String description;
    private double discounted;
}
