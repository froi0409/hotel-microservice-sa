package com.froi.hotel.booking.infrastructure.inputadapters.restapi;

import lombok.Value;

@Value
public class CostsReportResponse {
    String date;
    String description;
    Double amount;
}
