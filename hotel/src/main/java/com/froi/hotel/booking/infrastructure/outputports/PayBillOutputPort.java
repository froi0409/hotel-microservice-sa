package com.froi.hotel.booking.infrastructure.outputports;

import com.froi.hotel.booking.application.checkinusecase.MakeBillRequest;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;

public interface PayBillOutputPort {
    byte[] payCheckin(MakeBillRequest makeBillRequest) throws NetworkMicroserviceException;
}

