package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community")
@Data
@NoArgsConstructor
public class Community extends BaseEntity {

    @Id
    @Column(name = "community_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("community_id")
    private Integer communityId;

    @Column(name = "community_name", nullable = false)
    @JsonProperty("community_name")
    private String communityName;

    @Column(name = "address", nullable = false)
    @JsonProperty("address")
    private String address;

    @Column(name = "contact_phone", nullable = false)
    @JsonProperty("contact_phone")
    private String contactPhone;

    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private Short status;
}
