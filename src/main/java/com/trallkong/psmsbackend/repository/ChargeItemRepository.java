package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.ChargeItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeItemRepository extends JpaRepository<ChargeItem, Integer> {

}
