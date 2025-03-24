package com.stayflow.application.port.in;

import java.util.List;
import java.util.UUID;

import com.stayflow.domain.dto.PageResponse;
import com.stayflow.domain.table.City;
import com.stayflow.domain.table.Country;

public interface LocationUseCase {
  PageResponse<City> getCitiesInConutry(Integer page, String partialName, UUID cityId);
  List<Country> getAllCountries();
}
