package com.trallkong.psmsbackend.service;

import com.trallkong.psmsbackend.entity.Owner;
import com.trallkong.psmsbackend.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public ResponseEntity<Owner> getOwnerById(Integer id) {
        Optional<Owner> owner = ownerRepository.findById(id);

        return owner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Owner> getOwnerByIdCard(String idCard) {
        Optional<Owner> owner = ownerRepository.findByIdCard(idCard);

        return owner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Owner> getOwnerByPhone(String phone) {
        Optional<Owner> owner = ownerRepository.findByPhone(phone);

        return owner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Integer id, Owner owner) {
        Optional<Owner> existingOwner = ownerRepository.findById(id);
        if (existingOwner.isPresent()) {
            Owner updatedOwner = existingOwner.get();
            updatedOwner.setOwnerName(owner.getOwnerName());
            updatedOwner.setGender(owner.getGender());
            updatedOwner.setPhone(owner.getPhone());
            updatedOwner.setIdCard(owner.getIdCard());
            updatedOwner.setEmergencyContact(owner.getEmergencyContact());
            updatedOwner.setEmergencyPhone(owner.getEmergencyPhone());
            updatedOwner.setEmail(owner.getEmail());
            updatedOwner.setState(owner.getState());
            return ownerRepository.save(updatedOwner);
        }
        return null;
    }

    public void deleteOwner(Integer id) {
        ownerRepository.deleteById(id);
    }

    public Long countOwners() {
        return ownerRepository.count();
    }
}
