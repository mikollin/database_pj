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
public interface SickbedRepository extends CrudRepository<Sickbed, Long> {
    List<Sickbed> findByPatient(Patient patient);
    List<Sickbed> findByTreatmentArea(Integer treatment_area);
    List<Sickbed> findByPatientAndTreatmentArea(Patient patient,Integer treatment_area);
}
