package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Bill;
import com.trallkong.psmsbackend.entity.PaymentDetail;
import com.trallkong.psmsbackend.repository.BillRepository;
import com.trallkong.psmsbackend.repository.PaymentDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentDetailService {

    @Autowired
    private PaymentDetailRepository paymentDetailRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    // 获取所有缴费信息详情
    public List<PaymentDetail> getAllPaymentDetails() {
        return paymentDetailRepository.findAll();
    }

    // 根据ID获取缴费信息详情
    public PaymentDetail getPaymentDetailById(Long paymentDetailId) {
        return paymentDetailRepository.findById(paymentDetailId).orElse(null);
    }

    // 根据缴费ID获取缴费信息详情
    public List<PaymentDetail> getPaymentDetailsByPaymentId(Long paymentId) {
        return paymentDetailRepository.findPaymentDetailsByPaymentId(paymentId);
    }

    @Transactional
    public PaymentDetail addPaymentDetail(PaymentDetail paymentDetail) {
        // 适配数据库
        paymentDetail.setCreateTime(LocalDateTime.now());
        paymentDetail.setDetailId(null);

        // 获取账单信息
        Long billId = paymentDetail.getBillId();
        Bill bill = billRepository.findById(billId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid bill ID: " + billId));

        // 更新账单信息
        bill.setAmountPaid(bill.getAmountPaid().add(paymentDetail.getPayAmount()));
        billRepository.save(bill);

        // 更新账单状态
        billService.updateBillStatusById(billId);

        return paymentDetailRepository.save(paymentDetail);
    }

    // 删除缴费信息详情
    @Transactional
    public void deletePaymentDetail(Long paymentDetailId) {
        // 同步账单信息
        // 获取缴费信息详情
        PaymentDetail paymentDetail = paymentDetailRepository.findById(paymentDetailId).orElse(null);
        if (paymentDetail != null) {
            Bill bill = billRepository.findById(paymentDetail.getBillId()).orElse(null);
            if (bill != null) {
                // 更新账单信息
                bill.setAmountPaid(bill.getAmountPaid().subtract(paymentDetail.getPayAmount()));
                billRepository.save(bill);

                // 更新账单状态
                billService.updateBillStatusById(paymentDetail.getBillId());
            }
        }
        paymentDetailRepository.deleteById(paymentDetailId);
    }
}
