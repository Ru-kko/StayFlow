package com.stayflow.infrastructure.adapter.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stayflow.application.port.out.CountryRepository;
import com.stayflow.domain.table.Country;

@Repository
public interface JPACountryRepository extends JpaRepository<Country, UUID>, CountryRepository {
}