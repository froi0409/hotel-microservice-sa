package com.froi.hotel.booking.infrastructure.outputadapters.db;

import com.froi.hotel.booking.application.findbookingusecase.BookingCostsInfo;
import com.froi.hotel.booking.infrastructure.inputadapters.restapi.CostsReportResponse;
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
    @Query("SELECT new com.froi.hotel.booking.application.findbookingusecase.BookingCostsInfo(b.id, b.bookingName, b.checkinDate, h.id, h.name, r.id.roomCode, rt.maintenanceCost) " +
            "FROM BookingDbEntity b " +
            "JOIN RoomDbEntity r ON b.roomCode = r.id.roomCode AND b.hotel = r.id.hotel " +
            "JOIN HotelDbEntity h ON r.id.hotel = h.id " +
            "JOIN r.roomType rt " +
            "WHERE b.checkinDate IS NOT NULL")
    List<BookingCostsInfo> findBookingDetailsWithMaintenanceCost();

    @Query(value = "SELECT b.room_code, h.id AS hotel_id, h.name AS hotel_name, COUNT(*) " +
            "FROM booking b " +
            "JOIN hotel h ON h.id = b.hotel " +
            "GROUP BY h.id, b.room_code, h.name " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1", nativeQuery = true)
    List<Object[]> findTopRoomAndHotel();


    List<BookingDbEntity> findAllByRoomCodeAndHotel(String roomCode, Integer hotel);

}
