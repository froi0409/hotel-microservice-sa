package com.froi.hotel.hotel.infrastructure.outputports.db;

import com.froi.hotel.hotel.domain.Hotel;

public interface CreateHotelOutputPort {
    void createHotel(Hotel hotel);
}
