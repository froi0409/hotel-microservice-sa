package com.froi.hotel.room.infrastructure.inputadapters.restapi;

import com.froi.hotel.common.WebAdapter;
import com.froi.hotel.common.exceptions.DuplicatedEntityException;
import com.froi.hotel.room.application.createroomusecase.CreateRoomRequest;
import com.froi.hotel.room.domain.exceptions.InvalidRoomFormatException;
import com.froi.hotel.room.infrastructure.inputports.restapi.CreateRoomInputPort;
import com.froi.hotel.room.infrastructure.inputports.restapi.ExistsRoomInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels/v1/rooms")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class RoomControllerAdapter {

    private ExistsRoomInputPort existsRoomInputPort;
    private CreateRoomInputPort createRoomInputPort;

    @Autowired
    public RoomControllerAdapter(ExistsRoomInputPort existsRoomInputPort, CreateRoomInputPort createRoomInputPort) {
        this.existsRoomInputPort = existsRoomInputPort;
        this.createRoomInputPort = createRoomInputPort;
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{roomCode}/{hotel}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> existsRoom(@PathVariable String roomCode, @PathVariable String hotel) {
        existsRoomInputPort.existsRoom(roomCode, hotel);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createRoom(@RequestBody CreateRoomRequest createRoomRequest) throws InvalidRoomFormatException, DuplicatedEntityException {
        createRoomInputPort.createRoom(createRoomRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

}
