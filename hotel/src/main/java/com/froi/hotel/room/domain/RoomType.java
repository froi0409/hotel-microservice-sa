package com.froi.hotel.room.domain;

import com.froi.hotel.common.exceptions.IllegalEnumException;

public enum RoomType {
    INDIVIDUAL_ROOM,
    DOUBLE_ROOM,
    TRIPLE_ROOM,
    JUNIOR_SUITE,
    BUSINESS_SUITE,
    IMPERIAL_SUITE;

    public static RoomType fromOrdinal(int ordinal) throws IllegalEnumException {
        RoomType[] values = RoomType.values();
        if (ordinal < 0 || ordinal >= values.length) {
            throw new IllegalEnumException("Invalid ordinal for RoomType: " + ordinal);
        }
        return values[ordinal];
    }
}
