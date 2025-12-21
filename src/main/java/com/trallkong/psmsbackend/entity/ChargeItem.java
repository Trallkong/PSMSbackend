package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "charge_item")
@NoArgsConstructor
public class ChargeItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id", nullable = false)
    @JsonProperty("item_id")
    private Integer itemId;

    @Column(name = "community_id", nullable = false)
    @JsonProperty("community_id")
    private Integer communityId;

    @Column(name = "item_name", nullable = false)
    @JsonProperty("item_name")
    private String itemName;

    @Column(name = "charge_type", nullable = false)
    @JsonProperty("charge_type")
    private Short chargeType;

    @Column(name = "unit_price", nullable = false)
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @Column(name = "cycle_type", nullable = false)
    @JsonProperty("cycle_type")
    private Short cycleType;

    @Column(name = "is_fixed", nullable = false)
    @JsonProperty("is_fixed")
    private Short isFixed;

    @Column(name = "status", nullable = false)
    private Short status;

    @Column(name = "remark", nullable = false)
    private String remark;
}
