package com.froi.hotel.room.infrastructure.outputadapters.db;


import com.froi.hotel.room.domain.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "room", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class RoomDbEntity {
    @EmbeddedId
    private RoomDbEntityPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type", referencedColumnName = "id", nullable = false)
    private RoomTypeDbEntity roomType;
    public Room toDomain() {
        return Room.builder()
                .code(id.getRoomCode())
                .hotelId(id.getHotel())
                .maintenanceCost(roomType.getMaintenanceCost())
                .capacity(roomType.getCapacity())
                .build();
    }

    public static RoomDbEntity fromDomain(Room room, Integer roomType) {
        RoomDbEntity roomDbEntity = new RoomDbEntity();
        roomDbEntity.setId(new RoomDbEntityPK(room.getCode(), room.getHotelId()));

        RoomTypeDbEntity roomTypeDbEntity = new RoomTypeDbEntity();
        roomTypeDbEntity.setId(roomType);

        roomDbEntity.setRoomType(roomTypeDbEntity);
        return roomDbEntity;
    }
}
