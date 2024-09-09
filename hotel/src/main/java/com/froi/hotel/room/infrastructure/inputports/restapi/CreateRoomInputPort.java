package com.froi.hotel.room.infrastructure.inputports.restapi;

import com.froi.hotel.common.exceptions.DuplicatedEntityException;
import com.froi.hotel.room.application.createroomusecase.CreateRoomRequest;
import com.froi.hotel.room.domain.exceptions.InvalidRoomFormatException;

public interface CreateRoomInputPort {
    void createRoom(CreateRoomRequest createRoomRequest) throws InvalidRoomFormatException, DuplicatedEntityException;
}
