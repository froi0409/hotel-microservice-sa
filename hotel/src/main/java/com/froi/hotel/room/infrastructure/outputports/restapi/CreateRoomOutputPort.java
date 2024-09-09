package com.froi.hotel.room.infrastructure.outputports.restapi;

import com.froi.hotel.common.exceptions.IllegalEnumException;
import com.froi.hotel.room.application.createroomusecase.CreateRoomRequest;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;

import java.util.Optional;

public interface CreateRoomOutputPort {
    void createRoom(Room room, Integer roomType) throws IllegalEnumException;

    boolean existsRoomType(Integer roomType);
}
