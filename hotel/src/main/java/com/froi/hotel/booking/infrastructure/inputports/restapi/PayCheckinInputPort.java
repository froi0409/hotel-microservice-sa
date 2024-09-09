package com.froi.hotel.booking.infrastructure.inputports.restapi;

import com.froi.hotel.booking.application.checkinusecase.PayCheckinRequest;
import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;

public interface PayCheckinInputPort {
    byte[] payCheckin(PayCheckinRequest payCheckinRequest) throws BookingException, NetworkMicroserviceException, LogicBookingException;
}
