package com.froi.hotel.hotel.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDbEntityRepository extends JpaRepository<HotelDbEntity, Integer> {
}
