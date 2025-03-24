package com.stayflow.application.port.in;

import java.util.UUID;

import com.stayflow.domain.table.Room;

public interface RoomService {
  Room registerRoom(Room room);
  Room deleteRoom(UUID id);
}