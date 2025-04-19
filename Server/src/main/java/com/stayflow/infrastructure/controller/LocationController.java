package com.stayflow.infrastructure.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stayflow.application.port.in.LocationUseCase;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.City;
import com.stayflow.domain.table.Country;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LocationController {
  private final LocationUseCase locationService;

  @QueryMapping
  public PageResponse<City> cities(@Argument UUID countryId, @Argument String name, @Argument Optional<Integer> page) {
    return locationService.getCitiesInConutry(page.orElse(1), name, countryId);
  }

  @QueryMapping
  public List<Country> countries() {
    return locationService.getAllCountries();
  }
}
