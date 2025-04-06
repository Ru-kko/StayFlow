package com.stayflow.application.port.in;

import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Room;

public interface RoomUseCase { 
  Room registerRoom(Room room, MultipartFile file);
  PageResponse<Room> findRoomsNearMe(Double lon, Double lat, Integer page);
  PageResponse<Room> findByCountry(UUID countryId, Integer page);
  PageResponse<Room> findInCity(UUID city, Integer page);
  Optional<Room> findById(UUID id);
  void deleteRoom(UUID id);
}
