package com.froi.hotel.room.infrastructure.inputadapters.restapi;

import com.froi.hotel.room.domain.Room;
import lombok.Value;

@Value
public class RoomExistsResponse {
    String roomCode;
    String hotel;

    public static RoomExistsResponse fromDomain(Room room) {
        return new RoomExistsResponse(room.getCode(), room.getHotelId().toString());
    }
}
