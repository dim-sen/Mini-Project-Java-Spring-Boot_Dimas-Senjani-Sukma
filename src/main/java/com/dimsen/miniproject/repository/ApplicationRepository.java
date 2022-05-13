package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.ApplicationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationDao, Long> {
}
