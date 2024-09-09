package com.froi.hotel.hotel.application.createhoteusecase;

import com.froi.hotel.hotel.domain.Hotel;
import lombok.Value;

@Value
public class CreateHotelRequest {
    Integer id;
    String name;
    String phone1;
    String phone2;
    Integer city;

    public Hotel toDomain() {
        return Hotel.builder()
                .id(id)
                .name(name)
                .phone1(phone1)
                .phone2(phone2)
                .city(city)
                .build();
    }
}
