package com.froi.hotel.booking.infrastructure.inputadapters.restapi;

import com.froi.hotel.booking.application.checkinusecase.PayCheckinRequest;
import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;
import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.booking.infrastructure.inputports.db.MakeBookingInputPort;
import com.froi.hotel.booking.infrastructure.inputports.restapi.PayCheckinInputPort;
import com.froi.hotel.common.WebAdapter;
import com.froi.hotel.common.exceptions.NetworkMicroserviceException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hotels/v1/bookings")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class BookingControllerAdapter {
    private MakeBookingInputPort makeBookingInputPort;
    private PayCheckinInputPort payCheckinInputPort;

    @Autowired
    public BookingControllerAdapter(MakeBookingInputPort makeBookingInputPort, PayCheckinInputPort payCheckinInputPort) {
        this.makeBookingInputPort = makeBookingInputPort;
        this.payCheckinInputPort = payCheckinInputPort;
    }

    @PostMapping("/make")
    @PreAuthorize("hasAnyRole('HOTEL_EMPLOYEE', 'USER')")
    public ResponseEntity<String> makeBooking(@RequestBody MakeBookingRequest makeBookingRequest) throws BookingException, LogicBookingException, InvalidBookingFormatException {
        String booking = makeBookingInputPort.makeBooking(makeBookingRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }

    @PostMapping("/checkin")
    @PreAuthorize("hasRole('HOTEL_EMPLOYEE')")
    public ResponseEntity<byte[]> payCheckin(@RequestBody PayCheckinRequest payCheckinRequest) throws BookingException, NetworkMicroserviceException, LogicBookingException {
        System.out.println("hola");
        byte[] response = payCheckinInputPort.payCheckin(payCheckinRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_hotel.pdf");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(response);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World! :D";
    }

}
