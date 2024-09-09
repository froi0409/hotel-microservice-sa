package com.froi.hotel.booking.infrastructure.inputadapters.restapi;

import com.froi.hotel.booking.application.checkinusecase.PayCheckinRequest;
import com.froi.hotel.booking.application.exceptions.BookingException;
import com.froi.hotel.booking.application.findbookingusecase.BookingCostsInfo;
import com.froi.hotel.booking.application.makebookingusecase.MakeBookingRequest;
import com.froi.hotel.booking.domain.Booking;
import com.froi.hotel.booking.domain.exceptions.InvalidBookingFormatException;
import com.froi.hotel.booking.domain.exceptions.LogicBookingException;
import com.froi.hotel.booking.infrastructure.inputports.db.MakeBookingInputPort;
import com.froi.hotel.booking.infrastructure.inputports.restapi.FindBookingInputPort;
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

import java.util.List;

@RestController
@RequestMapping("hotels/v1/bookings")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class BookingControllerAdapter {
    private MakeBookingInputPort makeBookingInputPort;
    private PayCheckinInputPort payCheckinInputPort;
    private FindBookingInputPort findBookingInputPort;

    @Autowired
    public BookingControllerAdapter(MakeBookingInputPort makeBookingInputPort, PayCheckinInputPort payCheckinInputPort, FindBookingInputPort findBookingInputPort) {
        this.makeBookingInputPort = makeBookingInputPort;
        this.payCheckinInputPort = payCheckinInputPort;
        this.findBookingInputPort = findBookingInputPort;
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
        byte[] response = payCheckinInputPort.payCheckin(payCheckinRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=factura_hotel.pdf");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(response);
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasAnyRole('HOTEL_EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookingReportResponse> getBooking(@PathVariable String bookingId) {
        Booking booking = findBookingInputPort.findBookingById(bookingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BookingReportResponse.fromDomain(booking));
    }

    @GetMapping("/costs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingCostsInfo>> getCostsReport() {
        List<BookingCostsInfo> costsReport = findBookingInputPort.getCostsReport();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(costsReport);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World! :D";
    }

}
