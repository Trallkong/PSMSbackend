package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.Fee.ChargeType;
import com.trallkong.psmsbackend.Fee.CycleType;
import com.trallkong.psmsbackend.entity.Bill;
import com.trallkong.psmsbackend.entity.ChargeItem;
import com.trallkong.psmsbackend.entity.Property;
import com.trallkong.psmsbackend.exception.BusinessException;
import com.trallkong.psmsbackend.repository.BillRepository;
import com.trallkong.psmsbackend.repository.ChargeItemRepository;
import com.trallkong.psmsbackend.repository.OwnerRepository;
import com.trallkong.psmsbackend.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ChargeItemService {

    @Autowired
    private ChargeItemRepository chargeItemRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private BillRepository billRepository;

    public List<ChargeItem> getAllChargeItems() {
        return chargeItemRepository.findAll();
    }

    public ChargeItem getChargeItemById(Integer id) {
        return chargeItemRepository.findById(id).orElse(null);
    }

    @Transactional
    public ChargeItem addChargeItem(ChargeItem chargeItem) {
        chargeItem = chargeItemRepository.save(chargeItem);
        buildBillsForChargeItem(chargeItem);
        return chargeItem;
    }

    @Transactional
    public void deleteChargeItemById(Integer id) {
        List<Bill> bills = billRepository.findByItemId(id);
        boolean hasPaidBills = bills.stream().anyMatch(b -> b.getAmountPaid().compareTo(BigDecimal.ZERO) > 0);
        if (hasPaidBills) {
            throw new BusinessException("该收费项目存在已缴费账单，无法删除", "HAS_PAID_BILLS");
        }
        billRepository.deleteAll(bills);
        chargeItemRepository.deleteById(id);
    }

    @Transactional
    public ChargeItem updateChargeItem(Integer id, ChargeItem chargeItem) {
        ChargeItem existingChargeItem = chargeItemRepository.findById(id).orElse(null);
        if (existingChargeItem != null) {
            boolean pricingChanged = !existingChargeItem.getUnitPrice().equals(chargeItem.getUnitPrice())
                || !existingChargeItem.getChargeType().equals(chargeItem.getChargeType())
                || !existingChargeItem.getCycleType().equals(chargeItem.getCycleType());

            existingChargeItem.setCommunityId(chargeItem.getCommunityId());
            existingChargeItem.setItemName(chargeItem.getItemName());
            existingChargeItem.setChargeType(chargeItem.getChargeType());
            existingChargeItem.setUnitPrice(chargeItem.getUnitPrice());
            existingChargeItem.setCycleType(chargeItem.getCycleType());
            existingChargeItem.setIsFixed(chargeItem.getIsFixed());
            existingChargeItem.setStatus(chargeItem.getStatus());
            existingChargeItem.setRemark(chargeItem.getRemark());
            chargeItemRepository.save(existingChargeItem);

            if (pricingChanged) {
                rebuildUnpaidBillsForItem(existingChargeItem);
            }
        }
        return existingChargeItem;
    }

    private void buildBillsForChargeItem(ChargeItem chargeItem) {
        List<Integer> ownerIds = ownerRepository.findAllValidOwnersByCommunityId(chargeItem.getCommunityId());
        for (Integer ownerId : ownerIds) {
            List<Property> properties = propertyRepository.findByOwnerId(ownerId);
            for (Property p : properties) {
                Bill bill = buildBill(chargeItem, p, ownerId);
                billRepository.save(bill);
            }
        }
    }

    private void rebuildUnpaidBillsForItem(ChargeItem chargeItem) {
        List<Bill> existingBills = billRepository.findByItemId(chargeItem.getItemId());
        for (Bill bill : existingBills) {
            if (bill.getAmountPaid().compareTo(BigDecimal.ZERO) == 0) {
                Property p = propertyRepository.findById(bill.getPropertyId()).orElse(null);
                if (p != null) {
                    Bill updated = buildBill(chargeItem, p, bill.getOwnerId());
                    updated.setBillId(bill.getBillId());
                    updated.setBillNo(bill.getBillNo());
                    updated.setGenerateType(bill.getGenerateType());
                    updated.setGenerateStaffId(bill.getGenerateStaffId());
                    updated.setReductionAmount(bill.getReductionAmount());
                    updated.setAmountPayable(updated.getAmountDue().subtract(updated.getReductionAmount()));
                    billRepository.save(updated);
                }
            }
        }
    }

    private Bill buildBill(ChargeItem chargeItem, Property property, Integer ownerId) {
        Bill bill = new Bill();
        bill.setBillNo("B" + System.currentTimeMillis() + ownerId + property.getPropertyId());
        bill.setPropertyId(property.getPropertyId());
        bill.setOwnerId(ownerId);
        bill.setItemId(chargeItem.getItemId());

        CycleType cycleType = CycleType.getCycleType(chargeItem.getCycleType());
        if (cycleType != null) {
            switch (cycleType) {
                case Monthly -> {
                    bill.setCycleStart(LocalDate.now());
                    bill.setCycleEnd(LocalDate.now().plusMonths(1));
                }
                case Quarterly -> {
                    bill.setCycleStart(LocalDate.now());
                    bill.setCycleEnd(LocalDate.now().plusMonths(3));
                }
                case HalfYearly -> {
                    bill.setCycleStart(LocalDate.now());
                    bill.setCycleEnd(LocalDate.now().plusMonths(6));
                }
                case Yearly -> {
                    bill.setCycleStart(LocalDate.now());
                    bill.setCycleEnd(LocalDate.now().plusYears(1));
                }
                case OneTime -> {
                    bill.setCycleStart(LocalDate.now());
                    bill.setCycleEnd(LocalDate.now());
                }
            }
        }

        ChargeType chargeType = ChargeType.getChargeType(chargeItem.getChargeType());
        if (chargeType != null) {
            switch (chargeType) {
                case BuildingArea -> bill.setChargeBasis(property.getBuildingArea());
                case InsideArea -> bill.setChargeBasis(property.getInsideArea());
                case HouseHold, FixedAmount, Usage -> bill.setChargeBasis(BigDecimal.ONE);
            }
        }

        bill.setUnitPrice(chargeItem.getUnitPrice());
        bill.setAmountDue(bill.getChargeBasis().multiply(bill.getUnitPrice()));
        bill.setReductionAmount(BigDecimal.ZERO);
        bill.setAmountPayable(bill.getAmountDue().subtract(bill.getReductionAmount()));
        bill.setAmountPaid(BigDecimal.ZERO);
        bill.setDueDate(bill.getCycleEnd());
        bill.setStatus((short) 0);
        bill.setGenerateType((short) 0);
        bill.setGenerateStaffId(1);
        bill.setRemark("");
        return bill;
    }
}
