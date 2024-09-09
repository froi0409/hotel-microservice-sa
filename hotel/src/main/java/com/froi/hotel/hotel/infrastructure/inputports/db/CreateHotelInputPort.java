package com.froi.hotel.hotel.infrastructure.inputports.db;

import com.froi.hotel.hotel.application.createhoteusecase.CreateHotelRequest;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;

public interface CreateHotelInputPort {
    void createHotel(CreateHotelRequest createHotelRequest) throws InvalidHotelFormatException;
}
