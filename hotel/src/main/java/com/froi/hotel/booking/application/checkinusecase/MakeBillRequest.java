package com.froi.hotel.booking.application.checkinusecase;

import lombok.Value;

import java.util.List;

@Value
public class MakeBillRequest {
    String customerNit;
    String optionalCustomerDpi;
    String optionalCustomerFirstName;
    String optionalCustomerLastName;
    String optionalCustomerBirthDate;
    String establishmentId;
    String documentId;
    List<BillDetail> billDetails;
    List<BillDiscount> billDiscounts;
    Boolean hasDiscount;
}
