package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "staff")
@NoArgsConstructor
public class Staff extends BaseEntity {

    @Id
    @Column(name = "staff_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("staff_id")
    private Integer staffId;

    @Column(name = "staff_name", nullable = false)
    @JsonProperty("staff_name")
    private String staffName;

    @Column(name = "gender", nullable = false)
    private Short gender;

    @Column(name = "id_card", nullable = false)
    @JsonProperty("id_card")
    private String idCard;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "status", nullable = false)
    private Short status;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
