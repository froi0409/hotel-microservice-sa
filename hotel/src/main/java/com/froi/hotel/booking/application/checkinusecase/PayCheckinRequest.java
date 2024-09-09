package com.froi.hotel.booking.application.checkinusecase;

import lombok.Value;

@Value
public class PayCheckinRequest {
    private String bookingId;
    private String hotelId;
    private String customerNit;
    private String optionalCustomerDpi;
    private String optionalCustomerFirstName;
    private String optionalCustomerLastName;
    private String optionalCustomerBirthDate;
    private boolean hasDiscount;
}
