package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.JobCategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobCategoryRepository extends JpaRepository<JobCategoryDao, Long> {
}
