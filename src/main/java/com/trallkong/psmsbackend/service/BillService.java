package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Bill;
import com.trallkong.psmsbackend.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    // 获取所有账单
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    // 根据ID获取账单
    public Bill getBillById(Long billId) {
        return billRepository.findById(billId).orElse(null);
    }

    // 添加账单
    public Bill addBill(Bill bill) {
        return billRepository.save(bill);
    }

    // 更新账单
    public Bill updateBill(Long billId, Bill bill) {
        Bill existingBill = billRepository.findById(billId).orElse(null);
        if (existingBill != null) {
            existingBill.setBillNo(bill.getBillNo());
            existingBill.setPropertyId(bill.getPropertyId());
            existingBill.setOwnerId(bill.getOwnerId());
            existingBill.setItemId(bill.getItemId());
            existingBill.setCycleStart(bill.getCycleStart());
            existingBill.setCycleEnd(bill.getCycleEnd());
            existingBill.setChargeBasis(bill.getChargeBasis());
            existingBill.setUnitPrice(bill.getUnitPrice());
            existingBill.setAmountDue(bill.getAmountDue());
            existingBill.setReductionAmount(bill.getReductionAmount());
            existingBill.setAmountPayable(bill.getAmountPayable());
            existingBill.setAmountPaid(bill.getAmountPaid());
            existingBill.setDueDate(bill.getDueDate());
            existingBill.setStatus(bill.getStatus());
            existingBill.setGenerateType(bill.getGenerateType());
            existingBill.setGenerateStaffId(bill.getGenerateStaffId());
            existingBill.setRemark(bill.getRemark());
            return billRepository.save(existingBill);
        }
        return null;
    }

    // 删除账单
    public void deleteBill(Long billId) {
        billRepository.deleteById(billId);
    }

    // 根据ID获取账单未付金额
    public BigDecimal getAmountUnpaidById(Long billId) {
        return billRepository.findAmountUnpaidByBillId(billId);
    }

    // 根据房产ID和业主ID查询账单
    public List<Bill> getBillsByPropertyIdAndOwnerId(Integer propertyId, Integer ownerId) {
        return billRepository.findByPropertyIdAndOwnerId(propertyId, ownerId);
    }

    // 根据房产ID和业主ID查询未付账单
    public List<Bill> getUnpaidBillsByPropertyIdAndOwnerId(Integer propertyId, Integer ownerId) {
        List<Bill> bills = getBillsByPropertyIdAndOwnerId(propertyId, ownerId);

        return bills.stream()
                .filter(bill -> bill.getAmountUnpaid().compareTo(BigDecimal.ZERO) > 0)
                .toList();
    }

    // 自检更新账单状态
    public void updateBillStatusById(Long billId) {
        Bill bill = billRepository.findById(billId).orElse(null);

        if (bill != null) {
            LocalDate dueDate = bill.getDueDate();
            if (dueDate != null && dueDate.isBefore(LocalDate.now())) {
                bill.setStatus((short)2); // 逾期
            } else {
                BigDecimal amountUnpaid = bill.getAmountUnpaid();
                BigDecimal amountPayable = bill.getAmountPayable();
                if (amountUnpaid.equals(amountPayable)) {
                    bill.setStatus((short)0); // 未付
                } else if (amountUnpaid.compareTo(BigDecimal.ZERO) > 0) {
                    bill.setStatus((short)1); // 部分付
                } else {
                    bill.setStatus((short)3); // 全付
                }
            }

            billRepository.save(bill);
        }
    }
}
