package com.stayflow.application.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stayflow.application.port.in.RoomUseCase;
import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.ImageRepository;
import com.stayflow.application.port.out.ObjectStorage;
import com.stayflow.application.port.out.RoomRepository;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Image;
import com.stayflow.domain.table.Room;
import com.stayflow.util.PageConverter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomUseCase {
  private final RoomRepository roomRepository;
  private final ApplicationVariables props;
  private final ImageRepository imgRepository;
  private final ObjectStorage objStorage;

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

  @Override
  @SneakyThrows
  public Room registerRoom(Room room, MultipartFile file) {
    Image imgMetadata = Image.builder()
        .contentType(file.getContentType())
        .name(room.getName().concat(".webp"))
        .build();
    imgMetadata = imgRepository.save(imgMetadata);

    objStorage.uploadImage(file.getInputStream(), file.getContentType(),
        "images/".concat(imgMetadata.getImageId().toString()), file.getSize());

    room.setImage(imgMetadata);

    return roomRepository.save(room);
  }
}
