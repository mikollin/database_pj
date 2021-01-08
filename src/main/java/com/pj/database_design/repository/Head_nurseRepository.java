package com.pj.database_design.repository;

import com.pj.database_design.domain.Head_nurse;

import com.pj.database_design.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface Head_nurseRepository extends CrudRepository<Head_nurse, Long> {
    Head_nurse findByTreatmentArea(Integer treatment_area);
    Head_nurse findByUser(User user);
}
