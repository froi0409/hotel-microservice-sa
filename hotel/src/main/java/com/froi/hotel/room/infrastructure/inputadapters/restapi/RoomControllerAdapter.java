package com.froi.hotel.room.infrastructure.inputadapters.restapi;

import com.froi.hotel.common.WebAdapter;
import com.froi.hotel.room.infrastructure.inputports.restapi.ExistsRoomInputPort;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels/v1/rooms")
@WebAdapter
@SecurityRequirement(name = "bearerAuth")
public class RoomControllerAdapter {

    private ExistsRoomInputPort existsRoomInputPort;

    @Autowired
    public RoomControllerAdapter(ExistsRoomInputPort existsRoomInputPort) {
        this.existsRoomInputPort = existsRoomInputPort;
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{roomCode}/{hotel}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> existsRoom(@PathVariable String roomCode, @PathVariable String hotel) {
        existsRoomInputPort.existsRoom(roomCode, hotel);
        return ResponseEntity.ok().build();
    }

}
