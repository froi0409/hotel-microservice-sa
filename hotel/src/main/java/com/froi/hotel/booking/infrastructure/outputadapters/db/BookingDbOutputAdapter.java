package com.froi.hotel.booking.infrastructure.outputadapters.db;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.findbookingusecase.BookingCostsInfo;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;
import com.froi.hotel.booking.infrastructure.outputports.FindBookingsOutputPort;
import com.froi.hotel.booking.infrastructure.outputports.MakeBookingOutputPort;
import com.froi.hotel.booking.infrastructure.outputports.PayCheckinOutputPort;
import com.froi.hotel.common.PersistenceAdapter;
import com.froi.hotel.hotel.domain.Hotel;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbEntity;
import com.froi.hotel.hotel.infrastructure.outputadapters.HotelDbEntityRepository;
import com.froi.hotel.room.domain.Room;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbEntity;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbEntityPK;
import com.froi.hotel.room.infrastructure.outputadapters.db.RoomDbEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
public class BookingDbOutputAdapter implements MakeBookingOutputPort, FindBookingsOutputPort, PayCheckinOutputPort {

    private BookingDbEntityRepository bookingDbEntityRepository;
    private BookingDetailCostDbEntityRepository bookingDetailCostDbEntityRepository;
    private BookingExtraCostDbEntityRepository bookingExtraCostDbEntityRepository;
    private HotelDbEntityRepository hotelDbEntityRepository;
    private RoomDbEntityRepository roomDbEntityRepository;
    @Autowired
    public BookingDbOutputAdapter(BookingDbEntityRepository bookingDbEntityRepository, BookingDetailCostDbEntityRepository bookingDetailCostDbEntityRepository, BookingExtraCostDbEntityRepository bookingExtraCostDbEntityRepository, HotelDbEntityRepository hotelDbEntityRepository, RoomDbEntityRepository roomDbEntityRepository) {
        this.bookingDbEntityRepository = bookingDbEntityRepository;
        this.bookingDetailCostDbEntityRepository = bookingDetailCostDbEntityRepository;
        this.bookingExtraCostDbEntityRepository = bookingExtraCostDbEntityRepository;
        this.hotelDbEntityRepository = hotelDbEntityRepository;
        this.roomDbEntityRepository = roomDbEntityRepository;
    }


    @Override
    public List<Booking> findBookingBetweenCheckinAndCheckout(Integer hotel, String room, LocalDate checkin, LocalDate checkout) {
        return bookingDbEntityRepository
                .findBookingsBetweenDates(hotel, room, checkin, checkout)
                .stream()
                .map(BookingDbEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Booking findHotelBooking(String bookingId, String hotelId) {
        BookingDbEntity bookingDbEntity = bookingDbEntityRepository.findFirstByIdAndHotel(bookingId, Integer.valueOf(hotelId))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Booking with id %s not found in hotel %s", bookingId, hotelId)));
        Booking booking = bookingDbEntity.toDomain();

        booking.setRoom(Room.builder()
                .code(bookingDbEntity.getRoomCode())
                .build());

        return booking;
    }

    @Override
    public Booking findBookingById(String bookingId) {
        BookingDbEntity bookingDbEntity = bookingDbEntityRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Booking with id %s not found", bookingId)));
        Booking booking = bookingDbEntity.toDomain();

        Hotel hotel = hotelDbEntityRepository.findById(bookingDbEntity.getHotel())
                .map(HotelDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Hotel with id %s not found", bookingDbEntity.getHotel())));

        Room room = roomDbEntityRepository.findById(new RoomDbEntityPK(bookingDbEntity.getRoomCode(), hotel.getId()))
                .map(RoomDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Room with code %s not found", bookingDbEntity.getRoomCode())));

        booking.setHotel(hotel);
        booking.setRoom(room);

        return booking;
    }

    @Override
    public List<BookingExtraCost> findBookingExtraCosts(String bookingId) throws BookingException {
        List<BookingExtraCost> extraCosts = bookingDetailCostDbEntityRepository.findAllByIdBooking(bookingId)
                .stream()
                .map(BookingDetailCostDbEntity::toDomain)
                .toList();

        for (BookingExtraCost extraCost : extraCosts) {
            BookingExtraCostDbEntity dbEntity = bookingExtraCostDbEntityRepository.findById(extraCost.getId())
                    .orElseThrow(() -> new BookingException(String.format("Extra cost with id %s not found", extraCost.getId())));
            extraCost.setDescription(dbEntity.getDescription());
        }

        return extraCosts;
    }

    @Override
    public List<BookingCostsInfo> findAllCosts() {
        return bookingDbEntityRepository.findBookingDetailsWithMaintenanceCost();
    }

    @Override
    public Optional<BookingExtraCost> findExtraCost(Integer id) {
        Optional<BookingExtraCostDbEntity> dbEntity = bookingExtraCostDbEntityRepository.findById(id);
        if (dbEntity.isPresent()) {
            return dbEntity.map(BookingExtraCostDbEntity::toDomain);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Booking makeBooking(Booking booking) {
        BookingDbEntity bookingDbEntity = BookingDbEntity.fromDomain(booking);
        bookingDbEntity = bookingDbEntityRepository.save(bookingDbEntity);

        for (BookingExtraCost extraCost : booking.getCostsList()) {
            BookingDetailCostDbEntityPK pk = new BookingDetailCostDbEntityPK();
            pk.setBookingExtraCost(extraCost.getId());
            pk.setBooking(bookingDbEntity.getId());
            BookingDetailCostDbEntity detailCostDbEntity = new BookingDetailCostDbEntity();
            detailCostDbEntity.setId(pk);
            detailCostDbEntity.setRealPrice(extraCost.getRealPrice());
            bookingDetailCostDbEntityRepository.save(detailCostDbEntity);
        }
        return bookingDbEntity.toDomain();
    }

    @Override
    public void updateCheckinDate(String bookingId) {
        BookingDbEntity bookingDbEntity = bookingDbEntityRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Booking with id %s not found", bookingId)));
        bookingDbEntity.setCheckinDate(LocalDate.now());
        bookingDbEntityRepository.save(bookingDbEntity);
    }
}
