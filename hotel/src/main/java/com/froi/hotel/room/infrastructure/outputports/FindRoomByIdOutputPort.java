package com.froi.hotel.room.infrastructure.outputports;

import com.froi.hotel.common.exceptions.IllegalEnumException;
import com.froi.hotel.room.domain.Room;

import java.util.Optional;

public interface FindRoomByIdOutputPort {
    Optional<Room> findRoomById(int hotel, String code) throws IllegalEnumException;
}
