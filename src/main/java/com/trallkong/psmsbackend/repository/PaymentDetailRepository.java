package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    List<PaymentDetail> findPaymentDetailsByPaymentId(Long paymentId);
}
