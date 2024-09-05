package com.froi.hotel.booking.infrastructure.outputadapters.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingDbEntityRepository extends JpaRepository<BookingDbEntity, String> {
    @Query(value = "SELECT * FROM booking WHERE room_code = :room and hotel = :hotel" +
            " and (checkin_expected_date between :checkin and :checkout " +
            " or checkout_expected_date between  :checkin and :checkout)", nativeQuery = true)
    List<BookingDbEntity> findBookingsBetweenDates(@Param("hotel") Integer hotel, @Param("room") String room, @Param("checkin") LocalDate checkin, @Param("checkout") LocalDate checkout);

    Optional<BookingDbEntity> findFirstByIdAndHotel(String id, Integer hotel);
}
