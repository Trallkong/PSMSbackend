package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByUsername(String username);
}
