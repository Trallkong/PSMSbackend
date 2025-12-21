package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_detail")
@Getter
@Setter
@NoArgsConstructor
public class PaymentDetail {

    @Id
    @Column(name = "detail_id", nullable = false)
    @JsonProperty("detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    @Column(name = "payment_id", nullable = false)
    @JsonProperty("payment_id")
    private Long paymentId;

    @Column(name = "bill_id", nullable = false)
    @JsonProperty("bill_id")
    private Long billId;

    @Column(name = "pay_amount", nullable = false)
    @JsonProperty("pay_amount")
    private BigDecimal payAmount;

    @Column(name = "remark", nullable = false)
    @JsonProperty("remark")
    private String remark;

    @Column(name = "create_time", nullable = false, updatable = false)
    @JsonProperty("create_time")
    private LocalDateTime createTime;
}
