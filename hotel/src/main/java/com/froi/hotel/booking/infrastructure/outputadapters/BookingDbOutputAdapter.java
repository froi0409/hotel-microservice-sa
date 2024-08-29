package com.froi.hotel.booking.infrastructure.outputadapters;

import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.infrastructure.outputports.FindBookingsOutputAdapter;
import com.froi.hotel.booking.infrastructure.outputports.MakeBookingOutputPort;
import com.froi.hotel.common.PersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class BookingDbOutputAdapter implements MakeBookingOutputPort, FindBookingsOutputAdapter {

    private BookingDbEntityRepository bookingDbEntityRepository;

    @Autowired
    public BookingDbOutputAdapter(BookingDbEntityRepository bookingDbEntityRepository) {
        this.bookingDbEntityRepository = bookingDbEntityRepository;
    }


    @Override
    public List<Booking> findBookingBetweenCheckinAndCheckout(LocalDate checkin, LocalDate checkout) {
        return bookingDbEntityRepository
                .findBookingsBetweenDates(checkin, checkout)
                .stream()
                .map(BookingDbEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Booking makeBooking(Booking booking) {
        BookingDbEntity dbEntity = BookingDbEntity.fromDomain(booking);
        bookingDbEntityRepository.save(dbEntity);
        return dbEntity.toDomain();
    }
}
