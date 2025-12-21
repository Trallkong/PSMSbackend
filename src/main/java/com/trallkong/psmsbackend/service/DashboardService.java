package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.repository.BillRepository;
import com.trallkong.psmsbackend.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    // 获取未缴账单总金额
    public String unpaidSum() {
        BigDecimal sum = billRepository.sumAmountUnpaid().orElse(BigDecimal.ZERO);
        return sum.toString();
    }

    // 获取已缴账单总金额
    public String paidSum() {
        BigDecimal sum = billRepository.sumAmountPaid().orElse(BigDecimal.ZERO);
        return sum.toString();
    }

    // 获取欠费户数
    public String overdueCount() {
        return String.valueOf(ownerRepository.findAllOverDueOwners().size());
    }

    // 获取所有业主数量
    public String ownerCount() {
        return String.valueOf(ownerRepository.count());
    }
}
