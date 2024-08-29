package com.froi.hotel.room.infrastructure.outputadapters;

import com.froi.hotel.common.PersistenceAdapter;
import com.froi.hotel.common.exceptions.IllegalEnumException;
import com.froi.hotel.hotel.infrastructure.outputports.FindHotelByIdOutputPort;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;
import com.froi.hotel.room.infrastructure.outputports.FindRoomByIdOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PersistenceAdapter
public class RoomDbOutputAdapter implements FindRoomByIdOutputPort {

    private RoomDbEntityRepository roomDbEntityRepository;

    @Autowired
    public RoomDbOutputAdapter(RoomDbEntityRepository roomDbEntityRepository) {
        this.roomDbEntityRepository = roomDbEntityRepository;
    }

    @Override
    public Optional<Room> findRoomById(int hotel, String code) {
        return roomDbEntityRepository.findById(new RoomDbEntityPK(code, hotel))
                .map(RoomDbEntity::toDomain);
    }
}
