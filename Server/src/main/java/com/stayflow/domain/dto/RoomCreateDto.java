package com.stayflow.domain.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreateDto {
  private String name;
  private String description;
  private Integer beds;
  private Double lon;
  private Double lat;
  private UUID city;
  private BigDecimal price;
}
