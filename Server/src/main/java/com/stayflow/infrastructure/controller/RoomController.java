package com.stayflow.infrastructure.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.stayflow.application.port.in.RoomUseCase;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.dto.RoomCreateDto;
import com.stayflow.domain.table.City;
import com.stayflow.domain.table.Room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RoomController {
  private final RoomUseCase roomService;
  @QueryMapping
  public PageResponse<Room> findNearMe(@Argument float lon, @Argument float lat, @Argument Integer page) {
    return null;
  }

  @QueryMapping
  public PageResponse<Room> findAll(@Argument Integer page) {
    return null;
  }

  @QueryMapping
  public PageResponse<Room> findIn(@Argument Optional<UUID> cityId, @Argument Optional<UUID> countryId) {
    return null;
  }

  @MutationMapping
  public Room createRoom(@Argument RoomCreateDto data, @Argument MultipartFile image) {
    Room toSave = Room.builder()
        .description(data.getDescription())
        .beds(data.getBeds())
        .name(data.getName())
        .lon(data.getLon())
        .lat(data.getLat())
        .city(City.builder().cityId(data.getCity()).build())
        .build();
    
    return roomService.registerRoom(toSave, image);
  }
}
