package com.froi.hotel.booking.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingExtraCostDbEntityRepository extends JpaRepository<BookingExtraCostDbEntity, Integer> {
}
