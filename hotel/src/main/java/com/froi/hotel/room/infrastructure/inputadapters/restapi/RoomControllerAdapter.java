package com.froi.hotel.room.infrastructure.inputadapters.restapi;

import com.froi.hotel.common.WebAdapter;
import com.froi.hotel.room.infrastructure.inputports.restapi.ExistsRoomInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels/v1/rooms")
@WebAdapter
public class RoomControllerAdapter {

    private ExistsRoomInputPort existsRoomInputPort;

    @Autowired
    public RoomControllerAdapter(ExistsRoomInputPort existsRoomInputPort) {
        this.existsRoomInputPort = existsRoomInputPort;
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{roomCode}/{hotel}")
    public ResponseEntity<Void> existsRoom(@PathVariable String roomCode, @PathVariable String hotel) {
        existsRoomInputPort.existsRoom(roomCode, hotel);
        return ResponseEntity.ok().build();
    }

}
