package com.froi.hotel.booking.infrastructure.outputadapters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDbEntityRepository extends JpaRepository<BookingDbEntity, Long> {
    @Query(value = "SELECT * FROM booking WHERE checkin_expected_date > :checkin AND checkout_expected_date < :checkout", nativeQuery = true)
    List<BookingDbEntity> findBookingsBetweenDates(@Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);
}
