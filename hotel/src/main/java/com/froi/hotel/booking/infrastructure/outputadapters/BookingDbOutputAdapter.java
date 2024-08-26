package com.froi.hotel.booking.infrastructure.outputadapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingDbOutputAdapter {

    private BookingDbEntityRepository bookingDbEntityRepository;

    @Autowired
    public BookingDbOutputAdapter(BookingDbEntityRepository bookingDbEntityRepository) {
        this.bookingDbEntityRepository = bookingDbEntityRepository;
    }

}
