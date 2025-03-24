package com.stayflow.application.port.out;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stayflow.domain.table.Room;

public interface RoomRepository {
  Page<Room> findByCity_CityId(UUID cityId, Pageable page);
  Page<Room> findByCity_Country_countryId(UUID counttyId, Pageable page);
  Page<Room> findNearMe(Double lon, Double lat, Pageable page);
  Optional<Room> findByRoomId(UUID room);
  Room save(Room room);
  void softDelete(UUID roomId);
}
