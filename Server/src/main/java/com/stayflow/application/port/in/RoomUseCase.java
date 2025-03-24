package com.stayflow.application.port.in;

import java.util.Optional;
import java.util.UUID;

import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Room;

public interface RoomUseCase { 
  Room registerRoom(Room room);
  PageResponse<Room> findRoomsNearMe(Double lon, Double lat, Integer page);
  PageResponse<Room> findByCountry(UUID countryId, Integer page);
  PageResponse<Room> findInCity(UUID city, Integer page);
  Optional<Room> findById(UUID id);
  void deleteRoom(UUID id);
}
