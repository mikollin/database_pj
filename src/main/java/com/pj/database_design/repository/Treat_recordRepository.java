package com.pj.database_design.repository;

import com.pj.database_design.domain.Patient;
import com.pj.database_design.domain.Treat_record;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface Treat_recordRepository extends PagingAndSortingRepository<Treat_record, Long> {
    List<Treat_record> findByConditionRate(Integer condition );
    List<Treat_record> findByPatient(Patient patient);
    Iterable<Treat_record> findByDate(Date date, Sort order);//排序，这里要如何按时间倒序返回  //不能包含筛选条件
}
