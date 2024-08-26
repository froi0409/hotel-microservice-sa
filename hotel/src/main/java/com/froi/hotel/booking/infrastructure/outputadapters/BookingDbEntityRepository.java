package com.froi.hotel.booking.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDbEntityRepository extends JpaRepository<BookingDbEntity, Long> {
}
