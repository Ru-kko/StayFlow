package com.stayflow.infrastructure.adapter.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.stayflow.application.port.out.RoomRepository;
import com.stayflow.domain.table.Room;

public interface JPARoomRepository extends JpaRepository<Room, UUID>, RoomRepository {

  @Query("SELECT r FROM Room r WHERE r.city.cityId = :cityId AND r.enabled = true")
  Page<Room> findByCity_CityId(@Param("cityId") UUID cityId, Pageable page);

  /**
   * Finds enabled rooms sorted by proximity to a specific location, with
   * latitudes and longitudes stored in radians.
   *
   * <p>
   * The Haversine formula is as follows:
   * 
   * <pre>
   *  a = sin²(Δφ/2) + cos(φ1) ⋅ cos(φ2) ⋅ sin²(Δλ/2)
   *  c = 2 ⋅ atan2( √a, √(1−a) )
   *  d = R ⋅ c
   * </pre>
   * 
   * Where:
   * - φ1, φ2 are latitudes in radians.
   * - λ1, λ2 are longitudes in radians.
   * - Δφ = φ2 - φ1, Δλ = λ2 - λ1.
   * - R is Earth's radius (mean = 6371 km).
   * - d is the distance in kilometers.
   * </p>
   *
   */
  @Query("""
          SELECT r FROM Room r
          WHERE r.enabled = true
          ORDER BY (6371 * acos(
              cos(:lat) * cos(r.lat) *
              cos(r.lon - :lon) +
              sin(:lat) * sin(r.lat)
          ))
      """)
  Page<Room> findNearMe(@Param("lon") Double lon, @Param("lat") Double lat, Pageable page);

  @Query("UPDATE Room r SET r.enabled = true WHERE r.id = :roomId")
  void softDelete(@Param("roomId") UUID roomId);
}
