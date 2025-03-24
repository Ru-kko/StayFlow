package com.stayflow.domain.table;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
  @Id
  @UuidGenerator
  @GeneratedValue
  private UUID cityId;
  private String cityName;
  private String optimizedName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "countryId", nullable = false)
  private Country country;
}
