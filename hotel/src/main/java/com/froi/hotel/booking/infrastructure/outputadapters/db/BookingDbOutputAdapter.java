package com.froi.hotel.booking.infrastructure.outputadapters.db;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;
import com.froi.hotel.booking.infrastructure.outputports.FindBookingsOutputPort;
import com.froi.hotel.booking.infrastructure.outputports.MakeBookingOutputPort;
import com.froi.hotel.booking.infrastructure.outputports.PayCheckinOutputPort;
import com.froi.hotel.common.PersistenceAdapter;
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

    @Autowired
    public BookingDbOutputAdapter(BookingDbEntityRepository bookingDbEntityRepository, BookingDetailCostDbEntityRepository bookingDetailCostDbEntityRepository, BookingExtraCostDbEntityRepository bookingExtraCostDbEntityRepository) {
        this.bookingDbEntityRepository = bookingDbEntityRepository;
        this.bookingDetailCostDbEntityRepository = bookingDetailCostDbEntityRepository;
        this.bookingExtraCostDbEntityRepository = bookingExtraCostDbEntityRepository;
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
        return bookingDbEntityRepository.findFirstByIdAndHotel(bookingId, Integer.valueOf(hotelId))
                .map(BookingDbEntity::toDomain)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Booking with id %s not found in hotel %s", bookingId, hotelId)));
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
