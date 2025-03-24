package com.stayflow.application.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.stayflow.application.port.in.RoomUseCase;
import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.RoomRepository;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Room;
import com.stayflow.util.PageConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomUseCase {
  private final RoomRepository roomRepository;
  private final ApplicationVariables props;

  @Override
  public Room registerRoom(Room room) {
    // Todo
    throw new UnsupportedOperationException("Method not implemented yet.");
  }

  @Override
  public PageResponse<Room> findRoomsNearMe(Double lon, Double lat, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findNearMe(lon, lat, pageable);
    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public PageResponse<Room> findByCountry(UUID countryId, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findByCity_Country_countryId(countryId, pageable);
    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public PageResponse<Room> findInCity(UUID cityId, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findByCity_CityId(cityId, pageable);
    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public Optional<Room> findById(UUID id) {
    return roomRepository.findByRoomId(id);
  }

  @Override
  public void deleteRoom(UUID id) {
    roomRepository.softDelete(id);
  }
}
