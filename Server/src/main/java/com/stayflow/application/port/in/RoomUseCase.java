package com.stayflow.application.port.in;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Room;
import com.stayflow.infrastructure.error.StayFlowError;

public interface RoomUseCase { 
  Room registerRoom(Room room, MultipartFile file) throws StayFlowError;
  Room updateRoom(Room room) throws StayFlowError;
  Room updateRoomImage(UUID roomId, MultipartFile image) throws StayFlowError;
  PageResponse<Room> findRoomsNearMe(Double lon, Double lat, Integer page);
  PageResponse<Room> findByCountry(UUID countryId, Integer page);
  PageResponse<Room> findInCity(UUID city, Integer page);
  PageResponse<Room> findAll(Integer page);
  Room findById(UUID id) throws StayFlowError;
  void deleteRoom(UUID id);
}
