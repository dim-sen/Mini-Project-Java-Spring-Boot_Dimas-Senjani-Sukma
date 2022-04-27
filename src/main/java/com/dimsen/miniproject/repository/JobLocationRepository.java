package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.JobLocationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobLocationRepository extends JpaRepository<JobLocationDao, Long> {
}
