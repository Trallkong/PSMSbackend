package com.trallkong.psmsbackend.repository;

import com.trallkong.psmsbackend.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {

    Optional<Owner> findByIdCard(String idCard);
    Optional<Owner> findByPhone(String phone);

    // 查询所有有效业主
    @Query("SELECT p.ownerId FROM Property p WHERE p.communityId = :communityId")
    List<Integer> findAllValidOwnersByCommunityId(@Param("communityId") Integer communityId);

    // 查询所有逾期的业主
    @Query("SELECT o FROM Owner o WHERE o.state = 1")
    List<Owner> findAllOverDueOwners();
}
