package com.pj.database_design.repository;


import com.pj.database_design.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author zyl
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String username);
    User findByUserId(Long uid);

}
