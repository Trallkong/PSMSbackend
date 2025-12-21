package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.PaymentDetail;
import com.trallkong.psmsbackend.service.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment_detail")
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService paymentDetailService;

    @GetMapping
    public List<PaymentDetail> getAllPaymentDetails() {
        return paymentDetailService.getAllPaymentDetails();
    }

    @GetMapping("/{id}")
    public PaymentDetail getPaymentDetailById(@PathVariable Long id) {
        return paymentDetailService.getPaymentDetailById(id);
    }

    @GetMapping("/payment/{id}")
    public List<PaymentDetail> getPaymentDetailsByPaymentId(@PathVariable Long id) {
        return paymentDetailService.getPaymentDetailsByPaymentId(id);
    }

    @PostMapping
    public PaymentDetail addPaymentDetail(@RequestBody PaymentDetail paymentDetail) {
        return paymentDetailService.addPaymentDetail(paymentDetail);
    }

    @DeleteMapping("/{id}")
    public void deletePaymentDetail(@PathVariable Long id) {
        paymentDetailService.deletePaymentDetail(id);
    }
}
