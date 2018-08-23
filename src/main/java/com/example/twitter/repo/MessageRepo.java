package com.example.twitter.repo;

import com.example.twitter.entitys.Messages;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Messages, Long> {

    List<Messages> findByTag(String tag);
}
