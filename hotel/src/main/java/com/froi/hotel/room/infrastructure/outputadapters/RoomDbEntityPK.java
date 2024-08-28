package com.froi.hotel.room.infrastructure.outputadapters;

import com.froi.hotel.room.domain.RoomType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDbEntityPK implements Serializable {
    @Column(name = "room_code")
    private String roomCode;

    @Column(name = "hotel")
    private Integer hotel;
}
