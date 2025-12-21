package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "owner")
@NoArgsConstructor
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;

    @Column(name = "owner_name", nullable = false)
    @JsonProperty("owner_name")
    private String ownerName;

    @Column(name = "gender", nullable = false)
    @JsonProperty("gender")
    private Short gender;

    @Column(name = "phone", nullable = false)
    @JsonProperty("phone")
    private String phone;

    @Column(name = "id_card", nullable = false)
    @JsonProperty("id_card")
    private String idCard;

    @Column(name = "emergency_contact", nullable = false)
    @JsonProperty("emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_phone", nullable = false)
    @JsonProperty("emergency_phone")
    private String emergencyPhone;

    @Column(name = "email", nullable = false)
    @JsonProperty("email")
    private String email;

    @Column(name = "state")
    @JsonProperty("state")
    private Short state;
}
