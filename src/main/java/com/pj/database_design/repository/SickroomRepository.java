package com.pj.database_design.repository;


import com.pj.database_design.domain.Sickroom;
import com.pj.database_design.domain.SickroomPrimarykey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zyl
 */
@Repository
public interface SickroomRepository extends CrudRepository<Sickroom, SickroomPrimarykey> {

}
