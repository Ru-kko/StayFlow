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
public class Room {
  @Id
  @UuidGenerator
  @GeneratedValue
  private UUID roomId;
  private String name;
  private String description;
  private Integer beds;
  private String imageUrl;

  /**
   * Location longitude
   * For optimization its stored in {@code radiants} use <pre>Math.toRadiants(degree)</pre>
   */
  private Double lon;
  /**
   * Location loatitude
   * For optimization its stored in {@code radiants} use <pre>Math.toRadiants(degree)</pre>
   */
  private Double lat;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "cityId", nullable = false)
  private City city; 
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "imageId", nullable = false)
  private Image image;
  
  @Builder.Default
  private Boolean enabled = true; 
}
