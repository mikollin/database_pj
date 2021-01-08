package com.pj.database_design.repository;

import com.pj.database_design.domain.Message;

import com.pj.database_design.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zyl
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByUser(User user);
}
