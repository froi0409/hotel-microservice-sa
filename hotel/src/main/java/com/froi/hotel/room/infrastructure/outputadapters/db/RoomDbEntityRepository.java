package com.froi.hotel.room.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomDbEntityRepository extends JpaRepository<RoomDbEntity, Integer> {
    Optional<RoomDbEntity> findById(RoomDbEntityPK roomDbEntityPK);
}
