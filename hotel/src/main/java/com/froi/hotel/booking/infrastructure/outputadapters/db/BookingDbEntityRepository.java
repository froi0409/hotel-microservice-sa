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

    @Query(value = "select " +
            "room_code, " +
            "hotel, " +
            "count(*) " +
            "from booking " +
            "group by room_code, hotel " +
            "order by count(*) desc " +
            "limit 1", nativeQuery = true)
    List<Object[]> findTopRoomAndHotel();


    List<BookingDbEntity> findAllByRoomCodeAndHotel(String roomCode, Integer hotel);

}
