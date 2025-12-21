package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.ChargeItem;
import com.trallkong.psmsbackend.exception.BusinessException;
import com.trallkong.psmsbackend.service.ChargeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/charge_item")
public class ChargeItemController {

    @Autowired
    private ChargeItemService chargeItemService;

    @GetMapping
    public List<ChargeItem> getAllChargeItems() {
        return chargeItemService.getAllChargeItems();
    }

    @GetMapping("/{id}")
    public ChargeItem getChargeItemById(@PathVariable Integer id) {
        return chargeItemService.getChargeItemById(id);
    }

    @PostMapping
    public ChargeItem addChargeItem(@RequestBody ChargeItem chargeItem) {
        try {
            return chargeItemService.addChargeItem(chargeItem);
        } catch (Exception e) {
            System.out.println("添加收费项目失败: " + e.getMessage());
            return null;
        }
    }

    @PutMapping("/{id}")
    public ChargeItem updateChargeItem(@PathVariable Integer id, @RequestBody ChargeItem chargeItem) {
        return chargeItemService.updateChargeItem(id, chargeItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChargeItem(@PathVariable Integer id) {
        try {
            chargeItemService.deleteChargeItemById(id);
            return ResponseEntity.ok().build();
        } catch (BusinessException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getErrorCode(),
                    "message", e.getMessage()
            ));
        }
    }
}
