package com.stayflow.domain.table;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
  @Id
  @UuidGenerator
  @GeneratedValue
  private UUID imageId;
  private String contentType;
  private String name;
}
