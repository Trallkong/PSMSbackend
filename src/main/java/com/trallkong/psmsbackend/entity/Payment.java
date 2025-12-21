package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity{

    @Id
    @Column(name = "payment_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("payment_id")
    private Long paymentId;

    @Column(name = "payment_no", updatable = false)
    @JsonProperty("payment_no")
    private String paymentNo;

    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;

    @Column(name = "property_id", nullable = false)
    @JsonProperty("property_id")
    private Integer propertyId;

    @Column(name = "total_amount", nullable = false)
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;

    @Column(name = "pay_method", nullable = false)
    @JsonProperty("pay_method")
    private Short payMethod;

    @Column(name = "pay_time", nullable = false)
    @JsonProperty("pay_time")
    private LocalDateTime payTime;

    @Column(name = "payer_name", nullable = false)
    @JsonProperty("payer_name")
    private String payerName;

    @Column(name = "payer_phone", nullable = false)
    @JsonProperty("payer_phone")
    private String payerPhone;

    @Column(name = "staff_id", nullable = false)
    @JsonProperty("staff_id")
    private Integer staffId;

    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private Short status;

    @Column(name = "voucher_path", nullable = false)
    @JsonProperty("voucher_path")
    private String voucherPath;

    @Column(name = "remark", nullable = false)
    @JsonProperty("remark")
    private String remark;
}
