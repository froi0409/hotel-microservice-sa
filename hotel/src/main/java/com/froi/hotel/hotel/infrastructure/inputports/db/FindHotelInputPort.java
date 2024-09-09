package com.froi.hotel.hotel.infrastructure.inputports.db;

import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;

public interface FindHotelInputPort {
    void existsHotel(String existsHotel) throws InvalidHotelFormatException;
}
