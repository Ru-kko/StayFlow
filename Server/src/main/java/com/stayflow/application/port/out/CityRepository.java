package com.stayflow.application.port.out;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stayflow.domain.table.City;

public interface CityRepository {
  Page<City> findByCountry_countryId(UUID countryId, Pageable page);
  Page<City> findByCountryAndName(UUID countryId, String partialName, Pageable page);
}
