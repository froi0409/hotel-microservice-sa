package com.froi.hotel.booking.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingDetailCostDbEntityRepository extends JpaRepository<BookingDetailCostDbEntity, BookingDetailCostDbEntityPK> {
    List<BookingDetailCostDbEntity> findAllByIdBooking(String bookingId);
}
