package com.froi.hotel.room.infrastructure.outputadapters.restapi;

import com.froi.hotel.booking.application.checkinusecase.BillDiscount;
import lombok.Value;

@Value
public class FindDiscountResponse {
    String discountDescription;
    int discountPercentage;

    public BillDiscount toBillDiscount() {
        return new BillDiscount(discountDescription, discountPercentage);
    }
}
