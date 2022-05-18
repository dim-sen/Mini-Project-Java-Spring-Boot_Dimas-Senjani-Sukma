package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, Long> {

    User getDistinctTopByUsername(String username);

    Optional<UserDao> findByUsername(String username);

}
