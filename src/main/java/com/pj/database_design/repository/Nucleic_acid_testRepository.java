package com.pj.database_design.repository;

import com.pj.database_design.domain.Nucleic_acid_test;
import com.pj.database_design.domain.Patient;
import com.pj.database_design.domain.Treat_record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface Nucleic_acid_testRepository extends CrudRepository<Nucleic_acid_test, Long> {
    List<Nucleic_acid_test> findByPatient(Patient patient);
}
