package com.pj.database_design.repository;

import com.pj.database_design.domain.Patient;
import com.pj.database_design.domain.User;
import com.pj.database_design.domain.Ward_nurse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface Ward_nurseRepository extends CrudRepository<Ward_nurse, Long> {
    List<Ward_nurse> findByTreatmentArea(Integer area);
    Ward_nurse findByWardNurseId(Long id);
    Ward_nurse findByUser(User user);
}
