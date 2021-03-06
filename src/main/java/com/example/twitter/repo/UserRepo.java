package com.example.twitter.repo;

import com.example.twitter.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);

}
