package com.stayflow.application.port.out;

import java.util.List;


import com.stayflow.domain.table.Country;

public interface CountryRepository {
  List<Country> findAll();
}
