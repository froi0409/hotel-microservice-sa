package com.froi.hotel.room.infrastructure.outputadapters;


import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.domain.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
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
}
