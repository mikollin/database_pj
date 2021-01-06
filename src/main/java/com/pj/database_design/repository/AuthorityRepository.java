package com.pj.database_design.repository;


import com.pj.database_design.domain.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zyl
 */
@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByAuthority(String authority);
}
