package com.froi.hotel.hotel.infrastructure.outputports;

import com.froi.hotel.hotel.domain.Hotel;

import java.util.Optional;

public interface FindHotelByIdOutputPort {
    Optional<Hotel> findHotelById(int id);
}
