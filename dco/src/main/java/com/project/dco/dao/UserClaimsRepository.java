package com.project.dco.dao;

import com.project.dco.dto.model.UserClaims;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClaimsRepository extends CrudRepository<UserClaims, Integer> {
}
