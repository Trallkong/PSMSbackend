package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT SUM(amountUnpaid) FROM Bill")
    Optional<BigDecimal> sumAmountUnpaid();

    @Query("SELECT SUM(amountPaid) FROM Bill")
    Optional<BigDecimal> sumAmountPaid();

    @Query("SELECT amountUnpaid FROM Bill WHERE billId = :billId")
    BigDecimal findAmountUnpaidByBillId(@Param("billId") Long billId);

    List<Bill> findByPropertyIdAndOwnerId(Integer propertyId, Integer ownerId);

    List<Bill> findByItemId(Integer itemId);
}
