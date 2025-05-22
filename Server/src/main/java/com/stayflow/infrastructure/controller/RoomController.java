package com.stayflow.infrastructure.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.stayflow.application.port.in.RoomUseCase;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.dto.RoomCreateDto;
import com.stayflow.domain.table.City;
import com.stayflow.domain.table.Room;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomController {
  private final RoomUseCase roomService;

  @QueryMapping
  public PageResponse<Room> findRoomsNearMe(@Argument Double lon, @Argument Double lat, @Argument Optional<Integer> page) {
    return roomService.findRoomsNearMe(lon, lat, page.orElse(1));
  }

  @QueryMapping
  public PageResponse<Room> findAllRooms(@Argument Optional<Integer> page) {
    return roomService.findAll(page.orElse(1));
  }

  @QueryMapping
  public PageResponse<Room> findRoomsIn(@Argument Optional<UUID> cityId, @Argument UUID countryId,
      @Argument Optional<Integer> page) {
    if (cityId.isPresent())
      return roomService.findInCity(cityId.get(), page.orElse(1));
    return roomService.findByCountry(countryId, page.orElse(1));
  }

  @QueryMapping
  @SneakyThrows
  public Room findRoom(@Argument UUID id) {
    return roomService.findById(id);
  }

  @MutationMapping
  @SneakyThrows
  public Room updateRoomImage(@Argument UUID id, @Argument MultipartFile image) {
    return roomService.updateRoomImage(id, image);
  }

  @MutationMapping
  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  public Boolean deleteRoom(@Argument UUID id) {
    roomService.deleteRoom(id);
    return true;
  }

  @MutationMapping
  @SneakyThrows
  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  public Room updateRoom(@Argument UUID id, @Argument RoomCreateDto data) {
    Room toUpdate = Room.builder()
        .roomId(id)
        .description(data.getDescription())
        .beds(data.getBeds())
        .name(data.getName())
        .lon(data.getLon())
        .lat(data.getLat())
        .price(data.getPrice())
        .city(City.builder().cityId(data.getCity()).build())
        .build();

    return roomService.updateRoom(toUpdate);
  }

  @SneakyThrows
  @MutationMapping
  @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
  public Room createRoom(@Argument RoomCreateDto data, @Argument MultipartFile image) {
    Room toSave = Room.builder()
        .description(data.getDescription())
        .beds(data.getBeds())
        .name(data.getName())
        .lon(data.getLon())
        .lat(data.getLat())
        .price(data.getPrice())
        .city(City.builder().cityId(data.getCity()).build())
        .build();

    return roomService.registerRoom(toSave, image);
  }
}
