package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "property")
@Getter
@Setter
@NoArgsConstructor
public class Property extends BaseEntity {

    @Id
    @Column(name = "property_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("property_id")
    private Integer propertyId;

    @Column(name = "community_id", nullable = false)
    @JsonProperty("community_id")
    private Integer communityId;

    @Column(name = "building_id", nullable = false)
    @JsonProperty("building_id")
    private Integer buildingId;

    @Column(name = "unit_id", nullable = false)
    @JsonProperty("unit_id")
    private Integer unitId;

    @Column(name = "room_no", nullable = false)
    @JsonProperty("room_no")
    private String roomNo;

    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;

    @Column(name = "building_area", nullable = false)
    @JsonProperty("building_area")
    private BigDecimal buildingArea;

    @Column(name = "inside_area", nullable = false)
    @JsonProperty("inside_area")
    private BigDecimal insideArea;

    @Column(name = "house_type", nullable = false)
    @JsonProperty("house_type")
    private String houseType;

    @Column(name = "property_type", nullable = false)
    @JsonProperty("property_type")
    private Short propertyType;

    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private Short status;

    @Column(name = "checkin_time", nullable = false)
    @JsonProperty("checkin_time")
    private LocalDate checkinTime;
}
