package com.froi.hotel.room.infrastructure.outputadapters.db;

import com.froi.hotel.common.PersistenceAdapter;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;
import com.froi.hotel.room.infrastructure.outputports.db.FindRoomByIdOutputPort;
import com.froi.hotel.room.infrastructure.outputports.restapi.CreateRoomOutputPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@PersistenceAdapter
public class RoomDbOutputAdapter implements FindRoomByIdOutputPort, CreateRoomOutputPort {

    private RoomDbEntityRepository roomDbEntityRepository;
    private RoomTypeDbEntityRepository roomTypeDbEntityRepository;

    @Autowired
    public RoomDbOutputAdapter(RoomDbEntityRepository roomDbEntityRepository, RoomTypeDbEntityRepository roomTypeDbEntityRepository) {
        this.roomDbEntityRepository = roomDbEntityRepository;
        this.roomTypeDbEntityRepository = roomTypeDbEntityRepository;
    }

    @Override
    public Optional<Room> findRoomById(int hotel, String code) {
        return roomDbEntityRepository.findById(new RoomDbEntityPK(code, hotel))
                .map(RoomDbEntity::toDomain);
    }

    @Override
    public void createRoom(Room room, Integer roomType) {
        RoomDbEntity roomDbEntity = RoomDbEntity.fromDomain(room, roomType);
        roomDbEntityRepository.save(roomDbEntity);
    }

    @Override
    public boolean existsRoomType(Integer roomType) {
        Optional<RoomTypeDbEntity> roomTypeDbEntity = roomTypeDbEntityRepository.findById(roomType);
        return roomTypeDbEntity.isPresent();
    }
}
