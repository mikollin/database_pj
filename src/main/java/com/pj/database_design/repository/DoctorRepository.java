package com.pj.database_design.repository;

import com.pj.database_design.domain.Doctor;
import com.pj.database_design.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zyl
 */
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long> {
    Doctor findByDoctorId(Long id);
    Doctor findByUser(User user);
}
