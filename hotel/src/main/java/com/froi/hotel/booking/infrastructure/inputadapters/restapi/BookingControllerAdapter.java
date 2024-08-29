package com.froi.hotel.booking.infrastructure.inputadapters.restapi;

import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;
import com.froi.hotel.booking.infrastructure.inputports.MakeBookingInputPort;
import com.froi.hotel.common.WebAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bookings")
@WebAdapter
public class BookingControllerAdapter {
    private MakeBookingInputPort makeBookingInputPort;

    @Autowired
    public BookingControllerAdapter(MakeBookingInputPort makeBookingInputPort) {
        this.makeBookingInputPort = makeBookingInputPort;
    }

    @PostMapping("/make")
    public ResponseEntity<Void> makeBooking(@RequestBody MakeBookingRequest makeBookingRequest) throws BookingException {
        makeBookingInputPort.makeBooking(makeBookingRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
