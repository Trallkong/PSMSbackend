package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByOwnerId(Integer ownerId);
}
