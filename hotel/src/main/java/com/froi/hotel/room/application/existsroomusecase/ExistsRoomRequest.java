package com.froi.hotel.room.application.existsroomusecase;

import lombok.Value;

@Value
public class ExistsRoomRequest {
    String roomCode;
    String hotel;
}
