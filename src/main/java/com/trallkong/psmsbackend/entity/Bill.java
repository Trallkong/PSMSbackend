package com.trallkong.psmsbackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bill")
@NoArgsConstructor
@Getter
@Setter
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id", nullable = false)
    @JsonProperty("bill_id")
    private Long billId;

    @Column(name = "bill_no", nullable = false)
    @JsonProperty("bill_no")
    private String billNo;

    @Column(name = "property_id", nullable = false)
    @JsonProperty("property_id")
    private Integer propertyId;

    @Column(name = "owner_id", nullable = false)
    @JsonProperty("owner_id")
    private Integer ownerId;

    @Column(name = "item_id", nullable = false)
    @JsonProperty("item_id")
    private Integer itemId;

    @Column(name = "cycle_start", nullable = false)
    @JsonProperty("cycle_start")
    private LocalDate cycleStart;

    @Column(name = "cycle_end", nullable = false)
    @JsonProperty("cycle_end")
    private LocalDate cycleEnd;

    @Column(name = "charge_basis", nullable = false)
    @JsonProperty("charge_basis")
    private BigDecimal chargeBasis;

    @Column(name = "unit_price", nullable = false)
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;

    @Column(name = "amount_due", nullable = false)
    @JsonProperty("amount_due")
    private BigDecimal amountDue;

    @Column(name = "reduction_amount", nullable = false)
    @JsonProperty("reduction_amount")
    private BigDecimal reductionAmount;

    @Column(name = "amount_payable", nullable = false)
    @JsonProperty("amount_payable")
    private BigDecimal amountPayable;

    @Column(name = "amount_paid", nullable = false)
    @JsonProperty("amount_paid")
    private BigDecimal amountPaid;

    @Formula("COALESCE(amount_payable, 0) - COALESCE(amount_paid, 0)")
    @Basic(fetch = FetchType.EAGER)
    @JsonProperty("amount_unpaid")
    private BigDecimal amountUnpaid;

    @Column(name = "due_date", nullable = false)
    @JsonProperty("due_date")
    private LocalDate dueDate;

    @Column(name = "status", nullable = false)
    @JsonProperty("status")
    private Short status;

    @Column(name = "generate_type", nullable = false)
    @JsonProperty("generate_type")
    private Short generateType;

    @Column(name = "generate_staff_id", nullable = false)
    @JsonProperty("generate_staff_id")
    private Integer generateStaffId;

    @Column(name = "remark", nullable = false)
    @JsonProperty("remark")
    private String remark;
}
