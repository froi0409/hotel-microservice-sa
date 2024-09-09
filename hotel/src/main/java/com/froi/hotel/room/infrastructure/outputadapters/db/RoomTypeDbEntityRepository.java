package com.froi.hotel.room.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeDbEntityRepository extends JpaRepository<RoomTypeDbEntity, Integer> {
}
