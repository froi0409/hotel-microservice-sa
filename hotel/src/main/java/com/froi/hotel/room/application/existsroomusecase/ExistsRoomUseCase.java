package com.froi.hotel.room.application.existsroomusecase;

import com.froi.hotel.common.UseCase;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.infrastructure.inputadapters.restapi.RoomExistsResponse;
import com.froi.hotel.room.infrastructure.inputports.restapi.ExistsRoomInputPort;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class ExistsRoomUseCase implements ExistsRoomInputPort {

    private RoomDbOutputAdapter roomDbOutputAdapter;

    public ExistsRoomUseCase(RoomDbOutputAdapter roomDbOutputAdapter) {
        this.roomDbOutputAdapter = roomDbOutputAdapter;
    }

    @Override
    public void existsRoom(String roomCode, String hotel) {
        roomDbOutputAdapter.findRoomById(Integer.parseInt(hotel), roomCode)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with code %s not found in hotel %s", roomCode, hotel)));
    }
}
