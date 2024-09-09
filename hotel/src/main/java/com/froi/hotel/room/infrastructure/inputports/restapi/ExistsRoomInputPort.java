package com.froi.hotel.room.infrastructure.inputports.restapi;

import com.froi.hotel.room.application.existsroomusecase.ExistsRoomRequest;
import com.froi.hotel.room.infrastructure.inputadapters.restapi.RoomExistsResponse;

public interface ExistsRoomInputPort {
    void existsRoom(String roomCode, String hotel);
}
