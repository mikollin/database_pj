package com.pj.database_design.repository;

import com.pj.database_design.domain.Patient;
import com.pj.database_design.domain.Sickbed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    List<Patient> findByTreatmentArea(Integer area);
    Patient findByPatientId(Long id);
    List<Patient> findByLiveState(Integer live_state);
    List<Patient> findByIsAllowedDischarged(Integer isAllowDischarged);
    List<Patient> findByTreatmentAreaAndConditionRate(Integer treatment_area,Integer conditionRate);
    List<Patient> findByConditionRate(Integer conditionRate);
}
