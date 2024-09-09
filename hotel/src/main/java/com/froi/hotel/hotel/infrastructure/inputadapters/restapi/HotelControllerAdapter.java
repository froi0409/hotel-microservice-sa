package com.froi.hotel.hotel.infrastructure.inputadapters.restapi;

import com.froi.hotel.common.WebAdapter;
import com.froi.hotel.hotel.application.createhoteusecase.CreateHotelRequest;
import com.froi.hotel.hotel.domain.exceptions.InvalidHotelFormatException;
import com.froi.hotel.hotel.infrastructure.inputports.db.CreateHotelInputPort;
import com.froi.hotel.hotel.infrastructure.inputports.db.FindHotelInputPort;
import com.froi.hotel.hotel.infrastructure.outputports.db.CreateHotelOutputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels/v1/hotels")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class HotelControllerAdapter {

    private CreateHotelInputPort createHotelInputPort;
    private FindHotelInputPort findHotelInputPort;

    @Autowired
    public HotelControllerAdapter(CreateHotelInputPort createHotelInputPort, FindHotelInputPort findHotelInputPort) {
        this.createHotelInputPort = createHotelInputPort;
        this.findHotelInputPort = findHotelInputPort;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createHotel(@RequestBody CreateHotelRequest createHotelRequest) throws InvalidHotelFormatException {
        createHotelInputPort.createHotel(createHotelRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{hotel}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> existsHotel(@PathVariable String hotel) throws InvalidHotelFormatException {
        findHotelInputPort.existsHotel(hotel);
        return ResponseEntity.ok().build();
    }

}
