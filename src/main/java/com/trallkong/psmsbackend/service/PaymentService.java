package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Payment;
import com.trallkong.psmsbackend.entity.PaymentDetail;
import com.trallkong.psmsbackend.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentDetailService paymentDetailService;

    // 获取所有缴费信息
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 根据ID获取缴费信息
    public Payment getPaymentById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElse(null);
    }

    // 添加缴费信息
    public Payment addPayment(Payment payment) {
        // 生成缴费编号
        payment.setPaymentId(null);
        payment.setPaymentNo("P" + System.currentTimeMillis() + payment.getPaymentId() + payment.getOwnerId());

        // 设置默认备注值，避免 null 值
        if (payment.getRemark() == null) {
            payment.setRemark(""); // 或者设置其他默认值
        }

        return paymentRepository.save(payment);
    }

    // 删除缴费信息
    public void deletePayment(Long paymentId) {
        // 先删除缴费详情信息
        List<PaymentDetail> paymentDetails = paymentDetailService.getPaymentDetailsByPaymentId(paymentId);
        for (PaymentDetail paymentDetail : paymentDetails) {
            paymentDetailService.deletePaymentDetail(paymentDetail.getDetailId());
        }
        // 删除缴费信息
        paymentRepository.deleteById(paymentId);
    }
}
