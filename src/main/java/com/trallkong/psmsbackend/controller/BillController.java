package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Bill;
import com.trallkong.psmsbackend.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    @PostMapping
    public Bill addBill(@RequestBody Bill bill) {
        return billService.addBill(bill);
    }

    @PutMapping("/{id}")
    public Bill updateBill(@PathVariable Long id, @RequestBody Bill bill) {
        return billService.updateBill(id, bill);
    }

    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
    }

    @GetMapping("/property/{propertyId}/owner/{ownerId}")
    public List<Bill> getBillsByPropertyIdAndOwnerId(@PathVariable Integer propertyId, @PathVariable Integer ownerId) {
        return billService.getBillsByPropertyIdAndOwnerId(propertyId, ownerId);
    }

    @GetMapping("/property/{propertyId}/owner/{ownerId}/unpaid")
    public List<Bill> getUnpaidBillsByPropertyIdAndOwnerId(@PathVariable Integer propertyId, @PathVariable Integer ownerId) {
        return billService.getUnpaidBillsByPropertyIdAndOwnerId(propertyId, ownerId);
    }
}
