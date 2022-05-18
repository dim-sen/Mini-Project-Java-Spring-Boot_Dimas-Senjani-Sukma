package com.dimsen.miniproject.repository;

import com.dimsen.miniproject.domain.dao.ProfileTypeDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileTypeRepository extends JpaRepository<ProfileTypeDao, Long> {
}
