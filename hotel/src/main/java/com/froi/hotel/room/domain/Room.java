package com.froi.hotel.room.domain;

import com.froi.hotel.common.DomainEntity;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.room.domain.exceptions.InvalidRoomFormatException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Room {
    private String code;
    private Double maintenanceCost;
    private Integer capacity;
    private Hotel hotel;

    public void validate() throws InvalidRoomFormatException {
        if (maintenanceCost < 0) {
            throw new InvalidRoomFormatException(String.format("Maintenance cost should be positive number. Founded: %s", maintenanceCost));
        }
        if (capacity <= 0) {
            throw new InvalidRoomFormatException(String.format("Capacity should be a positive integer number greater tan 0. Founded: %s", capacity));
        }
    }

}
