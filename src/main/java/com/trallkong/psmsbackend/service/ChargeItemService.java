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
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        // 1. 保存收费项目
        chargeItem = chargeItemRepository.save(chargeItem);
        // 2. 查询所有需生产账单的业主
        List<Integer> ownerIds = ownerRepository.findAllValidOwnersByCommunityId(chargeItem.getCommunityId());

        // 3. 批量构建账单实例
        for (Integer ownerId : ownerIds) {
            List<Property> properties = propertyRepository.findByOwnerId(ownerId);
            for (Property p : properties) {
                Bill bill = new Bill();
                bill.setBillNo("B" + System.currentTimeMillis() + ownerId + p.getPropertyId());
                bill.setPropertyId(p.getPropertyId());
                bill.setOwnerId(ownerId);
                bill.setItemId(chargeItem.getItemId());

                // 计算周期
                CycleType cycleType = CycleType.getCycleType(chargeItem.getCycleType());
                if (cycleType != null) {
                    switch (cycleType) {
                        case Monthly:
                            bill.setCycleStart(LocalDate.now());
                            bill.setCycleEnd(LocalDate.now().plusMonths(1));
                            break;
                        case Quarterly:
                            bill.setCycleStart(LocalDate.now());
                            bill.setCycleEnd(LocalDate.now().plusMonths(3));
                            break;
                        case HalfYearly:
                            bill.setCycleStart(LocalDate.now());
                            bill.setCycleEnd(LocalDate.now().plusMonths(6));
                            break;
                        case Yearly:
                            bill.setCycleStart(LocalDate.now());
                            bill.setCycleEnd(LocalDate.now().plusYears(1));
                            break;
                        case OneTime:
                            bill.setCycleStart(LocalDate.now());
                            bill.setCycleEnd(LocalDate.now());
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid cycle type");
                    }
                }

                // 计算计费依据
                ChargeType chargeType = ChargeType.getChargeType(chargeItem.getChargeType());
                if (chargeType != null) {
                    switch (chargeType) {
                        case BuildingArea:
                            bill.setChargeBasis(p.getBuildingArea());
                            break;
                        case InsideArea:
                            bill.setChargeBasis(p.getInsideArea());
                            break;
                        case HouseHold, FixedAmount, Usage:
                            bill.setChargeBasis(BigDecimal.ONE);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid charge type");
                    }
                }

                bill.setUnitPrice(chargeItem.getUnitPrice());
                bill.setAmountDue(bill.getChargeBasis().multiply(bill.getUnitPrice()));
                bill.setReductionAmount(BigDecimal.ZERO);
                bill.setAmountPayable(bill.getAmountDue().subtract(bill.getReductionAmount()));
                bill.setAmountPaid(BigDecimal.ZERO);
                bill.setDueDate(bill.getCycleEnd());
                bill.setStatus((short) 0);
                bill.setGenerateType((short)0);
                bill.setGenerateStaffId(1);
                bill.setRemark("");

                billRepository.save(bill);
            }
        }

        // TODO: 发送邮件通知业主账单已生成

        return chargeItem;
    }

    public void deleteChargeItemById(Integer id) {
        try {
            chargeItemRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException ex = (ConstraintViolationException) e.getCause();
                if(ex.getConstraintName().equals("fk_bill_item")) {
                    throw new BusinessException("该收费项目正在被账单使用，无法删除", "FK_CONSTRAINT_VIOLATION");
                }
            }
            throw e;
        }

    }

    public ChargeItem updateChargeItem(Integer id, ChargeItem chargeItem) {
        ChargeItem existingChargeItem = chargeItemRepository.findById(id).orElse(null);
        if (existingChargeItem != null) {
            existingChargeItem.setCommunityId(chargeItem.getCommunityId());
            existingChargeItem.setItemName(chargeItem.getItemName());
            existingChargeItem.setChargeType(chargeItem.getChargeType());
            existingChargeItem.setUnitPrice(chargeItem.getUnitPrice());
            existingChargeItem.setCycleType(chargeItem.getCycleType());
            existingChargeItem.setIsFixed(chargeItem.getIsFixed());
            existingChargeItem.setStatus(chargeItem.getStatus());
            existingChargeItem.setRemark(chargeItem.getRemark());
            chargeItemRepository.save(existingChargeItem);
        }
        return existingChargeItem;
    }
}
