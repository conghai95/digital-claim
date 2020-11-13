package com.project.dco.dao;

import com.project.dco.dto.model.Claim;
import com.project.dco.dto.response.ClaimInfoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    @Query(value="select c.id, c.user_end_id from claims c where c.id = :id", nativeQuery = true)
    ClaimInfoResponse getClaimInfo(@Param("id") Integer id);
}
