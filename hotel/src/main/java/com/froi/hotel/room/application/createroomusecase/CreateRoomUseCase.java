package com.froi.hotel.room.application.createroomusecase;

import com.froi.hotel.common.UseCase;
import com.froi.hotel.common.exceptions.DuplicatedEntityException;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbOutputAdapter;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;
import com.froi.hotel.room.domain.exceptions.InvalidRoomFormatException;
import com.froi.hotel.room.infrastructure.inputports.restapi.CreateRoomInputPort;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbOutputAdapter;
import com.sun.jdi.InvalidLineNumberException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.DuplicateFormatFlagsException;
import java.util.Optional;

@UseCase
public class CreateRoomUseCase implements CreateRoomInputPort {

    private RoomDbOutputAdapter roomDbOutputAdapter;
    private HotelDbOutputAdapter hotelDbOutputAdapter;

    @Autowired
    public CreateRoomUseCase(RoomDbOutputAdapter roomDbOutputAdapter, HotelDbOutputAdapter hotelDbOutputAdapter) {
        this.roomDbOutputAdapter = roomDbOutputAdapter;
        this.hotelDbOutputAdapter = hotelDbOutputAdapter;
    }

    @Override
    public void createRoom(CreateRoomRequest createRoomRequest) throws InvalidRoomFormatException, DuplicatedEntityException {
        Integer roomType = createRoomRequest.getRoomType();
        Optional<Room> room = roomDbOutputAdapter.findRoomById(createRoomRequest.getHotel(), createRoomRequest.getRoomCode());
        Hotel hotel = hotelDbOutputAdapter.findHotelById(createRoomRequest.getHotel())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id %s not found", createRoomRequest.getHotel())));
        if (room.isPresent()) {
            throw new DuplicatedEntityException(String.format("Room with code %s already exists in hotel %s", createRoomRequest.getRoomCode(), createRoomRequest.getHotel()));
        }


        if (!roomDbOutputAdapter.existsRoomType(roomType)) {
            throw new InvalidRoomFormatException(String.format("Room type %s does not exist", createRoomRequest.getRoomType()));
        }

        Room roomDomain = createRoomRequest.toDomain();
        roomDbOutputAdapter.createRoom(roomDomain, roomType);
    }

}
