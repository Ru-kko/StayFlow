package com.stayflow.application.service;

import java.text.Normalizer;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.stayflow.application.port.in.LocationUseCase;
import com.stayflow.application.port.out.ApplicationVariables;
import com.stayflow.application.port.out.CityRepository;
import com.stayflow.application.port.out.CountryRepository;
import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.City;
import com.stayflow.domain.table.Country;
import com.stayflow.util.PageConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService implements LocationUseCase {
  private final ApplicationVariables props;
  private final CityRepository cityRepository;
  private final CountryRepository countryRepository;

  @Override
  public PageResponse<City> getCitiesInConutry(Integer page, String name, UUID cityId) {
    Pageable pageReq = PageRequest.of(page, props.getPageSize());

    Page<City> pageRes = name == null ?
      cityRepository.findByCountry_countryId(cityId, pageReq) : cityRepository.findByCountryAndName(cityId, normalizeName(name), pageReq);

    return PageConverter.buildPageResponse(pageRes);
  }

  @Override
  public List<Country> getAllCountries() {
    return countryRepository.findAll();
  }

  private String normalizeName(String input) {
    String normalized = input.toLowerCase();
    normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD)
        .replaceAll("[^\\p{ASCII}]", "");
    normalized = normalized.replaceAll("\\s+", "");

    return normalized;
  }
}
