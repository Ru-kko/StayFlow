package com.stayflow.application.service;

import java.math.BigDecimal;
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
import com.stayflow.domain.ErrorTypes;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.Image;
import com.stayflow.domain.table.Room;
import com.stayflow.infrastructure.error.StayFlowError;
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
  public PageResponse<Room> findAll(Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findAll(pageable);
    for (Room r : roomPage.getContent()) {
      r.degrees();
    }

    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public PageResponse<Room> findRoomsNearMe(Double lon, Double lat, Integer page) {
    Pageable pageable = PageRequest.of(page, props.getPageSize());
    Page<Room> roomPage = roomRepository.findNearMe(Math.toRadians(lon), Math.toRadians(lat), pageable);
    for (Room r : roomPage) {
      r.degrees();
    }

    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public PageResponse<Room> findByCountry(UUID countryId, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findByCity_Country_countryId(countryId, pageable);
    for (Room r : roomPage.getContent()) {
      r.degrees();
    }

    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public PageResponse<Room> findInCity(UUID cityId, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, props.getPageSize());
    Page<Room> roomPage = roomRepository.findByCity_CityId(cityId, pageable);
    for (Room r : roomPage.getContent()) {
      r.degrees();
    }

    return PageConverter.<Room>buildPageResponse(roomPage);
  }

  @Override
  public Room findById(UUID id) throws StayFlowError {
    Room res = roomRepository.findByRoomId(id).orElseThrow(() -> new StayFlowError(ErrorTypes.NOT_FOUND, "Room not found"));

    res.degrees();
    return res;
  }

  @Override
  public void deleteRoom(UUID id) {
    roomRepository.softDelete(id);
  }

  @Override
  @SneakyThrows
  public Room registerRoom(Room room, MultipartFile file) {
    Image imgMetadata = createImage(file, room.getName());
    room.setImage(imgMetadata);
    room.radiants();
    validate(room);

    Room saved = roomRepository.save(room);
    saved.degrees();

    return saved;
  }

  @Override
  public Room updateRoom(Room room) throws StayFlowError {
    Room existingRoom = roomRepository.findByRoomId(room.getRoomId())
    .orElseThrow(() -> new StayFlowError(ErrorTypes.NOT_FOUND, "Room not found"));
    
    room.radiants();
    if (room.getName() != null)
      existingRoom.setName(room.getName());
    if (room.getDescription() != null)
      existingRoom.setDescription(room.getDescription());
    if (room.getBeds() != null)
      existingRoom.setBeds(room.getBeds());
    if (room.getCity() != null)
      existingRoom.setCity(room.getCity());
    if (room.getPrice() != null)
      existingRoom.setPrice(room.getPrice());
    if (room.getLon() != null)
      existingRoom.setLon(room.getLon());
    if (room.getLat() != null)
      existingRoom.setLon(room.getLat());
    
    validate(existingRoom);
    return roomRepository.save(existingRoom);
  }

  @Override
  @SneakyThrows
  public Room updateRoomImage(UUID roomId, MultipartFile image) throws StayFlowError {
    Room room = roomRepository.findByRoomId(roomId)
        .orElseThrow(() -> new StayFlowError(ErrorTypes.NOT_FOUND, "Room not found"));
    Image imgMetadata = createImage(image, room.getName());

    room.setImage(imgMetadata);
    return roomRepository.save(room);
  }

  @SneakyThrows
  private Image createImage(MultipartFile image, String roomName) {
    Image imgMetadata = Image.builder()
        .contentType(image.getContentType() == null ? "image/webp" : image.getContentType())
        .name(roomName.concat(".webp"))
        .build();
    imgMetadata = imgRepository.save(imgMetadata);

    objStorage.uploadImage(
        image.getInputStream(),
        image.getContentType(),
        "images/".concat(imgMetadata.getImageId().toString()),
        image.getSize()
    );

    imgMetadata.setUrl(props.getImagesPrefix().concat(imgMetadata.getImageId().toString()));
    return imgRepository.save(imgMetadata);
  }

  private void validate(Room room) throws StayFlowError {
    if (room.getBeds() < 1)
      throw new StayFlowError(ErrorTypes.BAD_REQUEST, "A room should have a least of one bed");
    
    if (room.getPrice().compareTo(new BigDecimal(25)) < 1)
      throw new StayFlowError(ErrorTypes.BAD_REQUEST, "The room price is too cheap");
  }
}
