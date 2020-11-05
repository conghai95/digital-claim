package com.project.dco.dao;

import com.project.dco.dto.model.UserEnd;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEndRepository extends CrudRepository<UserEnd, Integer> {

    @Query(value = "select *from user_end ue where ue.id = :id", nativeQuery=true)
    UserEnd getUserEndById(@Param("id") Integer id);
}
