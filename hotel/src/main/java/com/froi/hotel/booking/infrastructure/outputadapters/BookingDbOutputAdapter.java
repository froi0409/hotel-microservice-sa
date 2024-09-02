package com.froi.hotel.booking.infrastructure.outputadapters;

import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.BookingExtraCost;
import com.froi.hotel.booking.infrastructure.outputports.FindBookingsOutputAdapter;
import com.froi.hotel.booking.infrastructure.outputports.MakeBookingOutputPort;
import com.froi.hotel.common.PersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
public class BookingDbOutputAdapter implements MakeBookingOutputPort, FindBookingsOutputAdapter {

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
}
