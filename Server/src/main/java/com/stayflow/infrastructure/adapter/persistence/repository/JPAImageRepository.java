package com.stayflow.infrastructure.adapter.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayflow.application.port.out.ImageRepository;
import com.stayflow.domain.table.Image;

public interface JPAImageRepository extends ImageRepository, JpaRepository<Image, UUID> {
}
