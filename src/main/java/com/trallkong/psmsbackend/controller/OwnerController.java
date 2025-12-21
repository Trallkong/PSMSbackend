package com.trallkong.psmsbackend.controller;

import com.trallkong.psmsbackend.entity.Owner;
import com.trallkong.psmsbackend.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PostMapping
    public Owner addOwner(@RequestBody Owner owner) {
        return ownerService.saveOwner(owner);
    }

    @PutMapping("/{id}")
    public Owner updateOwner(@PathVariable Integer id, @RequestBody Owner owner) {
        return ownerService.updateOwner(id, owner);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Integer id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping("/idCard/{idCard}")
    public ResponseEntity<Owner> getOwnerByIdCard(@PathVariable String idCard) {
        return ownerService.getOwnerByIdCard(idCard);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Owner> getOwnerByPhone(@PathVariable String phone) {
        return ownerService.getOwnerByPhone(phone);
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Integer id) {
        ownerService.deleteOwner(id);
    }

    @GetMapping("/count")
    public Long countOwners() {
        return ownerService.countOwners();
    }
}
