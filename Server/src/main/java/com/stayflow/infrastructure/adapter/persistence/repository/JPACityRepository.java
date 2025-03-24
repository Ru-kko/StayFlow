package com.stayflow.infrastructure.adapter.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stayflow.application.port.out.CityRepository;
import com.stayflow.domain.table.City;

@Repository
public interface JPACityRepository extends JpaRepository<City, UUID>, CityRepository {
  @Query("""
      SELECT c FROM City c
      WHERE c.country.id = :countryId
      AND c.optimizedName LIKE CONCAT('%', :name, '%')
      """)
  Page<City> findByCountryAndName(@Param("countryId") UUID countryId, @Param("name") String name, Pageable page);

  @Modifying
  @Transactional
  @Query("UPDATE Room r SET r.enabled = false WHERE r.roomId = :roomId")
  void softDelete(@Param("roomId") UUID roomId);
}