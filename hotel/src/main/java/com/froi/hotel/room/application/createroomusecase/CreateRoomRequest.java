package com.froi.hotel.room.application.createroomusecase;

import com.froi.hotel.common.exceptions.IllegalEnumException;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;
import lombok.Value;

@Value
public class CreateRoomRequest {
    String roomCode;
    Integer hotel;
    Integer roomType;

    public Room toDomain() {
        return Room.builder()
                .code(roomCode)
                .hotelId(hotel)
                .build();
    }
}
