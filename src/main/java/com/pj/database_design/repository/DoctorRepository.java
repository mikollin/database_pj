package com.pj.database_design.repository;

import com.pj.database_design.domain.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zyl
 */
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {

}
