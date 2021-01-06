package com.pj.database_design.repository;

import com.pj.database_design.domain.Emergency_nurse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zyl
 */
@Repository
public interface Emergency_nurseRepository extends CrudRepository<Emergency_nurse, Long> {

}
